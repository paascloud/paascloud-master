/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ForgetResetPasswordDto.java
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
 * 忘记密码.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class ForgetResetPasswordDto implements Serializable {
	private static final long serialVersionUID = 5478700873789068921L;
	@ApiModelProperty(value = "登录名")
	private String loginName;
	@ApiModelProperty(value = "密码")
	private String loginPwd;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "验证码")
	private String emailCode;
	@ApiModelProperty(value = "token")
	private String forgetToken;
}
