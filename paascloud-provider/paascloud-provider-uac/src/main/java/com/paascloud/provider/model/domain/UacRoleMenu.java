package com.paascloud.provider.model.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The class Uac role menu.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@Table(name = "pc_uac_role_menu")
@Alias(value = "uacRoleMenu")
public class UacRoleMenu implements Serializable {
	private static final long serialVersionUID = -9052683954152822756L;
	@Id
	@Column(name = "role_id")
	private Long roleId;

	@Id
	@Column(name = "menu_id")
	private Long menuId;
}