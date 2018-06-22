/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderQueryFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.service.hystrix.OmcOrderQueryFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The interface Omc order query feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcOrderQueryFeignHystrix.class)
public interface OmcOrderQueryFeignApi {
	/**
	 * Query by order no wrapper.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/order/queryByOrderNo/{orderNo}")
	Wrapper<OrderDto> queryByOrderNo(@PathVariable("orderNo") String orderNo);

	/**
	 * Query by user id and order no wrapper.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/order/queryByUserIdAndOrderNo/{userId}/{orderNo}")
	Wrapper<OrderDto> queryByUserIdAndOrderNo(@PathVariable("userId") Long userId, @PathVariable("orderNo") String orderNo);
}
