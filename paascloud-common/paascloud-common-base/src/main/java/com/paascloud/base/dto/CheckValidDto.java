package com.paascloud.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Check valid dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class CheckValidDto implements Serializable {
	private static final long serialVersionUID = 5178470476151416779L;
	/**
	 * 校验的参数值
	 */
	@ApiModelProperty(value = "校验参数值")
	private String validValue;

	/**
	 * 参数类型(列)
	 */
	@ApiModelProperty(value = "参数类型")
	private String type;
}
