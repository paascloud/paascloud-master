/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AliyunProperties.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.paascloud.config.properties;

import lombok.Data;

/**
 * The class Aliyun properties.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class AliyunProperties {
	private AliyunKeyProperties key = new AliyunKeyProperties();
	private RocketMqProperties rocketMq = new RocketMqProperties();
	private AliyunSmsProperties sms = new AliyunSmsProperties();

	@Data
	public class AliyunKeyProperties {
		/**
		 * 秘钥id
		 */
		private String accessKeyId;

		/**
		 * 秘钥
		 */
		private String accessKeySecret;
	}

	@Data
	public class RocketMqProperties {
		private String consumerGroup;
		private String producerGroup;
		private String namesrvAddr;
		/**
		 * 生产者是否使用可靠消息, 默认不使用 @MqConsumerStore
		 */
		private boolean reliableMessageProducer;
		/**
		 * 消费者是否使用可靠消息, 默认不使用 @MqProducerStore
		 */
		private boolean reliableMessageConsumer;
	}

	@Data
	public class AliyunSmsProperties {

		/**
		 * 阿里云管理控制台中配置的短信签名（状态必须是验证通过）
		 */
		private String signName;

		/**
		 * 机房信息
		 */
		private String regionId;

		/**
		 * 端点
		 */
		private String endpoint;

		/**
		 * 端点名称
		 */
		private String endpointName;

		private String product;

		private String domain;
	}
}