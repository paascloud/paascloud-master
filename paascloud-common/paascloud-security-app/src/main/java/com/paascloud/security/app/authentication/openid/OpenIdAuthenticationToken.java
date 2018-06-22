/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpenIdAuthenticationToken.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.app.authentication.openid;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;


/**
 * The class Open id authentication token.
 *
 * @author paascloud.net@gmail.com
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	// ~ Instance fields
	// ================================================================================================

	private final Object principal;
	private String providerId;

	// ~ Constructors
	// ===================================================================================================

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
	 * will return <code>false</code>.
	 *
	 * @param openId     the open id
	 * @param providerId the provider id
	 */
	public OpenIdAuthenticationToken(String openId, String providerId) {
		super(null);
		this.principal = openId;
		this.providerId = providerId;
		setAuthenticated(false);
	}

	/**
	 * This constructor should only be used by <code>AuthenticationManager</code> or
	 * <code>AuthenticationProvider</code> implementations that are satisfied with
	 * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
	 * authentication token.
	 *
	 * @param principal   the principal
	 * @param authorities the authorities
	 */
	public OpenIdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		// must use super, as we override
		super.setAuthenticated(true);
	}

	// ~ Methods
	// ========================================================================================================

	/**
	 * Gets credentials.
	 *
	 * @return the credentials
	 */
	@Override
	public Object getCredentials() {
		return null;
	}

	/**
	 * Gets principal.
	 *
	 * @return the principal
	 */
	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	/**
	 * Gets provider id.
	 *
	 * @return the provider id
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * Sets authenticated.
	 *
	 * @param isAuthenticated the is authenticated
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	/**
	 * Erase credentials.
	 */
	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
	}
}
