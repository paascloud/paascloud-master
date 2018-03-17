package com.paascloud.provider.model.enums;

import java.util.Arrays;
import java.util.List;


/**
 * The enum Uac user source enum.
 *
 * @author paascloud.net@gmail.com
 */
public enum UacUserSourceEnum {

	/**
	 * 注册
	 */
	REGISTER("REGISTER", "注册"),
	/**
	 * 后台新增
	 */
	INSERT("INSERT", "后台新增"),
	/**
	 * 后台导入
	 */
	IMPORT("IMPORT", "后台导入"),
	/**
	 * 手机
	 */
	APP("APP", "手机"),;

	/**
	 * The Key.
	 */
	String key;
	/**
	 * The Value.
	 */
	String value;

	UacUserSourceEnum(String key, String value) {
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
		for (UacUserSourceEnum ele : UacUserSourceEnum.values()) {
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
	public static UacUserSourceEnum getEnum(String key) {
		for (UacUserSourceEnum ele : UacUserSourceEnum.values()) {
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
	public static List<UacUserSourceEnum> getList() {
		return Arrays.asList(UacUserSourceEnum.values());
	}
}
