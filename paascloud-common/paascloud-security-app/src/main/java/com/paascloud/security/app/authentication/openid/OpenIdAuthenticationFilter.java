/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpenIdAuthenticationFilter.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.app.authentication.openid;

import com.paascloud.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The class Open id authentication filter.
 *
 * @author paascloud.net@gmail.com
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	// ~ Static fields/initializers
	// =====================================================================================

	private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
	private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
	private boolean postOnly = true;
	private static final String POST = "POST";

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Instantiates a new Open id authentication filter.
	 */
	OpenIdAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID, "POST"));
	}

	// ~ Methods
	// ========================================================================================================

	/**
	 * Attempt authentication authentication.
	 *
	 * @param request  the request
	 * @param response the response
	 *
	 * @return the authentication
	 *
	 * @throws AuthenticationException the authentication exception
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !POST.equals(request.getMethod())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String openid = obtainOpenId(request);
		String providerId = obtainProviderId(request);

		if (openid == null) {
			openid = "";
		}
		if (providerId == null) {
			providerId = "";
		}

		openid = openid.trim();
		providerId = providerId.trim();

		OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openid, providerId);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}


	/**
	 * 获取openId
	 *
	 * @param request the request
	 *
	 * @return the string
	 */
	protected String obtainOpenId(HttpServletRequest request) {
		return request.getParameter(openIdParameter);
	}

	/**
	 * 获取提供商id
	 *
	 * @param request the request
	 *
	 * @return the string
	 */
	protected String obtainProviderId(HttpServletRequest request) {
		return request.getParameter(providerIdParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 *
	 * @param request     that an authentication request is being created for
	 * @param authRequest the authentication request object that should have its details            set
	 */
	protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from
	 * the login request.
	 *
	 * @param openIdParameter the open id parameter
	 */
	public void setOpenIdParameter(String openIdParameter) {
		Assert.hasText(openIdParameter, "Username parameter must not be empty or null");
		this.openIdParameter = openIdParameter;
	}


	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 *
	 * @param postOnly the post only
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	/**
	 * Gets open id parameter.
	 *
	 * @return the open id parameter
	 */
	public final String getOpenIdParameter() {
		return openIdParameter;
	}

	/**
	 * Gets provider id parameter.
	 *
	 * @return the provider id parameter
	 */
	public String getProviderIdParameter() {
		return providerIdParameter;
	}

	/**
	 * Sets provider id parameter.
	 *
	 * @param providerIdParameter the provider id parameter
	 */
	public void setProviderIdParameter(String providerIdParameter) {
		this.providerIdParameter = providerIdParameter;
	}

}
