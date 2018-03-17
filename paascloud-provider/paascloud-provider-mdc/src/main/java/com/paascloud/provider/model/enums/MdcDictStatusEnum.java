package com.paascloud.provider.model.enums;


/**
 * The enum Uac menu status enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum MdcDictStatusEnum {
	/**
	 * 启用
	 */
	ENABLE(10, "启用"),
	/**
	 * 禁用
	 */
	DISABLE(20, "禁用");

	/**
	 * The Type.
	 */
	int type;
	/**
	 * The Name.
	 */
	String name;

	MdcDictStatusEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public int getType() {
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
	public static String getName(int type) {
		for (MdcDictStatusEnum ele : MdcDictStatusEnum.values()) {
			if (type == ele.getType()) {
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
	public static MdcDictStatusEnum getEnum(int type) {
		for (MdcDictStatusEnum ele : MdcDictStatusEnum.values()) {
			if (type == ele.getType()) {
				return ele;
			}
		}
		return MdcDictStatusEnum.ENABLE;
	}
}
