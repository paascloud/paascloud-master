/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：Md5Util.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * The class Md 5 util.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Md5Util {

	/**
	 * Encrypt string.
	 *
	 * @param password 密码
	 *
	 * @return the string
	 */
	public static String encrypt(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

	/**
	 * 密码是否匹配.
	 *
	 * @param rawPassword     明文
	 * @param encodedPassword 密文
	 *
	 * @return the boolean
	 */
	public static boolean matches(CharSequence rawPassword, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}

}
