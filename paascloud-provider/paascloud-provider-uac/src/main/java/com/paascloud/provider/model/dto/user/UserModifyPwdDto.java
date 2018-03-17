package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class User modify pwd dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "用户修改密码Dto")
public class UserModifyPwdDto implements Serializable {

	private static final long serialVersionUID = -3933378415083541145L;
	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登录名")
	private String loginName;

	/**
	 * 原始密码
	 */
	@ApiModelProperty(value = "原始密码")
	private String oldPassword;

	/**
	 * 新密码
	 */
	@ApiModelProperty(value = "新密码")
	private String newPassword;

	/**
	 * 确认密码
	 */
	@ApiModelProperty(value = "确认密码")
	private String confirmPwd;

}
