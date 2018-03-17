package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 校验密码.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "校验新密码是否与原始密码相同Dto ")
public class CheckNewPasswordDto implements Serializable {

	private static final long serialVersionUID = 4630716723912494960L;
	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登录名")
	private String loginName;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String newPassword;
}
