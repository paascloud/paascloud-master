package com.paascloud.provider.model.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * The class Grant auth role.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class RoleBindMenuDto implements Serializable {

	private static final long serialVersionUID = -8589698204017834593L;
	/**
	 * 菜单权限
	 */
	@ApiModelProperty(value = "菜单权限")
	private Set<Long> menuIdList;
	/**
	 * 角色Id
	 */
	@ApiModelProperty(value = "角色Id")
	private Long roleId;
}
