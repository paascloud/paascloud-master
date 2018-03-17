package com.paascloud.provider.mq.consumer.listener;

import com.paascloud.PublicUtil;
import com.paascloud.base.constant.AliyunMqTopicConstants;
import com.paascloud.core.mq.MqMessage;
import com.paascloud.provider.annotation.MqConsumerStore;
import com.paascloud.provider.service.MqMessageService;
import com.paascloud.provider.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * The class Uac push message listener.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
public class UacPushMessageListener implements MessageListenerConcurrently {
	@Resource
	private MqMessageService mqMessageService;
	@Resource
	private RedisService redisService;

	/**
	 * Consume message consume concurrently status.
	 *
	 * @param messageExtList             the message ext list
	 * @param consumeConcurrentlyContext the consume concurrently context
	 *
	 * @return the consume concurrently status
	 */
	@Override
	@MqConsumerStore
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
		MessageExt msg = messageExtList.get(0);
		String body = new String(msg.getBody());
		String topicName = msg.getTopic();
		String tags = msg.getTags();
		String keys = msg.getKeys();

		try {
			MqMessage.checkMessage(body, topicName, tags, keys);
			String mqKV = redisService.getKey(keys);
			if (PublicUtil.isNotEmpty(mqKV)) {
				log.error("MQ消费Topic={},tag={},key={}, 重复消费", topicName, tags, keys);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
			if (AliyunMqTopicConstants.MqTopicEnum.TPC_TOPIC.getTopic().equals(topicName)) {
				mqMessageService.deleteMessageTopic(body, tags);
			} else {
				log.info("OPC订单信息消 topicName={} 不存在", topicName);
			}
		} catch (IllegalArgumentException ex) {
			log.error("校验MQ message 失败 ex={}", ex.getMessage(), ex);
		} catch (Exception e) {
			log.error("处理MQ message 失败 topicName={}, keys={}, ex={}", topicName, keys, e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}

		redisService.setKey(keys, keys, 10, TimeUnit.DAYS);
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
