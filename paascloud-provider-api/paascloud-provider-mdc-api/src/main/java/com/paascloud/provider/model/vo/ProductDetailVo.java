/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ProductDetailVo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The class Product detail vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class ProductDetailVo implements Serializable {
	private static final long serialVersionUID = 4852861985938951261L;
	private Long id;
	private String name;
	private String subtitle;
	private String mainImage;
	private String subImages;
	private String detail;
	private BigDecimal price;
	private Integer stock;
	private Integer status;
	private String imageHost;
	private Long pid;
}
