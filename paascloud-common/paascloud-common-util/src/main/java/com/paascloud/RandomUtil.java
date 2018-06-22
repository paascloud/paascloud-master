/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：RandomUtil.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

/**
 * The class Random util.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtil {

	private static final int MAX_LENGTH = 50;

	/**
	 * 生成一个随机验证码 大小写字母+数字
	 *
	 * @param length the length
	 *
	 * @return 随机验证码 string
	 */
	public static String createComplexCode(int length) {

		if (length > MAX_LENGTH) {
			length = MAX_LENGTH;
		}

		Random r = new Random();

		StringBuilder code = new StringBuilder();

		while (true) {
			if (code.length() == length) {
				break;
			}
			int tmp = r.nextInt(127);
			if (tmp < 33 || tmp == 92 || tmp == 47 || tmp == 34) {
				continue;
			}
			char x = (char) (tmp);
			if (code.toString().indexOf(x) > 0) {
				continue;
			}
			code.append(x);
		}

		return code.toString();
	}

	/**
	 * Create number code string.
	 *
	 * @param length the length
	 *
	 * @return the string
	 */
	public static String createNumberCode(int length) {
		return randomString("0123456789", length);
	}

	private static String randomString(String baseString, int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		if (length < 1) {
			length = 1;
		}

		int baseLength = baseString.length();

		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(baseLength);
			sb.append(baseString.charAt(number));
		}

		return sb.toString();
	}
}
