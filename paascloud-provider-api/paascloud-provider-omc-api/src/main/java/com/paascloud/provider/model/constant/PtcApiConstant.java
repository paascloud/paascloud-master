/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcApiConstant.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The class Ptc api constant.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PtcApiConstant {


	/**
	 * The interface Alipay callback.
	 *
	 * @author paascloud.net@gmail.com
	 */
	public interface AlipayCallback {
		/**
		 * The constant TRADE_STATUS_WAIT_BUYER_PAY.
		 */
		String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
		/**
		 * The constant TRADE_STATUS_TRADE_SUCCESS.
		 */
		String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

		/**
		 * The constant RESPONSE_SUCCESS.
		 */
		String RESPONSE_SUCCESS = "success";
		/**
		 * The constant RESPONSE_FAILED.
		 */
		String RESPONSE_FAILED = "failed";
	}

	/**
	 * The enum Pay platform enum.
	 *
	 * @author paascloud.net@gmail.com
	 */
	public enum PayPlatformEnum {
		/**
		 * Alipay pay platform enum.
		 */
		ALIPAY(1, "支付宝");

		PayPlatformEnum(int code, String value) {
			this.code = code;
			this.value = value;
		}

		private String value;
		private int code;

		/**
		 * Gets value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets code.
		 *
		 * @return the code
		 */
		public int getCode() {
			return code;
		}
	}
}
