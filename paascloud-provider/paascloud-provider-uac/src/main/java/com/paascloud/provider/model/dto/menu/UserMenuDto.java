package com.paascloud.provider.model.dto.menu;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The class User menu dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class UserMenuDto implements Serializable {
	private static final long serialVersionUID = -5220054739321565548L;
	/**
	 * 一级菜单名称
	 */
	private String fistMenuName;
	/**
	 * 一级菜单图标
	 */
	private String fistMenuIcon;
	/**
	 * 一级菜单Id
	 */
	private String fistMenuId;
	/**
	 * 所有的子节点
	 */
	private List<UserMenuChildrenDto> children;
}
