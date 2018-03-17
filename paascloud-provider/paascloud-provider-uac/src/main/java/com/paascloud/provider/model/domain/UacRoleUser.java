package com.paascloud.provider.model.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The class Uac role user.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@Table(name = "pc_uac_role_user")
@Alias(value = "uacRoleUser")
public class UacRoleUser implements Serializable {
	private static final long serialVersionUID = -4598936929315554832L;
	@Id
	@Column(name = "role_id")
	private Long roleId;

	@Id
	@Column(name = "user_id")
	private Long userId;
}