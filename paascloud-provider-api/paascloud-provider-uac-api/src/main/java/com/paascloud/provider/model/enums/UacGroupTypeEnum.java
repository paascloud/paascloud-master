/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacGroupTypeEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The enum Uac group type enum.
 *
 * @author paascloud.net@gmail.com
 */
public enum UacGroupTypeEnum {


	/**
	 * Group area uac group type enum.
	 */
	GROUP_AREA("1", "大区"),
	/**
	 * Group warehouse uac group type enum.
	 */
	GROUP_WAREHOUSE("2", "仓库"),
	/**
	 * Group base uac group type enum.
	 */
	GROUP_BASE("3", "基地"),
	/**
	 * Group franchisee uac group type enum.
	 */
	GROUP_FRANCHISEE("5", "加盟商"),
	/**
	 * Group other uac group type enum.
	 */
	GROUP_OTHER("4", "其它"),;

	/**
	 * The Key.
	 */
	String key;
	/**
	 * The Value.
	 */
	String value;

	UacGroupTypeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets key.
	 *
	 * @param key the key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets value.
	 *
	 * @param value the value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取key获取value
	 *
	 * @param key key
	 *
	 * @return value value
	 */
	public static String getValue(String key) {
		for (UacGroupTypeEnum ele : UacGroupTypeEnum.values()) {
			if (key.equals(ele.getKey())) {
				return ele.getValue();
			}
		}
		return null;
	}

	/**
	 * 根据key获取该对象
	 *
	 * @param key key
	 *
	 * @return this enum
	 */
	public static UacGroupTypeEnum getEnum(String key) {
		for (UacGroupTypeEnum ele : UacGroupTypeEnum.values()) {
			if (key.equals(ele.getKey())) {
				return ele;
			}
		}
		return null;
	}

	/**
	 * 获取List集合
	 *
	 * @return List list
	 */
	public static List<UacGroupTypeEnum> getList() {
		return Arrays.asList(UacGroupTypeEnum.values());
	}


	/**
	 * 获取map类型集合
	 *
	 * @return Map类型List集合 map 2 list
	 */
	public static List<Map<String, String>> getMap2List() {
		List<Map<String, String>> list = Lists.newArrayList();
		for (UacGroupTypeEnum ele : UacGroupTypeEnum.values()) {
			Map<String, String> map = Maps.newHashMap();
			map.put("key", ele.getKey());
			map.put("value", ele.getValue());
			list.add(map);
		}
		return list;
	}
}
