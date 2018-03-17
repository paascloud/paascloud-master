package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class Uac user menu.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UacUserMenu extends BaseEntity {
	private static final long serialVersionUID = 71826276587527887L;
	/**
	 * pc_uac_user_menu.user_id;用户id
	 */
	private Long userId;

	/**
	 * pc_uac_user_menu.menu_id;菜单id
	 */
	private Long menuId;

	/**
	 * pc_uac_user_menu.number;序号
	 */
	private Integer number;
}