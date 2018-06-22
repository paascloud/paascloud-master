/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqMessageDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * The class Tpc mq message dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class TpcMqMessageDto implements Serializable {

	private static final long serialVersionUID = -6980935654952282538L;

	/**
	 * 生产者组
	 */
	private String producerGroup;

	/**
	 * 业务单据号
	 */
	private String refNo;

	/**
	 * 消息key
	 */
	private String messageKey;

	/**
	 * topic
	 */
	private String messageTopic;

	/**
	 * tag
	 */
	private String messageTag;

	/**
	 * 消息类型
	 */
	private Integer messageType;

	/**
	 * 消息内容
	 */
	private String messageBody;

	/**
	 * 延时消息的延时级别
	 */
	private int delayLevel;

	/**
	 * 顺序类型, 0有序 1无序
	 */
	private int orderType;
}