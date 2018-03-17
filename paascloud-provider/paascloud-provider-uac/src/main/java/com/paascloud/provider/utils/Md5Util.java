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
