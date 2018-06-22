/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ValidateCodeBeanConfig.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.core.validate.code;

import com.google.code.kaptcha.Producer;
import com.paascloud.security.core.properties.SecurityProperties;
import com.paascloud.security.core.validate.code.email.DefaultEmailCodeSender;
import com.paascloud.security.core.validate.code.email.EmailCodeSender;
import com.paascloud.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.paascloud.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paascloud.security.core.validate.code.image.ImageCodeGenerator;

/**
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
public class ValidateCodeBeanConfig {

	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private Producer captchaProducer;

	/**
	 * 图片验证码图片生成器
	 *
	 * @return validate code generator
	 */
	@Bean
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		codeGenerator.setCaptchaProducer(captchaProducer);
		return codeGenerator;
	}

	/**
	 * 短信验证码发送器
	 *
	 * @return sms code sender
	 */
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}

	/**
	 * 邮箱验证码发送器
	 *
	 * @return sms code sender
	 */
	@Bean
	@ConditionalOnMissingBean(EmailCodeSender.class)
	public EmailCodeSender emailCodeSender() {
		return new DefaultEmailCodeSender();
	}

}
