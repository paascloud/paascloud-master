package com.paascloud.provider.model.enums;


/**
 * The enum Mdc category status enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum MdcCategoryStatusEnum {
	/**
	 * 启用
	 */
	ENABLE(1, "启用"),
	/**
	 * 禁用
	 */
	DISABLE(2, "禁用");

	/**
	 * The Type.
	 */
	int type;
	/**
	 * The Name.
	 */
	String name;

	MdcCategoryStatusEnum(int type, String name) {
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
		for (MdcCategoryStatusEnum ele : MdcCategoryStatusEnum.values()) {
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
	public static MdcCategoryStatusEnum getEnum(int type) {
		for (MdcCategoryStatusEnum ele : MdcCategoryStatusEnum.values()) {
			if (type == ele.getType()) {
				return ele;
			}
		}
		return MdcCategoryStatusEnum.ENABLE;
	}
}
