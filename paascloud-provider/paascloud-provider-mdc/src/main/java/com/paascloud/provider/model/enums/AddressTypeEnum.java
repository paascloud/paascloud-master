/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AddressTypeEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.enums;

/**
 * 地址类型枚举类
 *
 * @author paascloud.net @gmail.com
 */
public enum AddressTypeEnum {

	/**
	 * Province address type enum.
	 */
	PROVINCE("province"),
	/**
	 * City address type enum.
	 */
	CITY("city"),
	/**
	 * District address type enum.
	 */
	DISTRICT("district"),
	/**
	 * Street address type enum.
	 */
	STREET("street");

	private String type;

	AddressTypeEnum(String type) {
		this.type = type;
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
	 * Gets enum.
	 *
	 * @param type the type
	 *
	 * @return the enum
	 */
	public static AddressTypeEnum getEnum(String type) {
		for (AddressTypeEnum ele : AddressTypeEnum.values()) {
			if (ele.getType().equals(type)) {
				return ele;
			}
		}
		return null;
	}
}
