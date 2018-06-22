/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ProductCategoryDto.java
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
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.List;

/**
 * The class Product category dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductCategoryDto implements Serializable {
	private static final long serialVersionUID = -5217129132596232253L;
	@ApiModelProperty(value = "类别ID")
	private Long categoryId;
	@ApiModelProperty(value = "类别名称")
	private String categoryName;
	@ApiModelProperty(value = "首图URL")
	private String imgUrl;

	private List<ProductCategoryDto> categoryList;

}
