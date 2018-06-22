/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqOrderTypeEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

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
