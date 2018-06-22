/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.BaseQuery;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.OmcOrder;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.model.dto.OrderPageQuery;
import com.paascloud.provider.model.vo.OrderVo;

/**
 * The interface Omc order service.
 *
 * @author paascloud.net @gmail.com
 */
public interface OmcOrderService extends IService<OmcOrder> {

	/**
	 * 创建订单.
	 *
	 * @param loginAuthDto the login auth dto
	 * @param shippingId   the shipping id
	 *
	 * @return the order vo
	 */
	OrderVo createOrderDoc(LoginAuthDto loginAuthDto, Long shippingId);

	/**
	 * 取消订单.
	 *
	 * @param loginAuthDto the login auth dto
	 * @param orderNo      the order no
	 *
	 * @return the int
	 */
	int cancelOrderDoc(LoginAuthDto loginAuthDto, String orderNo);

	/**
	 * 查询用户订单列表.
	 *
	 * @param userId    the user id
	 * @param baseQuery the base query
	 *
	 * @return the page info
	 */
	PageInfo queryUserOrderListWithPage(Long userId, BaseQuery baseQuery);

	/**
	 * 查询订单状态.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the boolean
	 */
	boolean queryOrderPayStatus(Long userId, String orderNo);

	/**
	 * Query by order no omc order.
	 *
	 * @param orderNo the order no
	 *
	 * @return the omc order
	 */
	OmcOrder queryByOrderNo(String orderNo);

	/**
	 * Query by user id and order no omc order.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the omc order
	 */
	OmcOrder queryByUserIdAndOrderNo(Long userId, String orderNo);

	/**
	 * 根据订单号查询订单信息.
	 *
	 * @param orderNo the order no
	 *
	 * @return the order dto
	 */
	OrderDto queryOrderDtoByOrderNo(String orderNo);

	/**
	 * 根据订单号查询用户订单信息.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the order dto
	 */
	OrderDto queryOrderDtoByUserIdAndOrderNo(Long userId, String orderNo);

	/**
	 * 查询用户订单详情.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the order detail
	 */
	OrderVo getOrderDetail(Long userId, String orderNo);

	/**
	 * 查询订单详情.
	 *
	 * @param orderNo the order no
	 *
	 * @return the order detail
	 */
	OrderVo getOrderDetail(String orderNo);

	/**
	 * 分页查询订单列表.
	 *
	 * @param orderPageQuery the order page query
	 *
	 * @return the page info
	 */
	PageInfo queryOrderListWithPage(OrderPageQuery orderPageQuery);
}