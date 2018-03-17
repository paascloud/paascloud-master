package com.paascloud.provider.model.vo;

import com.paascloud.base.dto.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class Role vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleVo extends BaseVo {

	private static final long serialVersionUID = 3819529748816533170L;

	/**
	 * 角色编码
	 */
	private String roleCode;

	private String roleName;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 备注
	 */
	private String remark;
}