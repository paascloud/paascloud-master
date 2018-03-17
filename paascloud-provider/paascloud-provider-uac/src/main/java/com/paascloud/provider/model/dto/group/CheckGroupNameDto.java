package com.paascloud.provider.model.dto.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * The class Check group name dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class CheckGroupNameDto {

	@ApiModelProperty(value = "组织ID")
	private Long groupId;

	@ApiModelProperty(value = "组织名称")
	private String groupName;
}
