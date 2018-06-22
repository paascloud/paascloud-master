/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductQueryFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.provider.service.MdcProductQueryFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

/**
 * The class Mdc product query feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MdcProductQueryFeignHystrix implements MdcProductQueryFeignApi {

	@Override
	public Wrapper<ProductDetailVo> getProductDetail(final Long productId) {
		return null;
	}

	@Override
	public Wrapper<ProductDto> selectById(final Long productId) {
		return null;
	}
}
