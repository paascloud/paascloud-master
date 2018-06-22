/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：LoginReqDto.java
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
 * The class Login req dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "登录参数")
public class LoginReqDto implements Serializable {
	private static final long serialVersionUID = -3455057005375331843L;
	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登录名")
	private String loginName;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String loginPwd;
	/**
	 * 登录IP地址
	 */
	@ApiModelProperty(value = "ip")
	private String ip;
	/**
	 * 登录秘钥
	 */
	@ApiModelProperty(value = "登录秘钥")
	private String secToken;

	@ApiModelProperty(value = "系统ID")
	private Long applicationId;

}
  