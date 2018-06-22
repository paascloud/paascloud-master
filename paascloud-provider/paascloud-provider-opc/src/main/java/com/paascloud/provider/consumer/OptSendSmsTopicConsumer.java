/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OptSendSmsTopicConsumer.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.consumer;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.paascloud.JacksonUtil;
import com.paascloud.core.mq.MqMessage;
import com.paascloud.provider.service.OptSmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The class Opt send sms topic consumer.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Service
public class OptSendSmsTopicConsumer {

	@Resource
	private OptSmsService smsService;

	/**
	 * Handler send sms topic.
	 *
	 * @param body      the body
	 * @param topicName the topic name
	 * @param tags      the tags
	 * @param keys      the keys
	 */
	public void handlerSendSmsTopic(String body, String topicName, String tags, String keys) {
		MqMessage.checkMessage(body, keys, topicName);
		SendSmsRequest sendSmsRequest;
		try {
			sendSmsRequest = JacksonUtil.parseJson(body, SendSmsRequest.class);
		} catch (Exception e) {
			log.error("发送短信MQ出现异常={}", e.getMessage(), e);
			throw new IllegalArgumentException("JSON转换异常", e);
		}
		String ipAddr = sendSmsRequest.getOutId();
		if (StringUtils.isEmpty(ipAddr)) {
			throw new IllegalArgumentException("outId不能为空");
		}
		smsService.sendSms(sendSmsRequest);
	}
}
