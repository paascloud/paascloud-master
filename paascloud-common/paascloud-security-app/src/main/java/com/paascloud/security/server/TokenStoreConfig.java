 /*
  * Copyright (c) 2018. paascloud.net All Rights Reserved.
  * 项目名称：paascloud快速搭建企业级分布式微服务平台
  * 类名称：TokenStoreConfig.java
  * 创建人：刘兆明
  * 联系方式：paascloud.net@gmail.com
  * 开源地址: https://github.com/paascloud
  * 博客地址: http://blog.paascloud.net
  * 项目官网: http://paascloud.net
  */

 package com.paascloud.security.server;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.paascloud.security.core.properties.SecurityProperties;


/**
 * The class Token store config.
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
public class TokenStoreConfig {

	/**
	 * 使用redis存储token的配置，只有在paascloud.security.oauth2.tokenStore配置为redis时生效
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "paascloud.security.oauth2", name = "tokenStore", havingValue = "redis")
	public static class RedisConfig {

		@Autowired
		private RedisConnectionFactory redisConnectionFactory;

		/**
		 * Redis token store token store.
		 *
		 * @return token store
		 */
		@Bean
		public TokenStore redisTokenStore() {
			return new RedisTokenStore(redisConnectionFactory);
		}

	}

	/**
	 * 使用jwt时的配置，默认生效
	 *
	 * @author paascloud.net @gmail.com
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "paascloud.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
	public static class JwtConfig {

		@Autowired
		private SecurityProperties securityProperties;

		/**
		 * Jwt token store token store.
		 *
		 * @return the token store
		 */
		@Bean
		public TokenStore jwtTokenStore() {
			return new JwtTokenStore(jwtAccessTokenConverter());
		}

		/**
		 * Jwt access token converter jwt access token converter.
		 *
		 * @return the jwt access token converter
		 */
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
			return converter;
		}

		/**
		 * Jwt token enhancer token enhancer.
		 *
		 * @return the token enhancer
		 */
		@Bean
		@ConditionalOnBean(TokenEnhancer.class)
		public TokenEnhancer jwtTokenEnhancer() {
			return new TokenJwtEnhancer();
		}

	}


}
