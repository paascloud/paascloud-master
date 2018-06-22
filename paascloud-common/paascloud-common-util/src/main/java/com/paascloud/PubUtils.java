/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PubUtils.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

/**
 * The class Pub utils.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PubUtils {
	/**
	 * The constant STRING_NULL.
	 */
	private final static String STRING_NULL = "-";
	/**
	 * 匹配手机号码, 支持+86和86开头
	 */
	private static final String REGX_MOBILENUM = "^((\\+86)|(86))?(13|15|17|18)\\d{9}$";

	/**
	 * 匹配邮箱帐号
	 */
	private static final String REGX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	/**
	 * 匹配手机号码（先支持13, 15, 17, 18开头的手机号码）.
	 *
	 * @param inputStr the input str
	 *
	 * @return the boolean
	 */
	public static Boolean isMobileNumber(String inputStr) {
		return !PubUtils.isNull(inputStr) && inputStr.matches(REGX_MOBILENUM);
	}

	/**
	 * 判断一个或多个对象是否为空
	 *
	 * @param values 可变参数, 要判断的一个或多个对象
	 *
	 * @return 只有要判断的一个对象都为空则返回true, 否则返回false boolean
	 */
	public static boolean isNull(Object... values) {
		if (!PubUtils.isNotNullAndNotEmpty(values)) {
			return true;
		}
		for (Object value : values) {
			boolean flag;
			if (value instanceof Object[]) {
				flag = !isNotNullAndNotEmpty((Object[]) value);
			} else if (value instanceof Collection<?>) {
				flag = !isNotNullAndNotEmpty((Collection<?>) value);
			} else if (value instanceof String) {
				flag = isOEmptyOrNull(value);
			} else {
				flag = (null == value);
			}
			if (flag) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Is o empty or null boolean.
	 *
	 * @param o the o
	 *
	 * @return boolean boolean
	 */
	private static boolean isOEmptyOrNull(Object o) {
		return o == null || isSEmptyOrNull(o.toString());
	}

	/**
	 * Is s empty or null boolean.
	 *
	 * @param s the s
	 *
	 * @return boolean boolean
	 */
	private static boolean isSEmptyOrNull(String s) {
		return trimAndNullAsEmpty(s).length() <= 0;
	}

	/**
	 * Trim and null as empty string.
	 *
	 * @param s the s
	 *
	 * @return java.lang.String string
	 */
	private static String trimAndNullAsEmpty(String s) {
		if (s != null && !s.trim().equals(STRING_NULL)) {
			return s.trim();
		} else {
			return "";
		}
		// return s == null ? "" : s.trim();
	}

	/**
	 * 判断对象数组是否为空并且数量大于0
	 *
	 * @param value the value
	 *
	 * @return boolean
	 */
	private static Boolean isNotNullAndNotEmpty(Object[] value) {
		boolean bl = false;
		if (null != value && 0 < value.length) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象集合（List,Set）是否为空并且数量大于0
	 *
	 * @param value the value
	 *
	 * @return boolean
	 */
	private static Boolean isNotNullAndNotEmpty(Collection<?> value) {
		boolean bl = false;
		if (null != value && !value.isEmpty()) {
			bl = true;
		}
		return bl;
	}

	/**
	 * Is email boolean.
	 *
	 * @param str the str
	 *
	 * @return the boolean
	 */
	public static boolean isEmail(String str) {
		boolean bl = true;
		if (isSEmptyOrNull(str) || !str.matches(REGX_EMAIL)) {
			bl = false;
		}
		return bl;
	}

	/**
	 * Uuid string.
	 *
	 * @return the string
	 */
	public synchronized static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
