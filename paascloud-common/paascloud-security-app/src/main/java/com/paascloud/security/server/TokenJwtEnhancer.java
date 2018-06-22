 /*
  * Copyright (c) 2018. paascloud.net All Rights Reserved.
  * 项目名称：paascloud快速搭建企业级分布式微服务平台
  * 类名称：TokenJwtEnhancer.java
  * 创建人：刘兆明
  * 联系方式：paascloud.net@gmail.com
  * 开源地址: https://github.com/paascloud
  * 博客地址: http://blog.paascloud.net
  * 项目官网: http://paascloud.net
  */

 package com.paascloud.security.server;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;


/**
 * The class Token jwt enhancer.
 *
 * @author paascloud.net @gmail.com
 */
public class TokenJwtEnhancer implements TokenEnhancer {

	/**
	 * Enhance o auth 2 access token.
	 *
	 * @param accessToken          the access token
	 * @param oAuth2Authentication the o auth 2 authentication
	 *
	 * @return the o auth 2 access token
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {
		Map<String, Object> info = new HashMap<>(8);
		info.put("timestamp", System.currentTimeMillis());
		Authentication authentication = oAuth2Authentication.getUserAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			Object principal = authentication.getPrincipal();
			info.put("loginName", ((UserDetails) principal).getUsername());
		}

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

		return accessToken;
	}

}
