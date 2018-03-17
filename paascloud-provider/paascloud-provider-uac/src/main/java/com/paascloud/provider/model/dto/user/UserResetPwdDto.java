package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class User reset pwd dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "用户忘记密码Dto")
public class UserResetPwdDto implements Serializable {

	private static final long serialVersionUID = 1036552024852502298L;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String mobileNo;

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

	/**
	 * 短信验证码
	 */
	@ApiModelProperty(value = "短信验证码")
	private String smsCode;

}
