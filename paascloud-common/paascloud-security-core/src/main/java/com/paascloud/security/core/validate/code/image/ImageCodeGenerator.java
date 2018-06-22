/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ImageCodeGenerator.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.core.validate.code.image;

import com.google.code.kaptcha.Producer;
import com.paascloud.security.core.properties.SecurityProperties;
import com.paascloud.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.image.BufferedImage;

/**
 * 默认的图片验证码生成器
 *
 * @author paascloud.net @gmail.com
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

	private SecurityProperties securityProperties;
	private Producer captchaProducer;

	/**
	 * 生成图片验证码.
	 *
	 * @param request the request
	 *
	 * @return the image code
	 */
	@Override
	public ImageCode generate(ServletWebRequest request) {
		String kaptchaCode = captchaProducer.createText();
		BufferedImage image = captchaProducer.createImage(kaptchaCode);
		return new ImageCode(image, kaptchaCode, securityProperties.getCode().getImage().getExpireIn());
	}

	/**
	 * Sets security properties.
	 *
	 * @param securityProperties the security properties
	 */
	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	/**
	 * Sets captcha producer.
	 *
	 * @param captchaProducer the captcha producer
	 */
	public void setCaptchaProducer(Producer captchaProducer) {
		this.captchaProducer = captchaProducer;
	}


}
