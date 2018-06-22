/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UniqueIdGenerator.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.core.generator;

/**
 * 分布式唯一ID生成器
 *
 * @author paascloud.net@gmail.com
 */
public class UniqueIdGenerator implements IdGenerator {

	/**
	 * 开始使用该算法的时间为: 2017-01-01 00:00:00
	 */
	private static final long START_TIME = 1483200000000L;

	/**
	 * worker id的bit数，最多支持8192个app和host的组合（即在N个服务器上每个服务器部署M个项目，总共部署N*M=8192）
	 */
	private static final int APP_HOST_ID_BITS = 13;
	/**
	 * 序列号，支持单节点最高1000*1024的并发
	 */
	private final static int SEQUENCE_BITS = 10;

	/**
	 * 最大的app host id，8091
	 */
	private final static long MAX_APP_HOST_ID = ~(-1L << APP_HOST_ID_BITS);

	/**
	 * 最大的序列号，1023
	 */
	private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

	/**
	 * app host编号的移位
	 */
	private final static long APP_HOST_ID_SHIFT = SEQUENCE_BITS;

	/**
	 * 时间戳的移位
	 */
	private final static long TIMESTAMP_LEFT_SHIFT = APP_HOST_ID_BITS + APP_HOST_ID_SHIFT;

	/**
	 * 该项目的app host id，对应着为某台机器上的某个项目分配的serviceId（注意区分Span中的serviceId）
	 */
	private long appHostId;

	/**
	 * 上次生成ID的时间戳
	 */
	private long lastTimestamp = -1L;

	/**
	 * 当前毫秒生成的序列
	 */
	private long sequence = 0L;

	/**
	 * 单例
	 */
	private static volatile UniqueIdGenerator idGen = null;

	/**
	 * 实例化
	 *
	 * @param appHostId the app host id
	 *
	 * @return the instance
	 */
	public static UniqueIdGenerator getInstance(long appHostId) {
		if (idGen == null) {
			synchronized (UniqueIdGenerator.class) {
				if (idGen == null) {
					idGen = new UniqueIdGenerator(appHostId);
				}
			}
		}
		return idGen;
	}

	private UniqueIdGenerator(long appHostId) {
		if (appHostId > MAX_APP_HOST_ID) {
			// zk分配的serviceId过大(基本小规模的公司不会出现这样的问题)
			throw new IllegalArgumentException(String.format("app host Id wrong: %d ", appHostId));
		}
		this.appHostId = appHostId;
	}

	/**
	 * 利用twitter的snowflake（做了些微修改）算法来实现
	 *
	 * @return the long
	 */
	@Override
	public Long nextId() {
		return this.genUniqueId();
	}

	/**
	 * 生成唯一id的具体实现
	 */
	private synchronized long genUniqueId() {
		long current = System.currentTimeMillis();

		if (current < lastTimestamp) {
			// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，出现问题返回-1
			return -1;
		}

		if (current == lastTimestamp) {
			// 如果当前生成id的时间还是上次的时间，那么对sequence序列号进行+1
			sequence = (sequence + 1) & MAX_SEQUENCE;

			if (sequence == MAX_SEQUENCE) {
				// 当前毫秒生成的序列数已经大于最大值，那么阻塞到下一个毫秒再获取新的时间戳
				current = this.nextMs(lastTimestamp);
			}
		} else {
			// 当前的时间戳已经是下一个毫秒
			sequence = 0L;
		}

		// 更新上次生成id的时间戳
		lastTimestamp = current;

		// 进行移位操作生成int64的唯一ID
		return ((current - START_TIME) << TIMESTAMP_LEFT_SHIFT)
				| (this.appHostId << APP_HOST_ID_SHIFT)
				| sequence;
	}

	/**
	 * 阻塞到下一个毫秒
	 */
	private long nextMs(long timeStamp) {
		long current = System.currentTimeMillis();
		while (current <= timeStamp) {
			current = System.currentTimeMillis();
		}
		return current;
	}

	/**
	 * Generate id long.
	 *
	 * @return the long
	 */
	public static long generateId() {
		return UniqueIdGenerator.getInstance(IncrementIdGenerator.getServiceId()).nextId();
	}
}
