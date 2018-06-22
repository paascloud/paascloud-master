/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.paascloud.BigDecimalUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.BaseQuery;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.exceptions.OmcBizException;
import com.paascloud.provider.mapper.OmcCartMapper;
import com.paascloud.provider.mapper.OmcOrderMapper;
import com.paascloud.provider.mapper.OmcShippingMapper;
import com.paascloud.provider.model.constant.OmcApiConstant;
import com.paascloud.provider.model.domain.OmcCart;
import com.paascloud.provider.model.domain.OmcOrder;
import com.paascloud.provider.model.domain.OmcOrderDetail;
import com.paascloud.provider.model.domain.OmcShipping;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.model.dto.OrderPageQuery;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.OrderDocVo;
import com.paascloud.provider.model.vo.OrderItemVo;
import com.paascloud.provider.model.vo.OrderVo;
import com.paascloud.provider.model.vo.ShippingVo;
import com.paascloud.provider.service.MdcProductService;
import com.paascloud.provider.service.OmcCartService;
import com.paascloud.provider.service.OmcOrderDetailService;
import com.paascloud.provider.service.OmcOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * The class Omc order service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
public class OmcOrderServiceImpl extends BaseService<OmcOrder> implements OmcOrderService {

	@Resource
	private OmcOrderMapper omcOrderMapper;
	@Resource
	private OmcCartMapper omcCartMapper;
	@Resource
	private OmcShippingMapper omcShippingMapper;
	@Resource
	private OmcCartService omcCartService;
	@Resource
	private OmcOrderDetailService omcOrderDetailService;

	@Resource
	private MdcProductService mdcProductService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderVo createOrderDoc(LoginAuthDto loginAuthDto, Long shippingId) {
		Long userId = loginAuthDto.getUserId();
		//从购物车中获取数据
		List<OmcCart> cartList = omcCartMapper.selectCheckedCartByUserId(userId);
		if (CollectionUtils.isEmpty(cartList)) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031001, userId);
		}
		//计算这个订单的总价
		List<OmcOrderDetail> omcOrderDetailList = omcCartService.getCartOrderItem(userId, cartList);

		if (CollectionUtils.isEmpty(omcOrderDetailList)) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031001, userId);
		}

		BigDecimal payment = this.getOrderTotalPrice(omcOrderDetailList);


		//生成订单
		OmcOrder order = this.assembleOrder(userId, shippingId, payment);
		if (order == null) {
			logger.error("生成订单失败, userId={}, shippingId={}, payment={}", userId, shippingId, payment);
			throw new OmcBizException(ErrorCodeEnum.OMC10031002);
		}
		order.setUpdateInfo(loginAuthDto);
		for (OmcOrderDetail orderDetail : omcOrderDetailList) {
			orderDetail.setUpdateInfo(loginAuthDto);
			orderDetail.setOrderNo(order.getOrderNo());


			orderDetail.setId(super.generateId());
			orderDetail.setUpdateInfo(loginAuthDto);
		}

		//mybatis 批量插入
		omcOrderDetailService.batchInsertOrderDetail(omcOrderDetailList);

		//生成成功,我们要减少我们产品的库存
		this.reduceProductStock(omcOrderDetailList);
		//清空一下购物车
		this.cleanCart(cartList);

		//返回给前端数据

		return assembleOrderVo(order, omcOrderDetailList);
	}

	@Override
	public int cancelOrderDoc(LoginAuthDto loginAuthDto, String orderNo) {
		Long userId = loginAuthDto.getUserId();
		OmcOrder order = omcOrderMapper.selectByUserIdAndOrderNo(userId, orderNo);
		if (order == null) {
			logger.error("该用户此订单不存在, userId={}, orderNo={}", userId, orderNo);
			throw new OmcBizException(ErrorCodeEnum.OMC10031003);
		}
		if (order.getStatus() != OmcApiConstant.OrderStatusEnum.NO_PAY.getCode()) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031004);
		}
		OmcOrder updateOrder = new OmcOrder();
		updateOrder.setId(order.getId());
		updateOrder.setStatus(OmcApiConstant.OrderStatusEnum.CANCELED.getCode());

		return omcOrderMapper.updateByPrimaryKeySelective(updateOrder);
	}

	@Override
	public PageInfo queryUserOrderListWithPage(Long userId, BaseQuery baseQuery) {
		PageHelper.startPage(baseQuery.getPageNum(), baseQuery.getPageSize());
		List<OmcOrder> orderList = omcOrderMapper.selectByUserId(userId);
		List<OrderVo> orderVoList = assembleOrderVoList(orderList, userId);
		return new PageInfo<>(orderVoList);
	}

	@Override
	public boolean queryOrderPayStatus(Long userId, String orderNo) {
		OmcOrder order = omcOrderMapper.selectByUserIdAndOrderNo(userId, orderNo);
		if (order == null) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031003);
		}
		return order.getStatus() >= OmcApiConstant.OrderStatusEnum.PAID.getCode();
	}

	@Override
	public OmcOrder queryByOrderNo(String orderNo) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(orderNo), "订单号不能为空");

		return omcOrderMapper.selectByOrderNo(orderNo);
	}

	@Override
	public OmcOrder queryByUserIdAndOrderNo(Long userId, String orderNo) {
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(orderNo), "订单号不能为空");

		return omcOrderMapper.selectByUserIdAndOrderNo(userId, orderNo);
	}

	@Override
	public OrderDto queryOrderDtoByOrderNo(String orderNo) {
		OmcOrder omcOrder = this.queryByOrderNo(orderNo);
		if (omcOrder == null) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031005, orderNo);
		}
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(omcOrder, OrderDto.class);
	}

	@Override
	public OrderDto queryOrderDtoByUserIdAndOrderNo(Long userId, String orderNo) {
		OmcOrder omcOrder = this.queryByUserIdAndOrderNo(userId, orderNo);
		if (omcOrder == null) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031005, orderNo);
		}
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(omcOrder, OrderDto.class);
	}

	private BigDecimal getOrderTotalPrice(List<OmcOrderDetail> orderItemList) {
		BigDecimal payment = new BigDecimal("0");
		for (OmcOrderDetail orderItem : orderItemList) {
			payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
		}
		return payment;
	}

	private OmcOrder assembleOrder(Long userId, Long shippingId, BigDecimal payment) {
		OmcOrder order = new OmcOrder();
		long orderNo = this.generateOrderNo();
		order.setOrderNo(String.valueOf(orderNo));
		order.setStatus(OmcApiConstant.OrderStatusEnum.NO_PAY.getCode());
		order.setPostage(0);
		order.setPaymentType(GlobalConstant.PaymentTypeEnum.ONLINE_PAY.getCode());
		order.setPayment(payment);

		order.setUserId(userId);
		order.setShippingId(shippingId);
		order.setId(super.generateId());
		//发货时间等等
		//付款时间等等
		int rowCount = omcOrderMapper.insertSelective(order);
		if (rowCount > 0) {
			return order;
		}
		return null;
	}

	private long generateOrderNo() {
		return super.generateId();
	}

	private OrderVo assembleOrderVo(OmcOrder order, List<OmcOrderDetail> orderItemList) {
		OrderVo orderVo = new OrderVo();
		orderVo.setOrderNo(order.getOrderNo());
		orderVo.setPayment(order.getPayment());
		orderVo.setPaymentType(order.getPaymentType());
		orderVo.setPaymentTypeDesc(Objects.requireNonNull(GlobalConstant.PaymentTypeEnum.codeOf(order.getPaymentType())).getValue());

		orderVo.setPostage(order.getPostage());
		orderVo.setStatus(order.getStatus());
		orderVo.setStatusDesc(OmcApiConstant.OrderStatusEnum.codeOf(order.getStatus()).getValue());

		orderVo.setShippingId(order.getShippingId());
		OmcShipping shipping = omcShippingMapper.selectByPrimaryKey(order.getShippingId());
		if (shipping != null) {
			orderVo.setReceiverName(shipping.getReceiverName());
			orderVo.setShippingVo(assembleShippingVo(shipping));
		}

		orderVo.setPaymentTime(order.getPaymentTime());
		orderVo.setSendTime(order.getSendTime());
		orderVo.setEndTime(order.getEndTime());
		orderVo.setCreateTime(order.getCreatedTime());
		orderVo.setCloseTime(order.getCloseTime());
		orderVo.setCreator(order.getCreator());

		orderVo.setImageHost("");


		List<OrderItemVo> orderItemVoList = Lists.newArrayList();

		for (OmcOrderDetail orderItem : orderItemList) {
			OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
			orderItemVoList.add(orderItemVo);
		}
		orderVo.setOrderItemVoList(orderItemVoList);
		return orderVo;
	}

	private ShippingVo assembleShippingVo(OmcShipping shipping) {
		ShippingVo shippingVo = new ShippingVo();
		shippingVo.setReceiverName(shipping.getReceiverName());
		shippingVo.setReceiverAddress(shipping.getDetailAddress());
		shippingVo.setReceiverProvince(shipping.getProvinceName());
		shippingVo.setReceiverCity(shipping.getCityName());
		shippingVo.setReceiverDistrict(shipping.getDistrictName());
		shippingVo.setReceiverMobile(shipping.getReceiverMobileNo());
		shippingVo.setReceiverZip(shipping.getReceiverZipCode());
		shippingVo.setReceiverPhone(shippingVo.getReceiverPhone());
		return shippingVo;
	}

	private OrderItemVo assembleOrderItemVo(OmcOrderDetail orderItem) {
		logger.info("订单信息 orderItem={}", orderItem);
		OrderItemVo orderItemVo = new OrderItemVo();
		orderItemVo.setOrderNo(orderItem.getOrderNo());
		orderItemVo.setProductId(orderItem.getProductId());
		orderItemVo.setProductName(orderItem.getProductName());
		// 查询商品的头图
		String url = mdcProductService.getMainImage(orderItem.getProductId());
		orderItemVo.setProductImage(url);
		orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
		orderItemVo.setQuantity(orderItem.getQuantity());
		orderItemVo.setTotalPrice(orderItem.getTotalPrice());

		orderItemVo.setCreateTime(orderItem.getCreatedTime());
		return orderItemVo;
	}

	private void reduceProductStock(List<OmcOrderDetail> omcOrderDetailList) {
		for (OmcOrderDetail orderItem : omcOrderDetailList) {
			ProductDto product = mdcProductService.selectById(orderItem.getProductId());
			product.setChangeStock(0 - orderItem.getQuantity());
			mdcProductService.updateProductStockById(product);
		}
	}

	private void cleanCart(List<OmcCart> cartList) {
		List<Long> idList = Lists.newArrayList();
		for (OmcCart cart : cartList) {
			idList.add(cart.getId());
		}
		int deleteCount = omcCartMapper.batchDeleteCart(idList);
		if (deleteCount < idList.size()) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031006);
		}
	}

	private List<OrderVo> assembleOrderVoList(List<OmcOrder> orderList, Long userId) {
		List<OrderVo> orderVoList = Lists.newArrayList();
		for (OmcOrder order : orderList) {
			List<OmcOrderDetail> orderItemList;
			if (userId == null) {
				orderItemList = omcOrderDetailService.getListByOrderNo(order.getOrderNo());
			} else {
				orderItemList = omcOrderDetailService.getListByOrderNoUserId(order.getOrderNo(), userId);
			}
			OrderVo orderVo = assembleOrderVo(order, orderItemList);
			orderVoList.add(orderVo);
		}
		return orderVoList;
	}

	@Override
	public OrderVo getOrderDetail(Long userId, String orderNo) {
		logger.info("获取订单明细, userId={}, orderNo={}", userId, orderNo);
		OmcOrder order = omcOrderMapper.selectByUserIdAndOrderNo(userId, orderNo);
		if (null == order) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031005, orderNo);
		}
		List<OmcOrderDetail> orderItemList = omcOrderDetailService.getListByOrderNoUserId(orderNo, userId);
		return assembleOrderVo(order, orderItemList);
	}

	@Override
	public OrderVo getOrderDetail(final String orderNo) {
		logger.info("获取订单明细, orderNo={}", orderNo);
		OmcOrder order = omcOrderMapper.selectByOrderNo(orderNo);
		if (null == order) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031005, orderNo);
		}
		List<OmcOrderDetail> orderItemList = omcOrderDetailService.getListByOrderNo(orderNo);
		return assembleOrderVo(order, orderItemList);
	}

	@Override
	public PageInfo queryOrderListWithPage(final OrderPageQuery orderPageQuery) {
		PageHelper.startPage(orderPageQuery.getPageNum(), orderPageQuery.getPageSize());
		List<OrderDocVo> orderList = omcOrderMapper.queryOrderListWithPage(orderPageQuery);
		return new PageInfo<>(orderList);
	}
}