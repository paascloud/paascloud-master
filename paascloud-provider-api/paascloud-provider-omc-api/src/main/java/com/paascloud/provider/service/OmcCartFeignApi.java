/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcCartFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.vo.CartProductVo;
import com.paascloud.provider.service.hystrix.OmcCartFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The interface Omc cart feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcCartFeignHystrix.class)
public interface OmcCartFeignApi {
	/**
	 * 更新购物车.
	 *
	 * @param cartProductVoList the cart product vo list
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/updateCartList")
	Wrapper updateCartList(@RequestBody List<CartProductVo> cartProductVoList);

	/**
	 * 添加购物车.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/addProduct")
	Wrapper addProduct(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId, @RequestParam("count") Integer count);

	/**
	 * 更新商品信息.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/updateProduct")
	Wrapper updateProduct(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId, @RequestParam("count") Integer count);

	/**
	 * 删除商品信息.
	 *
	 * @param userId     the user id
	 * @param productIds the product ids
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/deleteProduct")
	Wrapper deleteProduct(@RequestParam("userId") Long userId, @RequestParam("productIds") String productIds);

	/**
	 * 选中和反选商品.
	 *
	 * @param userId    the user id
	 * @param productId the product id
	 * @param checked   the checked
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/cart/selectOrUnSelect")
	Wrapper selectOrUnSelect(@RequestParam(name = "userId") Long userId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "checked") Integer checked);
}

