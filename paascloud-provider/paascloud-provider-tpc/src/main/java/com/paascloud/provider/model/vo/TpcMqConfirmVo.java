/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConfirmVo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.vo;

import com.paascloud.base.dto.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The class Tpc mq confirm.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TpcMqConfirmVo extends BaseVo {
	private static final long serialVersionUID = 8497361355200965902L;

	/**
	 * 任务ID
	 */
	private Long messageId;

	/**
	 * 消息唯一标识
	 */
	private String messageKey;

	/**
	 * 消费者组编码
	 */
	private String consumerCode;

	/**
	 * 消费的数次
	 */
	private Integer consumeCount;

	/**
	 * 状态, 10 - 未确认 ; 20 - 已确认; 30 已消费
	 */
	private Integer status;
}