/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.user.BindRoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * The interface Uac user mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface UacUserMapper extends MyMapper<UacUser> {

	/**
	 * Find by login name uac user.
	 *
	 * @param loginName the login name
	 *
	 * @return the uac user
	 */
	UacUser findByLoginName(String loginName);

	/**
	 * Find by mobile no uac user.
	 *
	 * @param mobileNo the mobile no
	 *
	 * @return the uac user
	 */
	UacUser findByMobileNo(@Param("mobileNo") String mobileNo);

	/**
	 * Find by login name and login pwd uac user.
	 *
	 * @param loginNamePwdMap the login name pwd map
	 *
	 * @return the uac user
	 */
	UacUser findByLoginNameAndLoginPwd(Map<String, String> loginNamePwdMap);

	/**
	 * Select user list list.
	 *
	 * @param uacUser the uac user
	 *
	 * @return the list
	 */
	List<UacUser> selectUserList(UacUser uacUser);

	/**
	 * Select user info by user id uac user.
	 *
	 * @param userId the user id
	 *
	 * @return the uac user
	 */
	UacUser selectUserInfoByUserId(Long userId);

	/**
	 * Update uac user int.
	 *
	 * @param user the user
	 *
	 * @return the int
	 */
	int updateUacUser(UacUser user);

	/**
	 * Select all need bind role list.
	 *
	 * @param superManagerRoleId the super manager role id
	 *
	 * @return the list
	 */
	List<BindRoleDto> selectAllNeedBindRole(@Param("superManagerRoleId") Long superManagerRoleId);

	/**
	 * Find user info by login name uac user.
	 *
	 * @param loginName the login name
	 *
	 * @return the uac user
	 */
	UacUser findUserInfoByLoginName(@Param("loginName") String loginName);
}