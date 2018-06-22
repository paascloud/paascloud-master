/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcCartFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.vo.CartProductVo;
import com.paascloud.provider.service.OmcCartFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class Omc cart feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class OmcCartFeignHystrix implements OmcCartFeignApi {
	@Override
	public Wrapper updateCartList(final List<CartProductVo> cartProductVoList) {
		return null;
	}

	@Override
	public Wrapper addProduct(final Long userId, final Long productId, final Integer count) {
		return null;
	}

	@Override
	public Wrapper updateProduct(final Long userId, final Long productId, final Integer count) {
		return null;
	}

	@Override
	public Wrapper deleteProduct(final Long userId, final String productIds) {
		return null;
	}

	@Override
	public Wrapper selectOrUnSelect(final Long userId, final Long productId, final Integer checked) {
		return null;
	}
}
