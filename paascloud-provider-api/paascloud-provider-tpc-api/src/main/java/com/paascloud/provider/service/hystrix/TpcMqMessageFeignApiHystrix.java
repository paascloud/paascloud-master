/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqMessageFeignApiHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.TpcMqMessageDto;
import com.paascloud.provider.service.TpcMqMessageFeignApi;
import com.paascloud.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * The class Tpc mq message feign api hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
@Slf4j
public class TpcMqMessageFeignApiHystrix implements TpcMqMessageFeignApi {

	@Override
	public Wrapper saveMessageWaitingConfirm(final TpcMqMessageDto mqMessageDto) {
		log.error("saveMessageWaitingConfirm - 服务降级. mqMessageDto={}", mqMessageDto);
		return null;
	}

	@Override
	public Wrapper confirmAndSendMessage(final String messageKey) {
		return null;
	}

	@Override
	public Wrapper saveAndSendMessage(final TpcMqMessageDto mqMessageDto) {
		return null;
	}

	@Override
	public Wrapper directSendMessage(final TpcMqMessageDto mqMessageDto) {
		return null;
	}

	@Override
	public Wrapper deleteMessageByMessageKey(final String messageKey) {
		return null;
	}

	@Override
	public Wrapper confirmReceiveMessage(final String cid, final String messageKey) {
		return null;
	}

	@Override
	public Wrapper confirmConsumedMessage(final String cid, final String messageKey) {
		return null;
	}
}
