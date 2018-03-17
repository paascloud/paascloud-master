/**
 * Project Name:cszl-framework
 * File Name:AdditionalParameters.java
 * Package Name:com.liuzm.framework.vo.jqgrid
 * Date:2016年7月2日下午3:15:30
 * Copyright (c) 2016, http://www.liuzhaoming.com All Rights Reserved.
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
