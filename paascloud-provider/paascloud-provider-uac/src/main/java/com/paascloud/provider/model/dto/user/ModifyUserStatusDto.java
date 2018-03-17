package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Modify user status dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "用户禁用/启用Dto ")
public class ModifyUserStatusDto implements Serializable {

	private static final long serialVersionUID = 1494899235149813850L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private String status;
}
