/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcCategoryCheckNameDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;


/**
 * The class Mdc category check name dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel
public class MdcCategoryCheckNameDto implements Serializable {
	private static final long serialVersionUID = 8687848883145768024L;
	/**
	 * 菜单的id
	 */
	@ApiModelProperty(value = "分类id")
	private Long categoryId;
	/**
	 * 菜单的url
	 */
	@ApiModelProperty(value = "分类名称")
	@NotBlank(message = "分类名称不能为空")
	private String categoryName;
}
