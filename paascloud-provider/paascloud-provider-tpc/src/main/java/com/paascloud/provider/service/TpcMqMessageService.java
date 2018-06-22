/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqMessageService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.provider.model.domain.TpcMqMessage;
import com.paascloud.provider.model.dto.MessageTaskQueryDto;
import com.paascloud.provider.model.dto.TpcMqMessageDto;
import com.paascloud.provider.model.vo.TpcMessageVo;
import com.paascloud.wrapper.Wrapper;

import java.util.List;

/**
 * The interface Tpc mq message service.
 *
 * @author paascloud.net @gmail.com
 */
public interface TpcMqMessageService {
	/**
	 * 预存储消息.
	 *
	 * @param mqMessageDto the mq message dto
	 */
	void saveMessageWaitingConfirm(TpcMqMessageDto mqMessageDto);


	/**
	 * 确认并发送消息.
	 *
	 * @param messageKey the message key
	 */
	void confirmAndSendMessage(String messageKey);


	/**
	 * 存储并发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 */
	void saveAndSendMessage(TpcMqMessageDto mqMessageDto);


	/**
	 * 直接发送消息.
	 *
	 * @param body       the body
	 * @param topic      the topic
	 * @param tag        the tag
	 * @param key        the key
	 * @param pid        the pid
	 * @param delayLevel the delay level
	 */
	void directSendMessage(String body, String topic, String tag, String key, String pid, Integer delayLevel);


	/**
	 * 重发消息.
	 *
	 * @param messageId the message id
	 */
	void resendMessageByMessageId(Long messageId);


	/**
	 * 根据messageId重发某条消息.
	 *
	 * @param messageKey the message key
	 */
	void resendMessageByMessageKey(String messageKey);


	/**
	 * 将消息标记为死亡消息.
	 *
	 * @param messageId the message id
	 */
	void setMessageToAlreadyDead(Long messageId);

	/**
	 * 根据消息ID删除消息
	 *
	 * @param messageKey the message key
	 */
	void deleteMessageByMessageKey(String messageKey);


	/**
	 * 重发某个消息队列中的全部已死亡的消息.
	 *
	 * @param topicName the topic name
	 * @param batchSize the batch size
	 */
	void resendAllDeadMessageByTopicName(String topicName, int batchSize);

	/**
	 * List message for waiting process list.
	 *
	 * @param query the query
	 *
	 * @return the list
	 */
	List<TpcMqMessage> listMessageForWaitingProcess(MessageTaskQueryDto query);

	/**
	 * 确认收到消息.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 */
	void confirmReceiveMessage(String cid, String messageKey);

	/**
	 * 确认消费消息.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 */
	void confirmConsumedMessage(String cid, String messageKey);

	/**
	 * 更新消息确认状态.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the int
	 */
	int updateMqConfirmStatus(String cid, String messageKey);

	/**
	 * 根据topic创建待确认的消息确认列表.
	 *
	 * @param topic      the topic
	 * @param messageId  the message id
	 * @param messageKey the message key
	 */
	void createMqConfirmListByTopic(String topic, Long messageId, String messageKey);

	/**
	 * 查询发送中且超时的消息.
	 *
	 * @param query the query
	 *
	 * @return the list
	 */
	List<String> queryWaitingConfirmMessageKeyList(MessageTaskQueryDto query);

	/**
	 * Handle waiting confirm message.
	 *
	 * @param deleteKeyList the delete key list
	 * @param resendKeyList the resend key list
	 */
	void handleWaitingConfirmMessage(List<String> deleteKeyList, List<String> resendKeyList);

	/**
	 * 更新任务状态.
	 *
	 * @param message the message
	 *
	 * @return the int
	 */
	int updateMqMessageTaskStatus(TpcMqMessage message);

	/**
	 * Update mq message status.
	 *
	 * @param update the update
	 *
	 * @return the int
	 */
	int updateMqMessageStatus(TpcMqMessage update);

	/**
	 * Query record list with page page info.
	 *
	 * @param tpcMessageQueryDto the tpc message query dto
	 *
	 * @return the page info
	 */
	Wrapper queryRecordListWithPage(MessageQueryDto tpcMessageQueryDto);

	/**
	 * List reliable message vo list.
	 *
	 * @param tpcMessageQueryDto the tpc message query dto
	 *
	 * @return the list
	 */
	List<TpcMessageVo> listReliableMessageVo(MessageQueryDto tpcMessageQueryDto);

	/**
	 * List reliable message vo list.
	 *
	 * @param messageIdList the message id list
	 *
	 * @return the list
	 */
	List<TpcMessageVo> listReliableMessageVo(List<Long> messageIdList);
}
