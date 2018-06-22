/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：CartVo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * The class Cart vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class CartVo implements Serializable {

	private static final long serialVersionUID = -7852680577412931768L;
	private List<CartProductVo> cartProductVoList;
	private BigDecimal cartTotalPrice;
	/**
	 * 是否已经都勾选
	 */
	private Boolean allChecked;
	private String imageHost;
}
