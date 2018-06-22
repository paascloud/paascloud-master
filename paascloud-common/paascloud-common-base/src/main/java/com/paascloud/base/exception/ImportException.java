/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ImportException.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.paascloud.base.exception;


/**
 * The class Import exception.
 *
 * @author paascloud.net@gmail.com
 */
public class ImportException extends RuntimeException {

	private static final long serialVersionUID = -4740091660440744697L;

	/**
	 * Instantiates a new Import exception.
	 *
	 * @param message the message
	 */
	public ImportException(String message) {
		super(message);
	}
}
