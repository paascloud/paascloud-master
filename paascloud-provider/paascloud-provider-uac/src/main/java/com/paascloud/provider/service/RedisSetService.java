/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：RedisSetService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import java.util.Set;

/**
 * The interface Redis set service.
 *
 * @author paascloud.net@gmail.com
 */
public interface RedisSetService {
	/**
	 * 返回集合中的所有成员
	 *
	 * @param key the key
	 *
	 * @return the all value
	 */
	Set<String> getAllValue(String key);

	/**
	 * 向集合添加一个或多个成员
	 *
	 * @param key   the key
	 * @param value the value
	 *
	 * @return the long
	 */
	Long add(String key, String... value);

	/**
	 * 移除集合中一个或多个成员
	 *
	 * @param key   the key
	 * @param value the value
	 *
	 * @return the long
	 */
	Long remove(String key, String... value);

}
