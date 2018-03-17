package com.paascloud.provider.model.dto.role;

import lombok.Data;

import java.io.Serializable;

/**
 * The class Check role code dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class CheckRoleCodeDto implements Serializable {
	private static final long serialVersionUID = 6369434659441735160L;

	private Long roleId;
	private String roleCode;
}
