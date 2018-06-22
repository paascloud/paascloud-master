/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacActionCheckCodeDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;


/**
 * The class Uac menu check code dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "UacActionCheckCodeDto")
public class UacActionCheckCodeDto implements Serializable {
	private static final long serialVersionUID = 8687848883145768024L;
	/**
	 * 权限的id
	 */
	@ApiModelProperty(value = "权限的id")
	private Long actionId;
	/**
	 * 权限的url
	 */
	@ApiModelProperty(value = "权限编码")
	@NotBlank(message = "权限编码不能为空")
	private String actionCode;


}
