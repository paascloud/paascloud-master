/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OrderDocVo.java
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

import java.math.BigDecimal;
import java.util.Date;

/**
 * The class Order doc vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDocVo extends BaseVo {

	private static final long serialVersionUID = 4282588127249930700L;
	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 收货人
	 */
	private String receiverName;

	/**
	 * 实际付款金额,单位是元,保留两位小数
	 */
	private BigDecimal payment;

	/**
	 * 支付类型,1-在线支付
	 */
	private Integer paymentType;

	/**
	 * 订单状态:0-已取消-10-未付款, 20-已付款, 40-已发货, 50-交易成功, 60-交易关闭
	 */
	private Integer status;

	/**
	 * 支付时间
	 */
	private Date paymentTime;

	/**
	 * 发货时间
	 */
	private Date sendTime;

	/**
	 * 交易完成时间
	 */
	private Date endTime;

	/**
	 * 交易关闭时间
	 */
	private Date closeTime;
}
