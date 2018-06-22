/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ZookeeperProperties.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.config.properties;


import lombok.Data;

/**
 * The class Job zookeeper properties.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class ZookeeperProperties {
	/**
	 * 连接Zookeeper服务器的列表
	 * 包括IP地址和端口号
	 * 多个地址用逗号分隔
	 * 如: host1:2181,host2:2181
	 */
	private String zkAddressList;

	/**
	 * Zookeeper的命名空间
	 */
	private String namespace;

	/**
	 * 等待重试的间隔时间的初始值
	 * 单位：毫秒
	 */
	private int baseSleepTimeMilliseconds = 1000;

	/**
	 * 等待重试的间隔时间的最大值
	 * 单位：毫秒
	 */
	private int maxSleepTimeMilliseconds = 3000;

	/**
	 * 最大重试次数
	 */
	private int maxRetries = 3;

	/**
	 * 连接超时时间
	 * 单位：毫秒
	 */
	private int connectionTimeoutMilliseconds = 15000;

	/**
	 * 会话超时时间
	 * 单位：毫秒
	 */
	private int sessionTimeoutMilliseconds = 60000;

	/**
	 * 连接Zookeeper的权限令牌
	 * 缺省为不需要权限验
	 */
	private String digest;
}
