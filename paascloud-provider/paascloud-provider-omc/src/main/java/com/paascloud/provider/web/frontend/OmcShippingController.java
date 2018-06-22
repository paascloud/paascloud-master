/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcShippingController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.frontend;

import com.github.pagehelper.PageInfo;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.OmcShipping;
import com.paascloud.provider.service.OmcShippingService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * The class Omc shipping controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/shipping", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - OmcShippingController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcShippingController extends BaseController {

	@Resource
	private OmcShippingService omcShippingService;

	/**
	 * 增加收货人地址.
	 *
	 * @param shipping the shipping
	 *
	 * @return the wrapper
	 */
	@PostMapping("/addShipping")
	@ApiOperation(httpMethod = "POST", value = "增加收货人地址")
	public Wrapper addShipping(OmcShipping shipping) {

		logger.info("addShipping - 增加收货人地址. shipping={}", shipping);
		int result = omcShippingService.saveShipping(getLoginAuthDto(), shipping);
		return handleResult(result);

	}

	/**
	 * 删除收货人地址.
	 *
	 * @param shippingId the shipping id
	 *
	 * @return the wrapper
	 */
	@PostMapping("/deleteShipping/{shippingId}")
	@ApiOperation(httpMethod = "POST", value = "删除收货人地址")
	public Wrapper deleteShipping(@PathVariable Integer shippingId) {
		Long userId = getLoginAuthDto().getUserId();
		logger.info("deleteShipping - 删除收货人地址. userId={}, shippingId={}", userId, shippingId);
		int result = omcShippingService.deleteShipping(userId, shippingId);
		return handleResult(result);
	}

	/**
	 * 编辑收货人地址.
	 *
	 * @param shipping the shipping
	 *
	 * @return the wrapper
	 */
	@PostMapping("/updateShipping")
	@ApiOperation(httpMethod = "POST", value = "编辑收货人地址")
	public Wrapper updateShipping(OmcShipping shipping) {
		logger.info("updateShipping - 编辑收货人地址. shipping={}", shipping);
		int result = omcShippingService.saveShipping(getLoginAuthDto(), shipping);
		return handleResult(result);
	}

	/**
	 * 设置默认收货地址.
	 *
	 * @param addressId the address id
	 *
	 * @return the default address
	 */
	@PostMapping("/setDefaultAddress/{addressId}")
	@ApiOperation(httpMethod = "POST", value = "设置默认收货地址")
	public Wrapper setDefaultAddress(@PathVariable Long addressId) {
		logger.info("updateShipping - 设置默认地址. addressId={}", addressId);
		int result = omcShippingService.setDefaultAddress(getLoginAuthDto(), addressId);
		return handleResult(result);
	}

	/**
	 * 根据Id查询收货人地址.
	 *
	 * @param shippingId the shipping id
	 *
	 * @return the wrapper
	 */
	@PostMapping("/selectShippingById/{shippingId}")
	@ApiOperation(httpMethod = "POST", value = "根据Id查询收货人地址")
	public Wrapper<OmcShipping> selectShippingById(@PathVariable Long shippingId) {
		Long userId = getLoginAuthDto().getUserId();
		logger.info("selectShippingById - 根据Id查询收货人地址. userId={}, shippingId={}", userId, shippingId);
		OmcShipping omcShipping = omcShippingService.selectByShippingIdUserId(userId, shippingId);
		return WrapMapper.ok(omcShipping);
	}

	/**
	 * 分页查询当前用户收货人地址列表.
	 *
	 * @param shipping the shipping
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryUserShippingListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询当前用户收货人地址列表")
	public Wrapper<PageInfo> queryUserShippingListWithPage(@RequestBody OmcShipping shipping) {
		Long userId = getLoginAuthDto().getUserId();
		logger.info("queryUserShippingListWithPage - 分页查询当前用户收货人地址列表.userId={} shipping={}", userId, shipping);
		PageInfo pageInfo = omcShippingService.queryListWithPageByUserId(userId, shipping.getPageNum(), shipping.getPageSize());
		return WrapMapper.ok(pageInfo);
	}

	/**
	 * 分页查询收货人地址列表.
	 *
	 * @param shipping the shipping
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryShippingListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询收货人地址列表")
	public Wrapper<PageInfo> queryShippingListWithPage(@RequestBody OmcShipping shipping) {
		logger.info("queryShippingListWithPage - 分页查询收货人地址列表. shipping={}", shipping);
		PageInfo pageInfo = omcShippingService.queryShippingListWithPage(shipping);
		return WrapMapper.ok(pageInfo);
	}

}
