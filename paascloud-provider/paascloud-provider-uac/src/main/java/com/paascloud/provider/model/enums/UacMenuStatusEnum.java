package com.paascloud.provider.model.enums;


/**
 * The enum Uac menu status enum.
 *
 * @author paascloud.net@gmail.com
 */
public enum UacMenuStatusEnum {
	/**
	 * 启用
	 */
	ENABLE("ENABLE", "启用"),
	/**
	 * 禁用
	 */
	DISABLE("DISABLE", "禁用");

	/**
	 * The Type.
	 */
	String type;
	/**
	 * The Name.
	 */
	String name;

	UacMenuStatusEnum(String type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Gets name.
	 *
	 * @param type the type
	 *
	 * @return the name
	 */
	public static String getName(String type) {
		for (UacMenuStatusEnum ele : UacMenuStatusEnum.values()) {
			if (type.equals(ele.getType())) {
				return ele.getName();
			}
		}
		return null;
	}

	/**
	 * Gets enum.
	 *
	 * @param type the type
	 *
	 * @return the enum
	 */
	public static UacMenuStatusEnum getEnum(String type) {
		for (UacMenuStatusEnum ele : UacMenuStatusEnum.values()) {
			if (type.equals(ele.getType())) {
				return ele;
			}
		}
		return UacMenuStatusEnum.ENABLE;
	}
}
