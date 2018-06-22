/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConsumerMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.TpcMqConsumer;
import com.paascloud.provider.model.vo.TpcMqConsumerVo;
import com.paascloud.provider.model.vo.TpcMqSubscribeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Tpc mq consumer mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface TpcMqConsumerMapper extends MyMapper<TpcMqConsumer> {
	/**
	 * 查询消费者列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the list
	 */
	List<TpcMqConsumerVo> listTpcMqConsumerVoWithPage(TpcMqConsumer tpcMqConsumer);

	/**
	 * 分页查询MQ订阅列表.
	 *
	 * @param tpcMqConsumer the tpc mq consumer
	 *
	 * @return the list
	 */
	List<TpcMqSubscribeVo> listTpcMqSubscribeVoWithPage(TpcMqConsumer tpcMqConsumer);

	/**
	 * Delete subscribe tag by tag id int.
	 *
	 * @param tagId the tag id
	 *
	 * @return the int
	 */
	int deleteSubscribeTagByTagId(@Param("tagId") Long tagId);

	/**
	 * List subscribe id by consumer id list.
	 *
	 * @param consumerId the consumer id
	 *
	 * @return the list
	 */
	List<Long> listSubscribeIdByConsumerId(@Param("consumerId") Long consumerId);

	/**
	 * Delete subscribe by consumer id.
	 *
	 * @param consumerId the consumer id
	 *
	 * @return the int
	 */
	int deleteSubscribeByConsumerId(@Param("consumerId") Long consumerId);

	/**
	 * Delete subscribe tag by subscribe id list.
	 *
	 * @param subscribeIdList the subscribe id list
	 *
	 * @return the int
	 */
	int deleteSubscribeTagBySubscribeIdList(@Param("subscribeIdList") List<Long> subscribeIdList);

	/**
	 * List subscribe vo list.
	 *
	 * @param subscribeIdList the subscribe id list
	 *
	 * @return the list
	 */
	List<TpcMqSubscribeVo> listSubscribeVo(@Param("subscribeIdList") List<Long> subscribeIdList);

	/**
	 * List consumer group by topic list.
	 *
	 * @param topic the topic
	 *
	 * @return the list
	 */
	List<String> listConsumerGroupByTopic(@Param("topic") String topic);

	/**
	 * Gets by cid.
	 *
	 * @param consumerGroup the consumer group
	 *
	 * @return the by cid
	 */
	TpcMqConsumer getByCid(String consumerGroup);
}