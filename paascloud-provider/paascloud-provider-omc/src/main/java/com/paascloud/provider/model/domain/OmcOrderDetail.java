/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderDetail.java
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
import java.math.BigDecimal;

/**
 * The class Omc order detail.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_omc_order_detail")
public class OmcOrderDetail extends BaseEntity {

	private static final long serialVersionUID = -2167960069551022897L;
	/**
	 * 订单明细序列号
	 */
	@Column(name = "detail_no")
	private String detailNo;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "product_id")
	private Long productId;

	/**
	 * 商品名称
	 */
	@Column(name = "product_name")
	private String productName;

	/**
	 * 商品图片地址
	 */
	@Column(name = "product_image")
	private String productImage;

	/**
	 * 生成订单时的商品单价, 单位是元,保留两位小数
	 */
	@Column(name = "current_unit_price")
	private BigDecimal currentUnitPrice;

	/**
	 * 商品数量
	 */
	private Integer quantity;

	/**
	 * 商品总价,单位是元,保留两位小数
	 */
	@Column(name = "total_price")
	private BigDecimal totalPrice;
}