package com.paascloud.provider.model.dto.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * The class Check group code dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class CheckGroupCodeDto implements Serializable {

	private static final long serialVersionUID = -7471245927289653237L;
	@ApiModelProperty(value = "组织ID")
	private Long groupId;

	@ApiModelProperty(value = "组织code")
	private String groupCode;
}
