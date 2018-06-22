/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AdditionalParameters.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.vo.treeview;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;

/**
 * The class Additional parameters.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class AdditionalParameters {
	/**
	 * 子节点列表
	 */
	private Map<String, Item> children;

	/**
	 * 节点的Id
	 */
	private Long id;

	/**
	 * 是否有选中属性
	 */
	@JsonProperty("item-selected")
	private boolean itemSelected;

}
