package com.paascloud.provider.model.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * The enum Uac user status enum.
 *
 * @author paascloud.net@gmail.com
 */
public enum UacUserStatusEnum {

	/**
	 * Enable uac user status enum.
	 */
	/*
	 * 启用
	 */
	ENABLE("ENABLE", "启用"),
	/**
	 * 禁用
	 */
	DISABLE("DISABLE", "禁用");

	/**
	 * The Key.
	 */
	String key;
	/**
	 * The Value.
	 */
	String value;

	UacUserStatusEnum(String key, String value) {
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
	 * Gets value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获取key获取value
	 *
	 * @param key key
	 *
	 * @return value value
	 */
	public static String getValue(String key) {
		for (UacUserStatusEnum ele : UacUserStatusEnum.values()) {
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
	public static UacUserStatusEnum getEnum(String key) {
		for (UacUserStatusEnum ele : UacUserStatusEnum.values()) {
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
	public static List<UacUserStatusEnum> getList() {
		return Arrays.asList(UacUserStatusEnum.values());
	}


	/**
	 * 获取map类型集合
	 *
	 * @return Map类型List集合 map 2 list
	 */
	public static List<Map<String, String>> getMap2List() {
		List<Map<String, String>> list = Lists.newArrayList();
		for (UacUserStatusEnum ele : UacUserStatusEnum.values()) {
			Map<String, String> map = Maps.newHashMap();
			map.put("key", ele.getKey());
			map.put("value", ele.getValue());
			list.add(map);
		}
		return list;
	}
}
