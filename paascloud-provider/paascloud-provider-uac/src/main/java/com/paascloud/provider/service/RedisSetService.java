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
