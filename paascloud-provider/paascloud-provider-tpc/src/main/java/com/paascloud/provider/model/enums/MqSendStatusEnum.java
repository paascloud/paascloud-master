/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqSendStatusEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.enums;

/**
 * The enum Mq send status enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum MqSendStatusEnum {
	/**
	 * 未发送.
	 */
	WAIT_SEND(10, "未发送"),

	/**
	 * 已发送.
	 */
	SENDING(20, "已发送"),

	/**
	 * 已完成
	 */
	FINISH(30, "已完成");

	private int sendStatus;

	private String value;

	MqSendStatusEnum(int sendStatus, String value) {
		this.sendStatus = sendStatus;
		this.value = value;
	}

	/**
	 * Confirm status int.
	 *
	 * @return the int
	 */
	public int sendStatus() {
		return sendStatus;
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
