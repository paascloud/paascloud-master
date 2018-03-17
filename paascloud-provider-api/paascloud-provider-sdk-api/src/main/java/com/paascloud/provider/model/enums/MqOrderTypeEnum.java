package com.paascloud.provider.model.enums;

/**
 * The enum Mq send type enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum MqOrderTypeEnum {
	/**
	 * 有序.
	 */
	ORDER(1),
	/**
	 * 无序.
	 */
	DIS_ORDER(0);

	/**
	 * The Order type.
	 */
	int orderType;

	MqOrderTypeEnum(final int orderType) {
		this.orderType = orderType;
	}

	/**
	 * Order type int.
	 *
	 * @return the int
	 */
	public int orderType() {
		return orderType;
	}
}
