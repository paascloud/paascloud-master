/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：SmsService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.sms.SmsMessage;

import javax.servlet.http.HttpServletResponse;


/**
 * The interface Sms service.
 *
 * @author paascloud.net @gmail.com
 */
public interface SmsService {
	/**
	 * Send sms code.
	 *
	 * @param smsMessage the sms message
	 * @param ipAddr     the ip addr
	 */
	void sendSmsCode(SmsMessage smsMessage, String ipAddr);

	/**
	 * Submit reset pwd phone.
	 *
	 * @param mobile   the mobile
	 * @param response the response
	 *
	 * @return the string
	 */
	String submitResetPwdPhone(String mobile, HttpServletResponse response);
}
