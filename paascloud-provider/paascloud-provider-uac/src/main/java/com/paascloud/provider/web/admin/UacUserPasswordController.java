/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserPasswordController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.admin;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.user.UserModifyPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用户密码.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
@Api(value = "Web - UacUserPasswordController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserPasswordController extends BaseController {
	@Resource
	private UacUserService uacUserService;

	/**
	 * 用户修改密码
	 *
	 * @param userModifyPwdDto the user modify pwd dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyUserPwd")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "用户修改密码")
	public Wrapper<Integer> modifyUserPwd(@ApiParam(name = "userModifyPwdDto", value = "用户修改密码Dto") @RequestBody UserModifyPwdDto userModifyPwdDto) {
		logger.info("==》vue用户修改密码, userModifyPwdDto={}", userModifyPwdDto);

		logger.info("旧密码 oldPassword = {}", userModifyPwdDto.getOldPassword());
		logger.info("新密码 newPassword = {}", userModifyPwdDto.getNewPassword());
		logger.info("登录名 loginName = {}", userModifyPwdDto.getLoginName());

		LoginAuthDto loginAuthDto = getLoginAuthDto();

		int result = uacUserService.userModifyPwd(userModifyPwdDto, loginAuthDto);
		return handleResult(result);
	}


	/**
	 * 注册
	 *
	 * @param registerDto the register dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/registerUser")
	@ApiOperation(httpMethod = "POST", value = "注册新用户")
	public Wrapper registerUser(@ApiParam(name = "registerDto", value = "用户注册Dto") @RequestBody UserRegisterDto registerDto) {
		logger.info("vue注册开始。注册参数：{}", registerDto);
		uacUserService.register(registerDto);
		return WrapMapper.ok("注册成功");
	}
}
