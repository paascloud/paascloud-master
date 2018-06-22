/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：QQConnectionFactory.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.core.social.qq.connet;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import com.paascloud.security.core.social.qq.api.QQ;

/**
 * The class Qq connection factory.
 *
 * @author paascloud.net@gmail.com
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	/**
	 * Instantiates a new Qq connection factory.
	 *
	 * @param providerId the provider id
	 * @param appId      the app id
	 * @param appSecret  the app secret
	 */
	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
