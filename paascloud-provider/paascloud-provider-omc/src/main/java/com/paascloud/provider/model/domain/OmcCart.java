/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcCart.java
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
 * The class Omc cart.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_omc_cart")
public class OmcCart extends BaseEntity {

	private static final long serialVersionUID = 5333646386138778574L;
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "product_id")
	private Long productId;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * 是否选择,1=已勾选,0=未勾选
	 */
	private Integer checked;
}