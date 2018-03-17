package com.paascloud.provider.model.enums;

/**
 * The enum Robot msg type enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum RobotMsgTypeEnum {
	/**
	 * 操作日志
	 */
	MARKDOWN("markdown", "markdown"),
	/**
	 * Link robot msg type enum.
	 */
	LINK("link", "link"),
	/**
	 * Text robot msg type enum.
	 */
	TEXT("text", "text"),;

	/**
	 * The Type.
	 */
	String type;
	/**
	 * The Name.
	 */
	String name;

	RobotMsgTypeEnum(String type, String name) {
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
		for (RobotMsgTypeEnum ele : RobotMsgTypeEnum.values()) {
			if (ele.getType().equals(type)) {
				return ele.getName();
			}
		}
		return null;
	}

}
