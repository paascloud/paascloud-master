/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AtDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * The class At dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel(value = "AtDto")
public class AtDto implements Serializable {
	private static final long serialVersionUID = 2344037651462081640L;
	/**
	 * 被@人的手机号
	 */
	@ApiModelProperty(value = "被@人的手机号")
	private String[] atMobiles;
	/**
	 * \@所有人时:true,否则为:false
	 */
	@ApiModelProperty(value = "@所有人时:true,否则为:false")
	private boolean isAtAll;
}
