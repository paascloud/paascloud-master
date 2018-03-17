package com.paascloud.provider.model.dto.menu;

import com.paascloud.provider.model.vo.MenuVo;
import lombok.Data;

import java.io.Serializable;

/**
 * The class User menu children dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class UserMenuChildrenDto implements Serializable {
	private static final long serialVersionUID = -6279523061450477189L;
	/**
	 * 叶子节点菜单名称
	 */
	private String leafMenuName;
	/**
	 * 是否被选中,true
	 */
	private boolean checked;
	/**
	 * 跳转URL
	 */
	private String url;
	/**
	 * 叶子节点菜单Id
	 */
	private Long leafMenuId;


	/**
	 * Instantiates a new User menu children dto.
	 *
	 * @param menuVo the menu vo
	 */
	public UserMenuChildrenDto(MenuVo menuVo) {
		this.leafMenuName = menuVo.getMenuName();
		this.url = menuVo.getUrl();
		this.leafMenuId = menuVo.getId();
	}
}
