/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OrderProductVo.java
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
 * The class Order product vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class OrderProductVo implements Serializable {
	private static final long serialVersionUID = 9200251296138955758L;
	private List<OrderItemVo> orderItemVoList;
	private BigDecimal productTotalPrice;
	private String imageHost;
}
