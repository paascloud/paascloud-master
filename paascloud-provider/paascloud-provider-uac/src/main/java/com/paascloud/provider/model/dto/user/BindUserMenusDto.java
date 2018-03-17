package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * The class Bind user menus dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class BindUserMenusDto implements Serializable {

	private static final long serialVersionUID = -5046118305639420777L;

	/**
	 * 菜单ID
	 */
	@ApiModelProperty(value = "菜单ID")
	private List<Long> menuIdList;
}
