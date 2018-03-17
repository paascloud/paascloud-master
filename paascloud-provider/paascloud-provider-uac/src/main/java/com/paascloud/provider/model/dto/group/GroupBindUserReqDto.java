package com.paascloud.provider.model.dto.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * The class Group bind user req dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "GroupBindUserReqDto")
public class GroupBindUserReqDto implements Serializable {
	private static final long serialVersionUID = 89217138744995863L;

	@ApiModelProperty(value = "组织ID")
	private Long groupId;

	@ApiModelProperty(value = "用户id")
	private List<Long> userIdList;
}
