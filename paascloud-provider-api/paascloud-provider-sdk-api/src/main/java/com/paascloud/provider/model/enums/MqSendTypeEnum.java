package com.paascloud.provider.model.enums;

/**
 * The enum Mq send type enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum MqSendTypeEnum {
	/**
	 * 等待确认.
	 */
	WAIT_CONFIRM,

	/**
	 * 直接发送.
	 */
	SAVE_AND_SEND,

	/**
	 * 直接发送
	 */
	//TODO 消费切口 有问题, 日后修复 暂时不可用
	@Deprecated
	DIRECT_SEND
}
