package com.paascloud.provider.model.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Modify status dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "更改状态")
public class ModifyStatusDto implements Serializable {

	private static final long serialVersionUID = 1494899235149813850L;
	/**
	 * 角色ID
	 */
	@ApiModelProperty(value = "角色ID")
	private Long id;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private String status;
}
