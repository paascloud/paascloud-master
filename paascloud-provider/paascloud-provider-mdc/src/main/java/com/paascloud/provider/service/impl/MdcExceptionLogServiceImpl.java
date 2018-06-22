/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcExceptionLogServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.MdcExceptionLogMapper;
import com.paascloud.provider.model.domain.MdcExceptionLog;
import com.paascloud.provider.model.dto.GlobalExceptionLogDto;
import com.paascloud.provider.model.dto.MdcExceptionQueryDto;
import com.paascloud.provider.model.dto.robot.ChatRobotMsgDto;
import com.paascloud.provider.model.factory.ChatRobotMsgFactory;
import com.paascloud.provider.service.MdcExceptionLogService;
import com.paascloud.provider.service.OpcRpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The class Mdc exception log service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
public class MdcExceptionLogServiceImpl extends BaseService<MdcExceptionLog> implements MdcExceptionLogService {
	@Resource
	private MdcExceptionLogMapper mdcExceptionLogMapper;
	@Resource
	private TaskExecutor taskExecutor;
	@Resource
	private OpcRpcService opcRpcService;
	@Value("${paascloud.dingTalk.webhookToken.sendException}")
	private String webhookToken;

	@Override
	public void saveAndSendExceptionLog(final GlobalExceptionLogDto exceptionLogDto) {
		MdcExceptionLog exceptionLog = new ModelMapper().map(exceptionLogDto, MdcExceptionLog.class);

		exceptionLog.setId(generateId());
		exceptionLog.setCreateTime(new Date());
		mdcExceptionLogMapper.insertSelective(exceptionLog);

		taskExecutor.execute(() -> {
			if (judgeIsSend(exceptionLogDto.getProfile())) {
				String text = exceptionLog.getApplicationName() + "出现异常. 环境：" + exceptionLogDto.getProfile() + "，操作人：" + exceptionLogDto.getCreator() + ".异常类型：" + exceptionLogDto.getExceptionSimpleName();
				ChatRobotMsgDto chatRobotMsgDto = ChatRobotMsgFactory.createChatRobotTextMsg(webhookToken, text, false, null);
				opcRpcService.sendChatRobotMsg(chatRobotMsgDto);
			}
		});

	}

	@Override
	public PageInfo queryExceptionListWithPage(final MdcExceptionQueryDto mdcExceptionQueryDto) {
		PageHelper.startPage(mdcExceptionQueryDto.getPageNum(), mdcExceptionQueryDto.getPageSize());
		List<MdcExceptionLog> actionList = mdcExceptionLogMapper.queryExceptionListWithPage(mdcExceptionQueryDto);
		return new PageInfo<>(actionList);
	}

	private boolean judgeIsSend(String profile) {
		Calendar calendar = Calendar.getInstance();
		int time = calendar.get(Calendar.HOUR_OF_DAY);
		return GlobalConstant.PRO_PROFILE.equals(profile) || time >= 10 && time <= 18;
	}
}