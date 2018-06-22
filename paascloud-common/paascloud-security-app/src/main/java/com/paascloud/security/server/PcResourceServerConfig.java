/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PcResourceServerConfig.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.server;

import com.paascloud.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.paascloud.security.core.authentication.FormAuthenticationConfig;
import com.paascloud.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.paascloud.security.core.authorize.AuthorizeConfigManager;
import com.paascloud.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 资源服务器配置
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
@EnableResourceServer
public class PcResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private OAuth2WebSecurityExpressionHandler pcSecurityExpressionHandler;

	@Autowired
	private AccessDeniedHandler pcAccessDeniedHandler;

	@Autowired
	protected AuthenticationSuccessHandler pcAuthenticationSuccessHandler;

	@Autowired
	protected AuthenticationFailureHandler pcAuthenticationFailureHandler;

	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Autowired
	private SpringSocialConfigurer pcSocialSecurityConfig;

	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;

	@Autowired
	private FormAuthenticationConfig formAuthenticationConfig;

	@Autowired
	private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

	@Resource
	private DataSource dataSource;

	/**
	 * 记住我功能的token存取器配置
	 *
	 * @return the persistent token repository
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true); // 第一次启动创建
		return tokenRepository;
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		formAuthenticationConfig.configure(http);
		http.headers().frameOptions().disable();
		http.apply(validateCodeSecurityConfig)
				.and()
				.apply(smsCodeAuthenticationSecurityConfig)
				.and()
				.apply(pcSocialSecurityConfig)
				.and()
				.apply(openIdAuthenticationSecurityConfig)
				.and()
				.headers().frameOptions().disable()
				.and()
				.exceptionHandling().accessDeniedHandler(pcAccessDeniedHandler)
				.and()
				.csrf().disable();

		authorizeConfigManager.config(http.authorizeRequests());
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.expressionHandler(pcSecurityExpressionHandler);
	}
}