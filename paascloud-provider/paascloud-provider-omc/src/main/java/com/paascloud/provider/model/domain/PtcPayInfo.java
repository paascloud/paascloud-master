/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcPayInfo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Ptc pay info.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "pc_ptc_pay_info")
@Data
public class PtcPayInfo extends BaseEntity {

	private static final long serialVersionUID = 7949091072343450552L;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 订单号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 支付平台:1-支付宝,2-微信
	 */
	@Column(name = "pay_platform")
	private Integer payPlatform;

	/**
	 * 支付宝支付流水号
	 */
	@Column(name = "platform_number")
	private String platformNumber;

	/**
	 * 支付宝支付状态
	 */
	@Column(name = "platform_status")
	private String platformStatus;

}