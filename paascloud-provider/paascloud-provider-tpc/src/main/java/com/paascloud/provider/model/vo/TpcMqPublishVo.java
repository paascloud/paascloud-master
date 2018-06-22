/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqPublishVo.java
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

/**
 * The class Mdc mq producer vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMqPublishVo extends BaseVo {
	private static final long serialVersionUID = -5644698735373761104L;

	/**
	 * 主题编码
	 */
	private String topicCode;

	/**
	 * 主题名称
	 */
	private String topicName;

	/**
	 * 微服务名称
	 */
	private String applicationName;

	/**
	 * 城市编码
	 */
	private String producerCode;

	/**
	 * 区域编码
	 */
	private String producerName;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 预留对账的URL
	 */
	private String queryMessageUrl;
}
