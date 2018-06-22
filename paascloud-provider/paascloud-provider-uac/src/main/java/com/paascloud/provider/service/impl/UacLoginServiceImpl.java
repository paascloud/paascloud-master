/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacLoginServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.paascloud.PublicUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.provider.model.constant.UacConstant;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.user.LoginRespDto;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.model.vo.MenuVo;
import com.paascloud.provider.security.SecurityUtils;
import com.paascloud.provider.service.UacLoginService;
import com.paascloud.provider.service.UacMenuService;
import com.paascloud.provider.service.UacUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * The class Uac login service.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UacLoginServiceImpl implements UacLoginService {

	@Resource
	private UacUserService uacUserService;
	@Resource
	private UacMenuService uacMenuService;

	@Override
	public LoginRespDto loginAfter(Long applicationId) {
		LoginRespDto loginRespDto = new LoginRespDto();
		String loginName = SecurityUtils.getCurrentLoginName();
		if (StringUtils.isEmpty(loginName)) {
			log.error("操作超时, 请重新登录 loginName={}", loginName);
			Preconditions.checkArgument(StringUtils.isNotEmpty(loginName), "操作超时, 请重新登录");
		}

		UacUser uacUser = uacUserService.findByLoginName(loginName);
		if (PublicUtil.isEmpty(uacUser)) {
			log.info("找不到用户信息 loginName={}", loginName);
			throw new UacBizException(ErrorCodeEnum.UAC10011002, loginName);
		}

		LoginAuthDto loginAuthDto = this.getLoginAuthDto(uacUser);
		List<MenuVo> menuVoList = uacMenuService.getMenuVoList(uacUser.getId(), applicationId);
		if (PublicUtil.isNotEmpty(menuVoList) && UacConstant.MENU_ROOT.equals(menuVoList.get(0).getMenuCode())) {
			menuVoList = menuVoList.get(0).getSubMenu();
		}
		loginRespDto.setLoginAuthDto(loginAuthDto);
		loginRespDto.setMenuList(menuVoList);
		return loginRespDto;
	}

	private LoginAuthDto getLoginAuthDto(UacUser uacUser) {
		LoginAuthDto loginAuthDto = new LoginAuthDto();
		loginAuthDto.setUserId(uacUser.getId());
		loginAuthDto.setUserName(uacUser.getUserName());
		loginAuthDto.setLoginName(uacUser.getLoginName());
		return loginAuthDto;
	}


}
