/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MessageQueryDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.paascloud.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * The class Tpc message query dto.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class MessageQueryDto extends BaseQuery {

	private static final long serialVersionUID = 3967075132487249652L;
	/**
	 * messageKey
	 */
	@ApiModelProperty(value = "messageKey")
	private String messageKey;
	/**
	 * messageTopic
	 */
	@ApiModelProperty(value = "messageTopic")
	private String messageTopic;

	/**
	 * messageTag
	 */
	@ApiModelProperty(value = "messageTag")
	private String messageTag;
	/**
	 * 发送状态
	 */
	@ApiModelProperty(value = "发送状态")
	private String messageStatus;

	/**
	 * PID
	 */
	@ApiModelProperty(value = "PID")
	private String producerGroup;

	/**
	 * 消息类型
	 */
	@ApiModelProperty(value = "消息类型")
	private String messageType;


	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startQueryTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endQueryTime;
}
