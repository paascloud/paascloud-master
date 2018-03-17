package com.paascloud.provider.service;

import com.paascloud.provider.model.domain.UacRoleAction;
import com.paascloud.core.support.IService;

import java.util.List;
import java.util.Set;

/**
 * The interface Uac role action service.
 *
 * @author paascloud.net @gmail.com
 */
public interface UacRoleActionService extends IService<UacRoleAction> {
	/**
	 * List by role id list.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacRoleAction> listByRoleId(Long roleId);

	/**
	 * Delete by role id.
	 *
	 * @param roleId the role id
	 */
	void deleteByRoleId(Long roleId);

	/**
	 * Insert.
	 *
	 * @param roleId       the role id
	 * @param actionIdList the action id list
	 */
	void insert(Long roleId, Set<Long> actionIdList);

	/**
	 * Delete by role id list int.
	 *
	 * @param roleIdList the role id list
	 *
	 * @return the int
	 */
	int deleteByRoleIdList(List<Long> roleIdList);
}
