package com.paascloud.provider.service;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.wrapper.Wrapper;

import java.util.Map;

/**
 * The interface Ptc alipay service.
 *
 * @author paascloud.net@gmail.com
 */
public interface PtcAlipayService {

	/**
	 * 生成付款二维码.
	 *
	 * @param orderNo      the order no
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the wrapper
	 */
	Wrapper pay(String orderNo, LoginAuthDto loginAuthDto);

	/**
	 * Ali pay callback wrapper.
	 *
	 * @param params the params
	 *
	 * @return the wrapper
	 */
	Wrapper aliPayCallback(Map<String, String> params);
}
