/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacRoleUserService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.domain.UacRoleUser;
import com.paascloud.core.support.IService;

import java.util.List;


/**
 * The interface Uac role user service.
 *
 * @author paascloud.net@gmail.com
 */
public interface UacRoleUserService extends IService<UacRoleUser> {
	/**
	 * 根据用户ID解除用户角色关联.
	 *
	 * @param userId the user id
	 *
	 * @return the int
	 */
	int deleteByUserId(Long userId);

	/**
	 * 根据用户ID查询绑定的角色ID列表.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacRoleUser> queryByUserId(Long userId);

	/**
	 * 用户是否绑定该角色.
	 *
	 * @param userId the user id
	 * @param roleId the role id
	 *
	 * @return the by user id and role id
	 */
	UacRoleUser getByUserIdAndRoleId(Long userId, Long roleId);

	/**
	 * 保存用户角色中间表信息.
	 *
	 * @param userId the user id
	 * @param roleId the role id
	 *
	 * @return the int
	 */
	int saveRoleUser(Long userId, Long roleId);

	/**
	 * 根据角色信息查询用户ID集合.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByRoleId(Long roleId);

	/**
	 * 根据角色ID列表查询用户ID集合.
	 *
	 * @param idList the id list
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByRoleIdList(List<Long> idList);

	/**
	 * List super user list.
	 *
	 * @param superManagerRoleId the super manager role id
	 *
	 * @return the list
	 */
	List<Long> listSuperUser(Long superManagerRoleId);

	/**
	 * Delete exclude super mng.
	 *
	 * @param roleId             the role id
	 * @param superManagerRoleId the super manager role id
	 */
	void deleteExcludeSuperMng(Long roleId, Long superManagerRoleId);

	/**
	 * 根据用户ID查询集合.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacRoleUser> listByUserId(Long userId);

	/**
	 * 根据角色ID集合解除关联关系.
	 *
	 * @param roleIdList the role id list
	 */
	void deleteByRoleIdList(List<Long> roleIdList);

	/**
	 * 根据角色ID解除关联关系.
	 *
	 * @param roleId the role id
	 */
	void deleteByRoleId(Long roleId);
}
