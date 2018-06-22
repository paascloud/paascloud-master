/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：SpringSocialConfigurerPostProcessor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.app.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.paascloud.security.core.properties.SecurityConstants;
import com.paascloud.security.core.social.support.PcSpringSocialConfigurer;


/**
 * The class Spring social configurer post processor.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

	/**
	 * Post process before initialization object.
	 *
	 * @param bean     the bean
	 * @param beanName the bean name
	 *
	 * @return the object
	 *
	 * @throws BeansException the beans exception
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * Post process after initialization object.
	 *
	 * @param bean     the bean
	 * @param beanName the bean name
	 *
	 * @return the object
	 *
	 * @throws BeansException the beans exception
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		String pcSocialSecurityConfig = "pcSocialSecurityConfig";
		if (StringUtils.equals(beanName, pcSocialSecurityConfig)) {
			PcSpringSocialConfigurer config = (PcSpringSocialConfigurer) bean;
			config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
			return config;
		}
		return bean;
	}

}
