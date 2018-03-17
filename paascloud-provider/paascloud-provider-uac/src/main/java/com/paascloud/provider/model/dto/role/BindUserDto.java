package com.paascloud.provider.model.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Bind user dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel(value = "绑定的用户信息")
public class BindUserDto implements Serializable {

	private static final long serialVersionUID = -3385971785265488527L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long userId;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String mobileNo;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;
	/**
	 * 是否可以操作
	 */
	@ApiModelProperty(value = "是否可以操作")
	private boolean disabled;

}
