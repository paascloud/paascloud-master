package com.paascloud.provider.model.enums;

import java.util.Arrays;
import java.util.List;


/**
 * The enum Uac user type enum.
 *
 * @author paascloud.net@gmail.com
 */
public enum UacUserTypeEnum {

	/**
	 * 运营
	 */
	OPERATE("1000", "运营"),;

	/**
	 * The Key.
	 */
	String key;
	/**
	 * The Value.
	 */
	String value;

	UacUserTypeEnum(String key, String value) {
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
		for (UacUserTypeEnum ele : UacUserTypeEnum.values()) {
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
	public static UacUserTypeEnum getEnum(String key) {
		for (UacUserTypeEnum ele : UacUserTypeEnum.values()) {
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
	public static List<UacUserTypeEnum> getList() {
		return Arrays.asList(UacUserTypeEnum.values());
	}

}
