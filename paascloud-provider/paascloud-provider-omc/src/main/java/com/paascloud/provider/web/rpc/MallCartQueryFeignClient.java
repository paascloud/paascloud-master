/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallCartQueryFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.rpc;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.vo.CartVo;
import com.paascloud.provider.service.OmcCartQueryFeignApi;
import com.paascloud.provider.service.OmcCartService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Mall cart query feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - MallCartQueryFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallCartQueryFeignClient extends BaseController implements OmcCartQueryFeignApi {

	@Resource
	private OmcCartService omcCartService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "获取购物车信息")
	public Wrapper<CartVo> getCartVo(@RequestParam("userId") Long userId) {
		logger.info("getCartVo - 获取购物车信息. userId={}", userId);
		CartVo cartVo = omcCartService.getCarVo(userId);
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, cartVo);
	}
}
