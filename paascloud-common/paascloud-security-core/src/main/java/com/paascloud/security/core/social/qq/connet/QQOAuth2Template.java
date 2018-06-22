
/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：QQOAuth2Template.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.core.social.qq.connet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * The class Qqo auth 2 template.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

	/**
	 * Instantiates a new Qqo auth 2 template.
	 *
	 * @param clientId       the client id
	 * @param clientSecret   the client secret
	 * @param authorizeUrl   the authorize url
	 * @param accessTokenUrl the access token url
	 */
	QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}

	/**
	 * Post for access grant access grant.
	 *
	 * @param accessTokenUrl the access token url
	 * @param parameters     the parameters
	 *
	 * @return the access grant
	 */
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

		log.info("获取accessToke的响应={}", responseStr);

		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[2], "=");

		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}

	/**
	 * Create rest template rest template.
	 *
	 * @return the rest template
	 */
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

}
