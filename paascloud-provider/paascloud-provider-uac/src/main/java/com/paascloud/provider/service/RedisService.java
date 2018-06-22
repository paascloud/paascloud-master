/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：RedisService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import java.util.concurrent.TimeUnit;

/**
 * The interface Redis service.
 *
 * @author paascloud.net@gmail.com
 */
public interface RedisService {

	/**
	 * Gets key.
	 *
	 * @param key the key
	 *
	 * @return the key
	 */
	String getKey(String key);

	/**
	 * Delete key.
	 *
	 * @param key the key
	 */
	void deleteKey(String key);

	/**
	 * Sets key.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	void setKey(String key, String value);

	/**
	 * Sets key.
	 *
	 * @param key     the key
	 * @param value   the value
	 * @param timeout the timeout
	 * @param unit    the unit
	 */
	void setKey(String key, String value, final long timeout, final TimeUnit unit);
}
