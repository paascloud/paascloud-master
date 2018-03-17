package com.paascloud.provider.service;

import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.TpcMqConsumer;
import com.paascloud.provider.model.vo.TpcMqConsumerVo;
import com.paascloud.provider.model.vo.TpcMqSubscribeVo;

import java.util.List;

/**
 * The interface Tpc mq consumer service.
 *
 * @author paascloud.net @gmail.com
 */
public interface TpcMqConsumerService extends IService<TpcMqConsumer> {
	/**
	 * 查询Mq消费者列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the list
	 */
	List<TpcMqConsumerVo> listConsumerVoWithPage(TpcMqConsumer tpcMqConsumer);

	/**
	 * 查询订阅者列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the list
	 */
	List<TpcMqSubscribeVo> listSubscribeVoWithPage(TpcMqConsumer tpcMqConsumer);

	/**
	 * Delete by tag id.
	 *
	 * @param tagId the tag id
	 *
	 * @return the int
	 */
	int deleteSubscribeTagByTagId(Long tagId);

	/**
	 * 根据消费者ID删除消费者.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteConsumerById(Long id);

	/**
	 * List subscribe vo list.
	 *
	 * @param subscribeIdList the subscribe id list
	 *
	 * @return the list
	 */
	List<TpcMqSubscribeVo> listSubscribeVo(List<Long> subscribeIdList);

	/**
	 * List consumer group by topic list.
	 *
	 * @param topic the topic
	 *
	 * @return the list
	 */
	List<String> listConsumerGroupByTopic(String topic);

	/**
	 * 根据cid更新生产者状态为在线.
	 *
	 * @param consumerGroup the consumer group
	 */
	void updateOnLineStatusByCid(String consumerGroup);

	/**
	 * 根据cid更新生产者状态为离线.
	 *
	 * @param consumerGroup the consumer group
	 */
	void updateOffLineStatusByCid(String consumerGroup);
}
