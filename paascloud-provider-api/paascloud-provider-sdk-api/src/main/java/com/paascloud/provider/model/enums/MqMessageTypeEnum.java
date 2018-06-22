/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqMessageTypeEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.enums;

/**
 * The enum Mq message type enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum MqMessageTypeEnum {
	/**
	 * 生产者.
	 */
	PRODUCER_MESSAGE(10, "生产者"),
	/**
	 * 消费者.
	 */
	CONSUMER_MESSAGE(20, "消费者");

	private int messageType;

	private String value;

	MqMessageTypeEnum(int messageType, String value) {
		this.messageType = messageType;
		this.value = value;
	}

	/**
	 * Message type int.
	 *
	 * @return the int
	 */
	public int messageType() {
		return messageType;
	}

	/**
	 * Value string.
	 *
	 * @return the string
	 */
	public String value() {
		return value;
	}

}
