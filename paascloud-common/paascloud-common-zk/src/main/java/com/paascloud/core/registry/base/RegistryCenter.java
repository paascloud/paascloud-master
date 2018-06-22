package com.paascloud.core.registry.base;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.retry.RetryNTimes;

/**
 * 注册中心.
 *
 * @author zhangliang
 */
public interface RegistryCenter {

	/**
	 * 初始化注册中心.
	 */
	void init();

	/**
	 * 关闭注册中心.
	 */
	void close();

	/**
	 * 获取注册数据.
	 *
	 * @param key 键
	 *
	 * @return 值 string
	 */
	String get(String key);

	/**
	 * 获取数据是否存在.
	 *
	 * @param key 键
	 *
	 * @return 数据是否存在 boolean
	 */
	boolean isExisted(String key);

	/**
	 * 持久化注册数据.
	 *
	 * @param key   键
	 * @param value 值
	 */
	void persist(String key, String value);

	/**
	 * 创建一个持久化节点，初始内容为空.
	 *
	 * @param key the key
	 */
	void persist(String key);

	/**
	 * 更新注册数据.
	 *
	 * @param key   键
	 * @param value 值
	 */
	void update(String key, String value);

	/**
	 * 删除注册数据.
	 *
	 * @param key 键
	 */
	void remove(String key);

	/**
	 * 获取注册中心当前时间.
	 *
	 * @param key 用于获取时间的键
	 *
	 * @return 注册中心当前时间 registry center time
	 */
	long getRegistryCenterTime(String key);

	/**
	 * 直接获取操作注册中心的原生客户端.
	 * 如：Zookeeper或Redis等原生客户端.
	 *
	 * @return 注册中心的原生客户端 raw client
	 */
	Object getRawClient();

	/**
	 * Instantiates a new Increment.
	 *
	 * @param path        the path
	 * @param retryNTimes the retry n times
	 */
	void increment(String path, RetryNTimes retryNTimes);

	/**
	 * Instantiates a new Get atomic value.
	 *
	 * @param path        the path
	 * @param retryNTimes the retry n times
	 *
	 * @return the atomic value
	 */
	AtomicValue<Integer> getAtomicValue(String path, RetryNTimes retryNTimes);
}
