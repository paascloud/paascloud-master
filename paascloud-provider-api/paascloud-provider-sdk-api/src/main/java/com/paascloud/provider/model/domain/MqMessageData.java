/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqMessageData.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.domain;

import com.paascloud.provider.model.dto.TpcMqMessageDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * The class Mq message data.
 *
 * @author paascloud.net @gmail.com
 */
@Table(name = "pc_mq_message_data")
@Data
@NoArgsConstructor
public class MqMessageData {
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
	 * 消息内容
	 */
	@Column(name = "message_body")
	private String messageBody;

	/**
	 * 消息类型: 10 - 生产者 ; 20 - 消费者
	 */
	@Column(name = "message_type")
	private Integer messageType;

	/**
	 * 顺序类型, 0有序 1无序
	 */
	private int orderType;

	/**
	 * 消息状态
	 */
	private Integer status;

	/**
	 * 延时级别
	 */
	@Column(name = "delay_level")
	private int delayLevel;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建人ID
	 */
	@Column(name = "creator_id")
	private Long creatorId;

	/**
	 * 创建时间
	 */
	@Column(name = "created_time")
	private Date createdTime;

	/**
	 * 最近操作人
	 */
	@Column(name = "last_operator")
	private String lastOperator;

	/**
	 * 最后操作人ID
	 */
	@Column(name = "last_operator_id")
	private Long lastOperatorId;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 是否删除 -0 未删除 -1 已删除
	 */
	private Integer yn;

	/**
	 * producer group name
	 */
	@Transient
	private String producerGroup;

	public MqMessageData(final String msgBody, final String topic, final String tag, final String key) {
		this.messageBody = msgBody;
		this.messageTopic = topic;
		this.messageTag = tag;
		this.messageKey = key;
	}

	/**
	 * Gets tpc mq message dto.
	 *
	 * @return the tpc mq message dto
	 */
	@Transient
	public TpcMqMessageDto getTpcMqMessageDto() {
		TpcMqMessageDto tpcMqMessageDto = new TpcMqMessageDto();
		tpcMqMessageDto.setMessageBody(this.messageBody);
		tpcMqMessageDto.setMessageKey(this.messageKey);
		tpcMqMessageDto.setMessageTag(this.messageTag);
		tpcMqMessageDto.setMessageTopic(this.messageTopic);
		tpcMqMessageDto.setMessageType(this.messageType);
		tpcMqMessageDto.setRefNo(this.messageKey);
		tpcMqMessageDto.setDelayLevel(this.delayLevel);
		tpcMqMessageDto.setOrderType(this.orderType);
		tpcMqMessageDto.setProducerGroup(this.producerGroup);
		return tpcMqMessageDto;
	}
}