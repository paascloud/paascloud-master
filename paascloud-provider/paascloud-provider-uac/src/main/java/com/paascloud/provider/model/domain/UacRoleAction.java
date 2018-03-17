package com.paascloud.provider.model.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The class Uac role action.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@Table(name = "pc_uac_role_action")
@Alias(value = "uacRoleAction")
public class UacRoleAction implements Serializable {
	private static final long serialVersionUID = -4240611881810188284L;

	@Id
	@Column(name = "role_id")
	private Long roleId;

	@Id
	@Column(name = "action_id")
	private Long actionId;
}