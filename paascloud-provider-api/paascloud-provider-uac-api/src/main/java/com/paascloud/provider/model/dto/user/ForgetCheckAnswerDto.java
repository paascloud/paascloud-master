/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ForgetCheckAnswerDto.java
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
 * The class Forget check answer dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class ForgetCheckAnswerDto implements Serializable {
	private static final long serialVersionUID = -4611532562847293450L;
	@ApiModelProperty(value = "登录名")
	private String loginName;
	@ApiModelProperty(value = "问题")
	private String email;
	@ApiModelProperty(value = "答案")
	private String answer;
}
