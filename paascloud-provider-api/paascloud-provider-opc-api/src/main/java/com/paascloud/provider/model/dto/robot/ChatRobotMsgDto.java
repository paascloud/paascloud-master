/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ChatRobotMsgDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 自定义机器人消息.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel(value = "自定义机器人消息体")
public class ChatRobotMsgDto implements Serializable {

	private static final long serialVersionUID = -9132398786027223009L;

	/**
	 * 机器人URL
	 */
	@ApiModelProperty(value = "机器人URL")
	private String webhookToken;

	/**
	 * 此消息类型为固定markdown
	 * {@link com.paascloud.provider.model.enums.RobotMsgTypeEnum}
	 */
	@ApiModelProperty(value = "首屏会话透出的展示内容", required = true)
	@JsonProperty(value = "msgtype")
	private String msgType;

	/**
	 * markdown消息体
	 */
	@ApiModelProperty(value = "markdown消息体")
	private MarkdownDto markdown;

	/**
	 * link类型
	 */
	@ApiModelProperty(value = "link类型")
	private LinkDto link;
	/**
	 * link类型
	 */
	@ApiModelProperty(value = "link类型")
	private TextDto text;
	/**
	 * link类型
	 */
	@ApiModelProperty(value = "link类型")
	private AtDto at;

}
