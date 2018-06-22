/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMenuUrlDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Uac menu url dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "根据URL查询菜单Dto")
public class UacMenuUrlDto implements Serializable {


	private static final long serialVersionUID = 456904676277011675L;
	@ApiModelProperty(value = "系统Id", required = true)
	private String systemId;

	@ApiModelProperty(value = "用户ID", required = true)
	private Long userId;

	@ApiModelProperty(value = "url", required = true)
	private String url;
}
