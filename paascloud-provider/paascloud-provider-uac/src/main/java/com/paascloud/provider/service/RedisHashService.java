/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：RedisHashService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The interface Redis hash service.
 *
 * @author paascloud.net@gmail.com
 */
public interface RedisHashService {

	/**
	 * 获取所有给定字段的值
	 *
	 * @param <T>    the type parameter
	 * @param key    the key
	 * @param fields the fields
	 *
	 * @return the value by fields
	 */
	<T extends Object> List<T> getValueByFields(String key, Set<String> fields);

	/**
	 * 获取所有给定字段的值
	 *
	 * @param <T>   the type parameter
	 * @param key   the key
	 * @param field the field
	 *
	 * @return the value by field
	 */
	<T extends Object> List<T> getValueByField(String key, String field);

	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中
	 *
	 * @param key the key
	 * @param map the map
	 */
	void setValueByFields(String key, Map<String, Object> map);

	/**
	 * 删除一个或多个哈希表字段R
	 *
	 * @param key      the key
	 * @param hashKeys the hash keys
	 *
	 * @return the long
	 */
	Long removeFields(String key, String... hashKeys);
}
