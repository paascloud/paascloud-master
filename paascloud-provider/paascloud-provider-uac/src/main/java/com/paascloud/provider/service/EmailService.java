/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：EmailService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.email.SendEmailMessage;

/**
 * The interface Email service.
 *
 * @author paascloud.net @gmail.com
 */
public interface EmailService {
	/**
	 * 重置密码发送邮件.
	 *
	 * @param email the email
	 */
	void submitResetPwdEmail(String email);

	/**
	 * 发送验证码
	 *
	 * @param sendEmailMessage the send email message
	 * @param loginName        the login name
	 */
	void sendEmailCode(SendEmailMessage sendEmailMessage, String loginName);

	/**
	 * 校验验证码 返回 token 用户最后修改密码使用
	 *
	 * @param sendEmailMessage the send email message
	 * @param loginName        the login name
	 */
	void checkEmailCode(SendEmailMessage sendEmailMessage, String loginName);
}
