/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcCartServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.paascloud.BigDecimalUtil;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.exceptions.MdcBizException;
import com.paascloud.provider.exceptions.OmcBizException;
import com.paascloud.provider.mapper.OmcCartMapper;
import com.paascloud.provider.model.constant.MdcApiConstant;
import com.paascloud.provider.model.constant.OmcApiConstant;
import com.paascloud.provider.model.domain.OmcCart;
import com.paascloud.provider.model.domain.OmcOrderDetail;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.dto.oss.OptGetUrlRequest;
import com.paascloud.provider.model.vo.CartProductVo;
import com.paascloud.provider.model.vo.CartVo;
import com.paascloud.provider.model.vo.OrderItemVo;
import com.paascloud.provider.model.vo.OrderProductVo;
import com.paascloud.provider.service.MdcProductService;
import com.paascloud.provider.service.OmcCartService;
import com.paascloud.provider.service.OpcOssService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * The class Omc cart service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
public class OmcCartServiceImpl extends BaseService<OmcCart> implements OmcCartService {
	@Resource
	private OmcCartMapper omcCartMapper;
	@Resource
	private MdcProductService mdcProductService;
	@Resource
	private OpcOssService opcOssService;

	@Override
	public CartVo getCarVo(Long userId) {
		logger.info("getCarVo -  获取购物车列表 -- userId={}", userId);
		CartVo cartVo = new CartVo();
		List<OmcCart> cartList = this.selectCartListByUserId(userId);
		List<CartProductVo> cartProductVoList = Lists.newArrayList();

		BigDecimal cartTotalPrice = new BigDecimal("0");

		if (PublicUtil.isNotEmpty(cartList)) {
			for (OmcCart cartItem : cartList) {
				CartProductVo cartProductVo = new CartProductVo();
				cartProductVo.setId(cartItem.getId());
				cartProductVo.setUserId(userId);
				cartProductVo.setProductId(cartItem.getProductId());

				ProductDto product = mdcProductService.selectById(cartItem.getProductId());

				if (product == null) {
					throw new MdcBizException(ErrorCodeEnum.MDC10021004, cartItem.getProductId());
				}

				cartProductVo.setProductMainImage(product.getMainImage());
				cartProductVo.setProductName(product.getName());
				cartProductVo.setProductSubtitle(product.getSubtitle());
				cartProductVo.setProductStatus(product.getStatus());
				cartProductVo.setProductPrice(product.getPrice());
				cartProductVo.setProductStock(product.getStock());

				//判断库存
				int buyLimitCount;
				if (product.getStock() >= cartItem.getQuantity()) {
					//库存充足的时候
					buyLimitCount = cartItem.getQuantity();
					cartProductVo.setLimitQuantity(OmcApiConstant.Cart.LIMIT_NUM_SUCCESS);
				} else {
					buyLimitCount = product.getStock();
					cartProductVo.setLimitQuantity(OmcApiConstant.Cart.LIMIT_NUM_FAIL);
					//购物车中更新有效库存
					OmcCart cartForQuantity = new OmcCart();
					cartForQuantity.setId(cartItem.getId());
					cartForQuantity.setQuantity(buyLimitCount);
					omcCartMapper.updateByPrimaryKeySelective(cartForQuantity);
				}
				cartProductVo.setQuantity(buyLimitCount);
				//计算总价
				cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
				cartProductVo.setChecked(cartItem.getChecked());

				if (cartItem.getChecked() == OmcApiConstant.Cart.CHECKED) {
					//如果已经勾选,增加到整个的购物车总价中
					cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
				}
				cartProductVoList.add(cartProductVo);
			}
		}
		cartVo.setCartTotalPrice(cartTotalPrice);
		cartVo.setCartProductVoList(cartProductVoList);
		cartVo.setAllChecked(this.getAllCheckedStatus(userId));
		return cartVo;
	}

	@Override
	public List<OmcCart> selectCartListByUserId(Long userId) {
		logger.info("selectCartListByUserId - 查询购物车记录 userId={}", userId);

		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());

		OmcCart omcCart = new OmcCart();
		omcCart.setUserId(userId);
		return omcCartMapper.select(omcCart);
	}

	@Override
	public int updateCartList(List<CartProductVo> cartProductVoList) {
		logger.info("updateCartList - 更新购物车集合 cartProductVoList={}", cartProductVoList);

		LoginAuthDto loginUser = new LoginAuthDto();
		loginUser.setLoginName(GlobalConstant.Sys.SUPER_MANAGER_LOGIN_NAME);
		loginUser.setUserId(1L);
		for (CartProductVo cartProductVo : cartProductVoList) {
			Integer quantity = cartProductVo.getQuantity();
			Integer productChecked = cartProductVo.getChecked();
			Long productId = cartProductVo.getProductId();

			ProductDto productDto = mdcProductService.selectById(productId);

			if (PublicUtil.isEmpty(productDto)) {
				throw new MdcBizException(ErrorCodeEnum.MDC10021004, productId);
			}

			OmcCart omcCart = new OmcCart();
			omcCart.setUserId(loginUser.getUserId());
			omcCart.setQuantity(quantity);
			omcCart.setChecked(productChecked);
			omcCart.setProductId(productId);
			omcCart.setQuantity(quantity);

			this.saveCart(omcCart, loginUser);
		}
		return 1;
	}

	@Override
	public void saveCart(OmcCart omcCart, LoginAuthDto authDto) {
		logger.info("saveCart - 保存购物车记录 omcCart={}, userId={}", omcCart, authDto.getUserId());

		Long productId = omcCart.getProductId();
		Long userId = authDto.getUserId();
		Preconditions.checkArgument(productId != null, "货品ID不能为空");
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());

		omcCart.setUpdateInfo(authDto);
		OmcCart omcCartExist = omcCartMapper.selectByProductIdAndUserId(productId, userId);
		if (PublicUtil.isEmpty(omcCartExist)) {
			try {
				omcCartMapper.insertSelective(omcCart);
			} catch (Exception e) {
				logger.error("新增购物车, 出现异常={}", e.getMessage(), e);
			}
			return;
		}
		omcCart.setId(omcCartExist.getId());
		omcCart.setQuantity(omcCart.getQuantity() + omcCartExist.getQuantity());
		int updateResult = omcCartMapper.updateByPrimaryKeySelective(omcCart);
		if (updateResult < 1) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031014, omcCartExist.getId());
		}

	}

	@Override
	public int saveCart(Long userId, Long productId, int count) {
		logger.info("saveCart - 保存购物车记录 userId={}, productId={}, count={}", userId, productId, count);

		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.msg());
		Preconditions.checkArgument(count != 0, "数量不符");

		int resultInt = 0;
		OmcCart cart = this.getCartByUserIdAndProductId(userId, productId);
		if (cart == null) {
			cart = new OmcCart();
			cart.setQuantity(count);
			cart.setChecked(OmcApiConstant.Cart.CHECKED);
			cart.setProductId(productId);
			cart.setUserId(userId);
			resultInt = omcCartMapper.insertSelective(cart);
		} else {
			count = cart.getQuantity() == null ? 0 : cart.getQuantity() + count;
			cart.setQuantity(count);
			omcCartMapper.updateByPrimaryKeySelective(cart);
		}
		return resultInt;
	}

	@Override
	public OmcCart getCartByUserIdAndProductId(Long userId, Long productId) {
		logger.info("getCartByUserIdAndProductId - 查询购物车记录 userId={}, productId={}", userId, productId);

		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.msg());

		return omcCartMapper.selectByProductIdAndUserId(productId, userId);
	}

	@Override
	public int deleteProduct(Long userId, String productIds) {
		logger.info("deleteProduct - 删除购物车记录 userId={}, productIds={}", userId, productIds);
		Preconditions.checkArgument(StringUtils.isNotEmpty(productIds), ErrorCodeEnum.MDC10021021.msg());
		List<String> productList = Splitter.on(",").splitToList(productIds);

		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());

		return omcCartMapper.deleteByUserIdProductIds(userId, productList);
	}

	@Override
	public int selectOrUnSelect(Long userId, Long productId, int checked) {
		logger.info("selectOrUnSelect - 选中购物车记录 userId={}, productId={}, checked={}", userId, productId, checked);

		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());

		return omcCartMapper.checkedOrUncheckedProduct(userId, productId, checked);
	}

	@Override
	public int updateCart(Long userId, Long productId, int count) {
		logger.info("updateCart - 更新货品数量 userId={}, productId={}, count={}", userId, productId, count);

		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.msg());

		int resultInt;
		OmcCart cart = this.getCartByUserIdAndProductId(userId, productId);
		if (cart == null) {
			logger.error("找不到商品信息, userId={}, productId={}", userId, productId);
			throw new MdcBizException(ErrorCodeEnum.MDC10021004, productId);
		}
		if (count == 0) {
			List<String> productList = Lists.newArrayList();
			productList.add(productId.toString());
			resultInt = omcCartMapper.deleteByUserIdProductIds(userId, productList);
		} else {
			cart.setQuantity(count);
			resultInt = omcCartMapper.updateByPrimaryKeySelective(cart);
		}

		return resultInt;
	}

	@Override
	public OrderProductVo getOrderCartProduct(Long userId) {
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());

		OrderProductVo orderProductVo = new OrderProductVo();

		List<OmcCart> omcCartList = omcCartMapper.selectCheckedCartByUserId(userId);
		List<OmcOrderDetail> orderItemList = this.getCartOrderItem(userId, omcCartList);

		List<OrderItemVo> orderItemVoList = Lists.newArrayList();
		BigDecimal payment = new BigDecimal("0");
		for (OmcOrderDetail orderItem : orderItemList) {
			payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
			final OptGetUrlRequest request = new OptGetUrlRequest();
			request.setAttachmentId(Long.valueOf(orderItem.getProductImage()));
			request.setEncrypt(true);
			String fileUrl = opcOssService.getFileUrl(request);
			OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
			orderItemVo.setProductImage(fileUrl);
			orderItemVoList.add(orderItemVo);
		}

		orderProductVo.setProductTotalPrice(payment);
		orderProductVo.setOrderItemVoList(orderItemVoList);

		return orderProductVo;
	}

	private boolean getAllCheckedStatus(Long userId) {
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());
		return omcCartMapper.selectUnCheckedCartProductCountByUserId(userId) == 0;

	}

	@Override
	public List<OmcOrderDetail> getCartOrderItem(Long userId, List<OmcCart> cartList) {
		List<OmcOrderDetail> orderItemList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(cartList)) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031001, userId);
		}

		//校验购物车的数据,包括产品的状态和数量
		for (OmcCart cartItem : cartList) {
			OmcOrderDetail orderDetail = new OmcOrderDetail();
			ProductDto product = mdcProductService.selectById(cartItem.getProductId());
			if (MdcApiConstant.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
				logger.error("商品不是在线售卖状态, productId={}", product.getId());
				throw new OmcBizException(ErrorCodeEnum.MDC10021015, product.getId());
			}

			//校验库存
			if (cartItem.getQuantity() > product.getStock()) {
				logger.error("商品库存不足, productId={}", product.getId());
				throw new OmcBizException(ErrorCodeEnum.MDC10021016, product.getId());
			}

			orderDetail.setUserId(userId);
			orderDetail.setProductId(product.getId());
			orderDetail.setProductName(product.getName());
			orderDetail.setProductImage(product.getMainImage());
			orderDetail.setCurrentUnitPrice(product.getPrice());
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetail.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));
			orderItemList.add(orderDetail);
		}
		return orderItemList;
	}

	private OrderItemVo assembleOrderItemVo(OmcOrderDetail orderItem) {
		OrderItemVo orderItemVo = new OrderItemVo();
		orderItemVo.setOrderNo(orderItem.getOrderNo());
		orderItemVo.setProductId(orderItem.getProductId());
		orderItemVo.setProductName(orderItem.getProductName());
		orderItemVo.setProductImage(orderItem.getProductImage());
		orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
		orderItemVo.setQuantity(orderItem.getQuantity());
		orderItemVo.setTotalPrice(orderItem.getTotalPrice());

		orderItemVo.setCreateTime(orderItem.getCreatedTime());
		return orderItemVo;
	}
}
