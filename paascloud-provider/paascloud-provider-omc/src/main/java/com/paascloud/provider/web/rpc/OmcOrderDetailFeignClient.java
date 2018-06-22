/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderDetailFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.rpc;

import com.paascloud.provider.service.OmcOrderDetailFeignApi;
import com.paascloud.core.support.BaseController;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * The class Omc order detail feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - OmcOrderDetailFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderDetailFeignClient extends BaseController implements OmcOrderDetailFeignApi {

}
