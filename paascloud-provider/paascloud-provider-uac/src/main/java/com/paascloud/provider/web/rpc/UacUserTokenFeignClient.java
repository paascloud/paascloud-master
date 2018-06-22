/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserTokenFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.rpc;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.service.UacUserTokenFeignApi;
import com.paascloud.provider.service.UacUserTokenService;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用户token.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "API - UacUserTokenFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserTokenFeignClient extends BaseController implements UacUserTokenFeignApi {
	@Resource
	private UacUserTokenService uacUserTokenService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "更新token离线状态")
	public Wrapper<Integer> updateTokenOffLine() {
		int result = uacUserTokenService.batchUpdateTokenOffLine();
		return handleResult(result);
	}
}
