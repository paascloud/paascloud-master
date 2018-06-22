/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PcWebResponseExceptionTranslator.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.server;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * The class Pc web response exception translator.
 *
 * @author paascloud.net @gmail.com
 */
public class PcWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
	/**
	 * Translate response entity.
	 *
	 * @param e the e
	 *
	 * @return the response entity
	 *
	 * @throws Exception the exception
	 */
	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		return null;
	}
}
