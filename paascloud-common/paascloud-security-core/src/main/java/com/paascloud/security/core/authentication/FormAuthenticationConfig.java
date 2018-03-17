package com.paascloud.security.core.authentication;

import com.paascloud.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class FormAuthenticationConfig {

	/**
	 * The Pc authentication success handler.
	 */
	protected final AuthenticationSuccessHandler pcAuthenticationSuccessHandler;

	/**
	 * The Pc authentication failure handler.
	 */
	protected final AuthenticationFailureHandler pcAuthenticationFailureHandler;

	/**
	 * Instantiates a new Form authentication config.
	 *
	 * @param pcAuthenticationSuccessHandler the pc authentication success handler
	 * @param pcAuthenticationFailureHandler the pc authentication failure handler
	 */
	@Autowired
	public FormAuthenticationConfig(AuthenticationSuccessHandler pcAuthenticationSuccessHandler, AuthenticationFailureHandler pcAuthenticationFailureHandler) {
		this.pcAuthenticationSuccessHandler = pcAuthenticationSuccessHandler;
		this.pcAuthenticationFailureHandler = pcAuthenticationFailureHandler;
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 *
	 * @throws Exception the exception
	 */
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
				.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
				.successHandler(pcAuthenticationSuccessHandler)
				.failureHandler(pcAuthenticationFailureHandler);
	}

}
