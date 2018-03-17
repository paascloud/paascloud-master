package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 重置密码.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class ResetPasswordDto implements Serializable {
	private static final long serialVersionUID = 4762153981220090958L;
	@ApiModelProperty("登录名")
	private String loginName;
	@ApiModelProperty(value = "旧密码")
	private String passwordOld;
	@ApiModelProperty(value = "新密码")
	private String passwordNew;
}
