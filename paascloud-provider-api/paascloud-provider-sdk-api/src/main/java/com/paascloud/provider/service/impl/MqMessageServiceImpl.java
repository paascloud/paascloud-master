package com.paascloud.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.AliyunMqTopicConstants;
import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.base.dto.MqMessageVo;
import com.paascloud.base.dto.ShardingContextDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.generator.UniqueIdGenerator;
import com.paascloud.provider.exceptions.TpcBizException;
import com.paascloud.provider.mapper.MqMessageDataMapper;
import com.paascloud.provider.model.domain.MqMessageData;
import com.paascloud.provider.model.dto.TpcMqMessageDto;
import com.paascloud.provider.model.enums.MqMessageTypeEnum;
import com.paascloud.provider.model.enums.MqSendTypeEnum;
import com.paascloud.provider.service.MqMessageService;
import com.paascloud.provider.service.TpcMqMessageFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mq message service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Service
public class MqMessageServiceImpl implements MqMessageService {
	@Resource
	private MqMessageDataMapper mqMessageDataMapper;
	@Resource
	private TpcMqMessageFeignApi tpcMqMessageFeignApi;
	@Resource
	private TaskExecutor taskExecutor;

	@Value("${spring.profiles.active}")
	String profile;
	@Value("${spring.application.name}")
	String applicationName;

	@Override
	public void saveMqProducerMessage(MqMessageData mqMessageData) {
		// 校验消息数据
		this.checkMessage(mqMessageData);
		// 先保存消息
		mqMessageData.setMessageType(MqMessageTypeEnum.PRODUCER_MESSAGE.messageType());
		mqMessageData.setId(UniqueIdGenerator.generateId());
		mqMessageDataMapper.insertSelective(mqMessageData);
	}

	private void checkMessage(MqMessageData mqMessageData) {
		if (null == mqMessageData) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050007);
		}
		String messageTopic = mqMessageData.getMessageTopic();
		String messageBody = mqMessageData.getMessageBody();
		String messageKey = mqMessageData.getMessageKey();
		String producerGroup = mqMessageData.getProducerGroup();
		if (StringUtils.isEmpty(messageKey)) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050009);
		}
		if (StringUtils.isEmpty(messageTopic)) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050001);
		}
		if (StringUtils.isEmpty(messageBody)) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050008, mqMessageData.getMessageKey());
		}

		if (StringUtils.isEmpty(producerGroup)) {
			throw new TpcBizException(ErrorCodeEnum.TPC100500015, mqMessageData.getMessageKey());
		}
	}

	@Override
	public void confirmAndSendMessage(String messageKey) {
		// 发送确认消息给消息中心
		try {
			Wrapper wrapper = tpcMqMessageFeignApi.confirmAndSendMessage(messageKey);
			if (wrapper == null) {
				throw new TpcBizException(ErrorCodeEnum.GL99990002);
			}
			if (wrapper.error()) {
				throw new TpcBizException(ErrorCodeEnum.TPC10050004, wrapper.getMessage(), messageKey);
			}
			log.info("<== saveMqProducerMessage - 存储并发送消息给消息中心成功. messageKey={}", messageKey);
		} catch (Exception e) {
			log.error("<== saveMqProducerMessage - 存储并发送消息给消息中心失败. messageKey={}", messageKey, e);
		}

	}

	@Override
	public void saveMqConsumerMessage(MqMessageData mqMessageData) {

	}

	@Override
	public void deleteMessageByMessageKey(String messageKey, MqSendTypeEnum type) {
		log.info("删除预发送消息. messageKey={}, type={}", messageKey, type);
		if (type == MqSendTypeEnum.WAIT_CONFIRM) {
			taskExecutor.execute(() -> tpcMqMessageFeignApi.deleteMessageByMessageKey(messageKey));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void confirmReceiveMessage(String cid, MqMessageData messageData) {
		final String messageKey = messageData.getMessageKey();
		log.info("confirmReceiveMessage - 消费者={}, 确认收到key={}的消息", cid, messageKey);
		// 先保存消息
		messageData.setMessageType(MqMessageTypeEnum.CONSUMER_MESSAGE.messageType());
		messageData.setId(UniqueIdGenerator.generateId());
		mqMessageDataMapper.insertSelective(messageData);

		Wrapper wrapper = tpcMqMessageFeignApi.confirmReceiveMessage(cid, messageKey);
		log.info("tpcMqMessageFeignApi.confirmReceiveMessage result={}", wrapper);
		if (wrapper == null) {
			throw new TpcBizException(ErrorCodeEnum.GL99990002);
		}
		if (wrapper.error()) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050004, wrapper.getMessage(), messageKey);
		}
	}

	@Override
	public void saveAndConfirmFinishMessage(String cid, String messageKey) {
		Wrapper wrapper = tpcMqMessageFeignApi.confirmConsumedMessage(cid, messageKey);
		log.info("tpcMqMessageFeignApi.confirmReceiveMessage result={}", wrapper);
		if (wrapper == null) {
			throw new TpcBizException(ErrorCodeEnum.GL99990002);
		}
		if (wrapper.error()) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050004, wrapper.getMessage(), messageKey);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteMqMessage(final int shardingTotalCount, final int shardingItem, final String tags) {
		// 分页参数每页5000条
		int pageSize = 1000;
		int messageType;
		if (AliyunMqTopicConstants.MqTagEnum.DELETE_PRODUCER_MESSAGE.getTag().equals(tags)) {
			messageType = MqMessageTypeEnum.PRODUCER_MESSAGE.messageType();
		} else {
			messageType = MqMessageTypeEnum.CONSUMER_MESSAGE.messageType();
		}

		int totalCount = mqMessageDataMapper.getBefore7DayTotalCount(shardingTotalCount, shardingItem, messageType);
		if (totalCount == 0) {
			return;
		}
		// 分页参数, 总页数
		int pageNum = (totalCount - 1) / pageSize + 1;

		for (int currentPage = 1; currentPage < pageNum; currentPage++) {
			List<Long> idList = mqMessageDataMapper.getIdListBefore7Day(shardingTotalCount, shardingItem, messageType, currentPage, pageSize);
			mqMessageDataMapper.batchDeleteByIdList(idList);
		}
	}

	@Override
	public void deleteMessageTopic(final String body, final String tags) {
		ShardingContextDto shardingContextDto = JSON.parseObject(body, ShardingContextDto.class);
		int shardingTotalCount = shardingContextDto.getShardingTotalCount();
		int shardingItem = shardingContextDto.getShardingItem();

		this.deleteMqMessage(shardingTotalCount, shardingItem, tags);
	}

	@Override
	public List<String> queryMessageKeyList(final List<String> messageKeyList) {
		return mqMessageDataMapper.queryMessageKeyList(messageKeyList);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveWaitConfirmMessage(final MqMessageData mqMessageData) {
		this.saveMqProducerMessage(mqMessageData);
		// 发送预发送状态的消息给消息中心
		TpcMqMessageDto tpcMqMessageDto = mqMessageData.getTpcMqMessageDto();
		tpcMqMessageFeignApi.saveMessageWaitingConfirm(tpcMqMessageDto);
		log.info("<== saveWaitConfirmMessage - 存储预发送消息成功. messageKey={}", mqMessageData.getMessageKey());
	}

	@Override
	public void saveAndSendMessage(final MqMessageData mqMessageData) {
		this.saveMqProducerMessage(mqMessageData);
		// 发送预发送状态的消息给消息中心
		TpcMqMessageDto tpcMqMessageDto = mqMessageData.getTpcMqMessageDto();
		tpcMqMessageFeignApi.saveAndSendMessage(tpcMqMessageDto);
		log.info("<== saveAndSendMessage - 保存并送消息成功. messageKey={}", mqMessageData.getMessageKey());
	}

	@Override
	public void directSendMessage(final MqMessageData mqMessageData) {
		// 发送预发送状态的消息给消息中心
		TpcMqMessageDto tpcMqMessageDto = mqMessageData.getTpcMqMessageDto();
		tpcMqMessageFeignApi.directSendMessage(tpcMqMessageDto);
		log.info("<== directSendMessage - 直接送消息成功. messageKey={}", mqMessageData.getMessageKey());
	}

	@Override
	public Wrapper<PageInfo<MqMessageVo>> queryMessageListWithPage(final MessageQueryDto messageQueryDto) {
		PageHelper.startPage(messageQueryDto.getPageNum(), messageQueryDto.getPageSize());
		List<MqMessageVo> list = mqMessageDataMapper.queryMessageListWithPage(messageQueryDto);

		if (PublicUtil.isEmpty(list)) {
			list = Lists.newArrayList();
		}
		return WrapMapper.ok(new PageInfo<>(list));
	}
}
