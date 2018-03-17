package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Bind role dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel(value = "绑定的用户信息")
public class BindRoleDto implements Serializable {

	private static final long serialVersionUID = -3385971785265488527L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long roleId;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "角色编码")
	private String roleCode;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;
	/**
	 * 是否可以操作
	 */
	@ApiModelProperty(value = "是否可以操作")
	private boolean disabled;

}
