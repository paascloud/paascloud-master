/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcDictStatusEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.enums;


/**
 * The enum Uac menu status enum.
 *
 * @author paascloud.net @gmail.com
 */
public enum MdcDictStatusEnum {
	/**
	 * 启用
	 */
	ENABLE(10, "启用"),
	/**
	 * 禁用
	 */
	DISABLE(20, "禁用");

	/**
	 * The Type.
	 */
	int type;
	/**
	 * The Name.
	 */
	String name;

	MdcDictStatusEnum(int type, String name) {
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
		for (MdcDictStatusEnum ele : MdcDictStatusEnum.values()) {
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
	public static MdcDictStatusEnum getEnum(int type) {
		for (MdcDictStatusEnum ele : MdcDictStatusEnum.values()) {
			if (type == ele.getType()) {
				return ele;
			}
		}
		return MdcDictStatusEnum.ENABLE;
	}
}
