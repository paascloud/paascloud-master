/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderQueryFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.rpc;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.service.OmcOrderQueryFeignApi;
import com.paascloud.provider.service.OmcOrderService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Omc order query feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - OmcOrderQueryFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderQueryFeignClient extends BaseController implements OmcOrderQueryFeignApi {
	@Resource
	private OmcOrderService omcOrderService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "根据订单号查询订单信息")
	public Wrapper<OrderDto> queryByOrderNo(@PathVariable("orderNo") String orderNo) {
		logger.info("selectByOrderNo - 根据订单号查询订单信息. orderNo={}", orderNo);
		OrderDto orderDto = omcOrderService.queryOrderDtoByOrderNo(orderNo);
		return WrapMapper.ok(orderDto);
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "根据订单号查询用户订单信息")
	public Wrapper<OrderDto> queryByUserIdAndOrderNo(@PathVariable("userId") Long userId, @PathVariable("orderNo") String orderNo) {
		logger.info("selectByUserIdAndOrderNo - 根据订单号查询用户订单信息. userId={}, orderNo={}", userId, orderNo);
		OrderDto orderDto = omcOrderService.queryOrderDtoByUserIdAndOrderNo(userId, orderNo);
		return WrapMapper.ok(orderDto);
	}
}
