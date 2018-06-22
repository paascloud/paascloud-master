/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqMessage.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The class Tpc mq message.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@Alias(value = "tpcMqMessage")
@Table(name = "pc_tpc_mq_message")
public class TpcMqMessage implements Serializable {
	private static final long serialVersionUID = -5951754367474682967L;
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 消息key
	 */
	@Column(name = "message_key")
	private String messageKey;

	/**
	 * topic
	 */
	@Column(name = "message_topic")
	private String messageTopic;

	/**
	 * tag
	 */
	@Column(name = "message_tag")
	private String messageTag;

	/**
	 * 消息类型: 10 - 生产者 ; 20 - 消费者
	 */
	@Column(name = "message_type")
	private Integer messageType;

	/**
	 * 生产者PID
	 */
	@Column(name = "producer_group")
	private String producerGroup;

	/**
	 * 延时级别 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
	 */
	@Column(name = "delay_level")
	private Integer delayLevel;

	/**
	 * 顺序类型 0有序 1无序
	 */
	@Column(name = "order_type")
	private Integer orderType;

	/**
	 * 消息状态
	 */
	@Column(name = "message_status")
	private Integer messageStatus;

	/**
	 * 消息内容
	 */
	@Column(name = "message_body")
	private String messageBody;

	/**
	 * 状态
	 */
	@Column(name = "task_status")
	private Integer taskStatus;

	/**
	 * 执行次数
	 */
	@Column(name = "resend_times")
	private Integer resendTimes;

	/**
	 * 是否死亡 0 - 活着; 1-死亡
	 */
	private Integer dead;

	/**
	 * 执行时间
	 */
	@Column(name = "next_exe_time")
	private Integer nextExeTime;

	/**
	 * 是否删除 -0 未删除 -1 已删除
	 */
	private Integer yn;

	/**
	 * 创建时间
	 */
	@Column(name = "created_time")
	private Date createdTime;
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	@Transient
	private List<Integer> preStatusList;
}