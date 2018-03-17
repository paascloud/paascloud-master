package com.paascloud.provider.model.constant;

/**
 * The class Uac constant.
 *
 * @author paascloud.net @gmail.com
 */
public class UacConstant {

	/**
	 * The constant MENU_ROOT.
	 */
	public static final String MENU_ROOT = "root";

	/**
	 * token变量
	 *
	 * @author paascloud.net @gmail.com
	 */
	public static final class Token {
		/**
		 * 用户名密码加密秘钥
		 */
		public static final String SEC_TOKEN = "SEC_TOKEN";
		/**
		 * 验证码
		 */
		public static final String KAPTCHA = "KAPTCHA";

		/**
		 * The constant SMS_TOKEN.
		 */
		public static final String SMS_TOKEN = "SMS_TOKEN";
		/**
		 * The constant AUTH_TOKEN_KEY.
		 */
		public static final String AUTH_TOKEN_KEY = "PAAS_TOKEN_KEY";
		/**
		 * The constant REGISTER_TOKEN_KEY.
		 */
		public static final String REGISTER_TOKEN_KEY = "REGISTER_TOKEN_KEY";
		/**
		 * The constant RESET_PWD_TOKEN_KEY.
		 */
		public static final String RESET_PWD_TOKEN_KEY = "RESET_PWD_TOKEN_KEY";

		/**
		 * 登录人信息
		 */
		public static final String TOKEN_AUTH_DTO = "TOKEN_AUTH_DTO";

		/**
		 * The class Jwt.
		 *
		 * @author paascloud.net @gmail.com
		 */
		public static final class Jwt {
			/**
			 * The constant VIEW.
			 */
			public static final String VIEW = "JWT_VIEW_PRIVATE";
			/**
			 * The constant API.
			 */
			public static final String API = "JWT_API_PRIVATE";
		}
	}

	/**
	 * The class User.
	 *
	 * @author paascloud.net @gmail.com
	 */
	public static final class User {
		/**
		 * The constant LOGIN_NAME.
		 */
		public static final String LOGIN_NAME = "LOGIN_NAME";
	}

	/**
	 * The class Cookie.
	 *
	 * @author paascloud.net @gmail.com
	 */
	public static final class Cookie {
		/**
		 * 用户名密码加密秘钥
		 */
		public static final String PAASCLOUD_DOMAIN = "paascloud.com";
		/**
		 * token 前缀
		 */
		public static final String PAASCLOUD_PATH = "/ ";

	}
}
