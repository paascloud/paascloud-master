package com.paascloud.provider.model.enums;


/**
 * The enum Job task type enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum JobTaskTypeEnum {

	/**
	 * Mq send message job task type enum.
	 */
	MQ_SEND_MESSAGE("MQ_SEND_MESSAGE", "发送可靠消息"),;
	/**
	 * The Type.
	 */
	String type;

	/**
	 * The Value.
	 */
	String value;

	JobTaskTypeEnum(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String type() {
		return type;
	}

	public String value() {
		return value;
	}

}
