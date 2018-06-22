/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcPayController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.frontend;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.constant.PtcApiConstant;
import com.paascloud.provider.service.PtcAlipayService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The class Ptc pay controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/pay")
@Api(value = "WEB - PtcPayController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PtcPayController extends BaseController {
	@Resource
	private PtcAlipayService ptcAlipayService;

	/**
	 * 生成付款二维码.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping("/createQrCodeImage/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "生成付款二维码")
	public Wrapper createQrCodeImage(@PathVariable String orderNo) {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		return ptcAlipayService.pay(orderNo, loginAuthDto);
	}

	/**
	 * 支付宝回调信息.
	 *
	 * @param request the request
	 *
	 * @return the object
	 */
	@PostMapping("/alipayCallback")
	@ApiOperation(httpMethod = "POST", value = "支付宝回调信息")
	public Object alipayCallback(HttpServletRequest request) {
		logger.info("收到支付宝回调信息");
		Map<String, String> params = Maps.newHashMap();

		Map requestParams = request.getParameterMap();
		for (Object o : requestParams.keySet()) {
			String name = (String) o;
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {

				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		logger.info("支付宝回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

		//非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.

		params.remove("sign_type");
		try {
			boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());

			if (!alipayRSACheckedV2) {
				return WrapMapper.error("非法请求,验证不通过,再恶意请求我就报警找网警了");
			}
		} catch (AlipayApiException e) {
			logger.error("支付宝验证回调异常", e);
		}

		//todo 验证各种数据
		Wrapper serverResponse = ptcAlipayService.aliPayCallback(params);
		if (serverResponse.success()) {
			return PtcApiConstant.AlipayCallback.RESPONSE_SUCCESS;
		}
		return PtcApiConstant.AlipayCallback.RESPONSE_FAILED;
	}

}
