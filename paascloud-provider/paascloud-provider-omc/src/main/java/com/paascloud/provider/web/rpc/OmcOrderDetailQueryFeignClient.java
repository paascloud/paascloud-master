/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderDetailQueryFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.rpc;

import com.google.common.collect.Lists;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.OmcOrderDetail;
import com.paascloud.provider.model.dto.OrderDetailDto;
import com.paascloud.provider.service.OmcOrderDetailQueryFeignApi;
import com.paascloud.provider.service.OmcOrderDetailService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Omc order detail query feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - MallCartQueryFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderDetailQueryFeignClient extends BaseController implements OmcOrderDetailQueryFeignApi {
	@Resource
	private OmcOrderDetailService omcOrderDetailService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "获取用户订单详情")
	public Wrapper<List<OrderDetailDto>> getListByOrderNoUserId(@PathVariable("orderNo") String orderNo, @PathVariable("userId") Long userId) {
		logger.info("getListByOrderNoUserId - 获取用户订单详情. orderNo={}, userId={}", orderNo, userId);
		List<OmcOrderDetail> list = omcOrderDetailService.getListByOrderNoUserId(orderNo, userId);

		List<OrderDetailDto> orderDetailDtoList = Lists.newArrayList();

		for (OmcOrderDetail orderDetail : list) {

			OrderDetailDto orderDetailDto = new OrderDetailDto();
			BeanUtils.copyProperties(orderDetail, orderDetailDto);
			orderDetailDtoList.add(orderDetailDto);
		}

		return WrapMapper.ok(orderDetailDtoList);
	}
}
