/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：JobTaskTypeEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

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
