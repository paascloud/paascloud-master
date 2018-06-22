/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ReliableMessageRegisterDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.core.registry.base;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * The class Reliable message register dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class ReliableMessageRegisterDto {
	private String consumerGroup;
	private String producerGroup;
	private String namesrvAddr;

	/**
	 * Sets consumer group.
	 *
	 * @param consumerGroup the consumer group
	 *
	 * @return the consumer group
	 */
	public ReliableMessageRegisterDto setConsumerGroup(final String consumerGroup) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(consumerGroup), "init zk cid is null");
		this.consumerGroup = consumerGroup;
		return this;
	}

	/**
	 * Sets producer group.
	 *
	 * @param producerGroup the producer group
	 *
	 * @return the producer group
	 */
	public ReliableMessageRegisterDto setProducerGroup(final String producerGroup) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(producerGroup), "init zk pid is null");
		this.producerGroup = producerGroup;
		return this;
	}

	/**
	 * Sets namesrv addr.
	 *
	 * @param namesrvAddr the namesrv addr
	 *
	 * @return the namesrv addr
	 */
	public ReliableMessageRegisterDto setNamesrvAddr(final String namesrvAddr) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(namesrvAddr), "init ZK namesrvAddr is null");
		this.namesrvAddr = namesrvAddr;
		return this;
	}
}
