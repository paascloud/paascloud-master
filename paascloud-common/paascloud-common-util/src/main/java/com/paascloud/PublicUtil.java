/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PublicUtil.java
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
import java.util.Map;

/**
 * The class Public util.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicUtil {

	/**
	 * 判断对象是否Empty(null或元素为0)
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj 待检查对象
	 *
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null) {
			return true;
		}
		if (pObj == "") {
			return true;
		}
		if (pObj instanceof String) {
			return ((String) pObj).length() == 0;
		} else if (pObj instanceof Collection) {
			return ((Collection) pObj).isEmpty();
		} else if (pObj instanceof Map) {
			return ((Map) pObj).size() == 0;
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素大于0)
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj 待检查对象
	 *
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null) {
			return false;
		}
		if (pObj == "") {
			return false;
		}
		if (pObj instanceof String) {
			return ((String) pObj).length() != 0;
		} else if (pObj instanceof Collection) {
			return !((Collection) pObj).isEmpty();
		} else if (pObj instanceof Map) {
			return ((Map) pObj).size() != 0;
		}
		return true;
	}

}
