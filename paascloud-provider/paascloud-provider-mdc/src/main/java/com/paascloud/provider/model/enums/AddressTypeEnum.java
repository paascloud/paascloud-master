package com.paascloud.provider.model.enums;

/**
 * 地址类型枚举类
 *
 * @author paascloud.net @gmail.com
 */
public enum AddressTypeEnum {

	/**
	 * Province address type enum.
	 */
	PROVINCE("province"),
	/**
	 * City address type enum.
	 */
	CITY("city"),
	/**
	 * District address type enum.
	 */
	DISTRICT("district"),
	/**
	 * Street address type enum.
	 */
	STREET("street");

	private String type;

	AddressTypeEnum(String type) {
		this.type = type;
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
	 * Gets enum.
	 *
	 * @param type the type
	 *
	 * @return the enum
	 */
	public static AddressTypeEnum getEnum(String type) {
		for (AddressTypeEnum ele : AddressTypeEnum.values()) {
			if (ele.getType().equals(type)) {
				return ele;
			}
		}
		return null;
	}
}
