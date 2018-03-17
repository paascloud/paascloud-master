package com.paascloud.security.core.properties;

/**
 * The interface Security constants.
 *
 * @author paascloud.net@gmail.com
 */
public interface SecurityConstants {


	/**
	 * 默认的处理验证码的url前缀
	 */
	String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/auth/code";

	/**
	 * 当请求需要身份认证时，默认跳转的url
	 */
	String DEFAULT_UNAUTHENTICATION_URL = "/auth/require";
	/**
	 * 默认的用户名密码登录请求处理url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/auth/form";
	/**
	 * 默认的手机验证码登录请求处理url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/auth/mobile";
	/**
	 * 默认的OPENID登录请求处理url
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/auth/openid";
	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
	/**
	 * 验证邮箱验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_CODE_EMAIL = "emailCode";
	/**
	 * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

	/**
	 * 发送邮箱验证码 或 验证邮箱验证码时，传递邮箱的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_EMAIL = "email";

	/**
	 * openid参数名
	 */
	String DEFAULT_PARAMETER_NAME_OPENID = "openId";
	/**
	 * providerId参数名
	 */
	String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
	/**
	 * 获取第三方用户信息的url
	 */
	String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";
}
