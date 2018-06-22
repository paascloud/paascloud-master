/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacRoleService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.domain.UacRole;
import com.paascloud.provider.model.dto.role.RoleBindActionDto;
import com.paascloud.provider.model.dto.role.RoleBindMenuDto;
import com.paascloud.provider.model.dto.role.RoleBindUserDto;
import com.paascloud.provider.model.dto.role.RoleBindUserReqDto;
import com.paascloud.provider.model.vo.BindAuthVo;
import com.paascloud.provider.model.vo.MenuVo;
import com.paascloud.provider.model.vo.RoleVo;
import com.paascloud.core.support.IService;

import java.util.List;


/**
 * The interface Uac role service.
 *
 * @author paascloud.net@gmail.com
 */
public interface UacRoleService extends IService<UacRole> {

	/**
	 * Find by role code uac role.
	 *
	 * @param roleCode the role code
	 *
	 * @return the uac role
	 */
	UacRole findByRoleCode(String roleCode);

	/**
	 * Query role list with page list.
	 *
	 * @param role the role
	 *
	 * @return the list
	 */
	List<RoleVo> queryRoleListWithPage(UacRole role);

	/**
	 * 根据角色删除用户.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteRoleById(Long id);

	/**
	 * 保存用户.
	 *
	 * @param role         the role
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the int
	 */
	int saveRole(UacRole role, LoginAuthDto loginAuthDto);

	/**
	 * Gets auth tree no check menu.
	 *
	 * @param roleId the role id
	 *
	 * @return the auth tree no check menu
	 */
	List<Long> getAuthTreeNoCheckMenu(Long roleId);

	/**
	 * Grant auth 4 role int.
	 *
	 * @param grantAuthRole the grant auth role
	 */
	void bindAction(RoleBindActionDto grantAuthRole);

	/**
	 * 根据角色Id获取角色信息.
	 *
	 * @param roleId the role id
	 *
	 * @return the role by id
	 */
	UacRole getRoleById(Long roleId);

	/**
	 * 角色绑定用户获取穿梭框信息.
	 *
	 * @param roleId        the role id
	 * @param currentUserId the current user id
	 *
	 * @return the role bind user dto by role id
	 */
	RoleBindUserDto getRoleBindUserDto(Long roleId, Long currentUserId);

	/**
	 * 角色绑定用户.
	 *
	 * @param roleBindUserReqDto the role bind user req dto
	 * @param loginAuthDto       the login auth dto
	 */
	void bindUser4Role(RoleBindUserReqDto roleBindUserReqDto, LoginAuthDto loginAuthDto);

	/**
	 * Find all role info by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacRole> findAllRoleInfoByUserId(Long userId);

	/**
	 * Gets auth tree by role id.
	 *
	 * @param roleId the role id
	 *
	 * @return the auth tree by role id
	 */
	BindAuthVo getActionTreeByRoleId(Long roleId);

	/**
	 * Batch delete by id list int.
	 *
	 * @param idList the id list
	 */
	void batchDeleteByIdList(List<Long> idList);

	/**
	 * Gets own auth tree by role id.
	 *
	 * @param userId the user id
	 *
	 * @return the own auth tree by role id
	 */
	List<MenuVo> getOwnAuthTree(Long userId);

	/**
	 * Bind menu.
	 *
	 * @param roleBindMenuDto the role bind menu dto
	 */
	void bindMenu(RoleBindMenuDto roleBindMenuDto);

	/**
	 * Gets menu tree by role id.
	 *
	 * @param roleId the role id
	 *
	 * @return the menu tree by role id
	 */
	BindAuthVo getMenuTreeByRoleId(Long roleId);
}
