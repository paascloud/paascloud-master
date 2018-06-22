/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.service.hystrix.MdcProductFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface Mdc product feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-mdc", configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcProductFeignHystrix.class)
public interface MdcProductFeignApi {

	/**
	 * Update product stock by id int.
	 *
	 * @param productDto the product dto
	 *
	 * @return the int
	 */
	@PostMapping(value = "/api/product/updateProductStockById")
	Wrapper<Integer> updateProductStockById(@RequestBody ProductDto productDto);

	/**
	 * Gets main image.
	 *
	 * @param productId the product
	 *                  id
	 *
	 * @return the main image
	 */
	@PostMapping(value = "/api/product/getMainImage")
	Wrapper<String> getMainImage(@RequestParam("productId") Long productId);
}
