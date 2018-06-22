/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacGroupUserService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.domain.UacGroup;
import com.paascloud.provider.model.domain.UacGroupUser;
import com.paascloud.core.support.IService;

import java.util.List;


/**
 * The interface Uac group user service.
 *
 * @author paascloud.net@gmail.com
 */
public interface UacGroupUserService extends IService<UacGroupUser> {
	/**
	 * 根据userId查询
	 *
	 * @param userId the user id
	 *
	 * @return the uac group user
	 */
	UacGroupUser queryByUserId(Long userId);

	/**
	 * 根据userId和version修改
	 *
	 * @param uacGroupUser the uac group user
	 *
	 * @return the int
	 */
	int updateByUserId(UacGroupUser uacGroupUser);

	/**
	 * 通过用户Id获取组织信息
	 *
	 * @param userId the user id
	 *
	 * @return the group list by user id
	 */
	List<UacGroup> getGroupListByUserId(Long userId);

	/**
	 * Save user group.
	 *
	 * @param id      the id
	 * @param groupId the group id
	 */
	void saveUserGroup(Long id, Long groupId);
}
