/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacActionMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.UacAction;
import com.paascloud.provider.model.domain.UacMenu;
import com.paascloud.provider.model.dto.user.Perm;
import com.paascloud.provider.model.vo.ActionVo;
import com.paascloud.provider.model.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Uac action mapper.
 *
 * @author paascloud.net@gmail.com
 */
@Mapper
@Component
public interface UacActionMapper extends MyMapper<UacAction> {
	/**
	 * Find all perms list.
	 *
	 * @return the list
	 */
	List<Perm> findAllPerms();

	/**
	 * Find action code list by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<String> findActionCodeListByUserId(Long userId);

	/**
	 * Batch delete by id list int.
	 *
	 * @param deleteIdList the delete id list
	 *
	 * @return the int
	 */
	int batchDeleteByIdList(@Param("idList") List<Long> deleteIdList);

	/**
	 * Query action list with page list.
	 *
	 * @param uacAction the uac action
	 *
	 * @return the list
	 */
	List<ActionVo> queryActionListWithPage(UacAction uacAction);

	/**
	 * Delete by menu id int.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteByMenuId(@Param("menuId") Long id);

	/**
	 * Gets checked action list.
	 *
	 * @param roleId the role id
	 *
	 * @return the checked action list
	 */
	List<Long> getCheckedActionList(@Param("roleId") Long roleId);

	/**
	 * Gets own auth list.
	 *
	 * @param userId the user id
	 *
	 * @return the own auth list
	 */
	List<MenuVo> getOwnAuthList(@Param("userId") Long userId);

	/**
	 * Gets checked menu list.
	 *
	 * @param roleId the role id
	 *
	 * @return the checked menu list
	 */
	List<Long> getCheckedMenuList(@Param("roleId") Long roleId);

	/**
	 * Gets own uac action list by user id.
	 *
	 * @param userId the user id
	 *
	 * @return the own uac action list by user id
	 */
	List<UacAction> getOwnUacActionListByUserId(Long userId);

	/**
	 * 根据角色ID查询权限列表.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacAction> listActionListByRoleId(@Param("roleId") Long roleId);

	/**
	 * List action list list.
	 *
	 * @param menuList the menu list
	 *
	 * @return the list
	 */
	List<UacAction> listActionList(@Param("menuList") List<UacMenu> menuList);
}