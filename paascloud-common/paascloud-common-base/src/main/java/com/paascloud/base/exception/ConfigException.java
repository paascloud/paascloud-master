/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ConfigException.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.paascloud.base.exception;

/**
 * The class Config exception.
 *
 * @author paascloud.net@gmail.com
 */
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = 6480772904575978373L;

	/**
	 * Instantiates a new Config exception.
	 *
	 * @param message the message
	 */
	public ConfigException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new Config exception.
	 */
	public ConfigException() {

	}
}
