/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqMessageService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.base.dto.MqMessageVo;
import com.paascloud.provider.model.domain.MqMessageData;
import com.paascloud.provider.model.enums.MqSendTypeEnum;
import com.paascloud.wrapper.Wrapper;

import java.util.List;

/**
 * The interface Tpc mq message feign api.
 *
 * @author paascloud.net @gmail.com
 */
public interface MqMessageService {

	/**
	 * 保存生产者信息.
	 *
	 * @param mqMessageData the mq message data
	 */
	void saveMqProducerMessage(MqMessageData mqMessageData);

	/**
	 * Confirm and send message.
	 *
	 * @param messageKey the message key
	 */
	void confirmAndSendMessage(String messageKey);

	/**
	 * 保存消费者信息.
	 *
	 * @param mqMessageData the mq message data
	 */
	void saveMqConsumerMessage(MqMessageData mqMessageData);

	/**
	 * 根据messageKey删除消息记录.
	 *
	 * @param messageKey the message key
	 * @param type       the type
	 */
	void deleteMessageByMessageKey(String messageKey, MqSendTypeEnum type);

	/**
	 * Confirm receive message.
	 *
	 * @param cid 消费者分组id
	 * @param dto the dto
	 */
	void confirmReceiveMessage(String cid, MqMessageData dto);

	/**
	 * Save and confirm finish message.
	 *
	 * @param cid        消费者分组id
	 * @param messageKey the message key
	 */
	void saveAndConfirmFinishMessage(String cid, String messageKey);

	/**
	 * Delete mq producer message.
	 *
	 * @param shardingTotalCount the sharding total count
	 * @param shardingItem       the sharding item
	 * @param tags               the tags
	 */
	void deleteMqMessage(int shardingTotalCount, int shardingItem, String tags);

	/**
	 * Delete message topic.
	 *
	 * @param body the body
	 * @param tags the tags
	 */
	void deleteMessageTopic(String body, String tags);

	/**
	 * 查询含有的messageKey.
	 *
	 * @param messageKeyList the message key list
	 *
	 * @return the wrapper
	 */
	List<String> queryMessageKeyList(List<String> messageKeyList);

	/**
	 * 保存等待确认的消息(前置拦截器).
	 *
	 * @param mqMessageData the mq message data
	 */
	void saveWaitConfirmMessage(MqMessageData mqMessageData);


	/**
	 * 保存并发送消息(后置拦截器).
	 *
	 * @param mqMessageData the mq message data
	 */
	void saveAndSendMessage(MqMessageData mqMessageData);

	/**
	 * 直接发送消息(后置拦截器).
	 *
	 * @param mqMessageData the mq message data
	 */
	void directSendMessage(MqMessageData mqMessageData);

	/**
	 * Query message list with page wrapper.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the wrapper
	 */
	Wrapper<PageInfo<MqMessageVo>> queryMessageListWithPage(MessageQueryDto messageQueryDto);
}
