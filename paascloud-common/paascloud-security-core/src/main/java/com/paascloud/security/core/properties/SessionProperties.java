package com.paascloud.security.core.properties;

import lombok.Data;

/**
 * session管理相关配置项
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class SessionProperties {

	/**
	 * 同一个用户在系统中的最大session数，默认1
	 */
	private int maximumSessions = 1;
	/**
	 * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
	 */
	private boolean maxSessionsPreventsLogin;
}
