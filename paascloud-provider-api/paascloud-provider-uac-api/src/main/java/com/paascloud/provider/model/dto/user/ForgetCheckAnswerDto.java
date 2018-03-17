package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Forget check answer dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class ForgetCheckAnswerDto implements Serializable {
	private static final long serialVersionUID = -4611532562847293450L;
	@ApiModelProperty(value = "登录名")
	private String loginName;
	@ApiModelProperty(value = "问题")
	private String email;
	@ApiModelProperty(value = "答案")
	private String answer;
}
