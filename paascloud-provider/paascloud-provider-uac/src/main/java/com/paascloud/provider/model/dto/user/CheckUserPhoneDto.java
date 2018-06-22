/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：CheckUserPhoneDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * The class Check user phone dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "校验用户电话号码唯一性Dto ")
public class CheckUserPhoneDto implements Serializable {

	private static final long serialVersionUID = 3378874756673320539L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String mobileNo;
}
