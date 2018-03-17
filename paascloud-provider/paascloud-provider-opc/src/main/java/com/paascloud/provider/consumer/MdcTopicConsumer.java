package com.paascloud.provider.consumer;

import com.paascloud.JacksonUtil;
import com.paascloud.base.constant.AliyunMqTopicConstants;
import com.paascloud.core.mq.MqMessage;
import com.paascloud.provider.model.dto.UpdateAttachmentDto;
import com.paascloud.provider.service.OpcAttachmentService;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Opt send sms topic consumer.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Service
public class MdcTopicConsumer {

	@Resource
	private OpcAttachmentService opcAttachmentService;

	/**
	 * Handler send sms topic.
	 *
	 * @param body      the body
	 * @param topicName the topic name
	 * @param tags      the tags
	 * @param keys      the keys
	 */
	@Transactional(rollbackFor = Exception.class)
	public void handlerSendSmsTopic(String body, String topicName, String tags, String keys) throws QiniuException {
		MqMessage.checkMessage(body, keys, topicName);

		if (StringUtils.equals(tags, AliyunMqTopicConstants.MqTagEnum.DELETE_ATTACHMENT.getTag())) {
			List<Long> idList = opcAttachmentService.queryAttachmentByRefNo(body);
			for (final Long id : idList) {
				opcAttachmentService.deleteFile(id);
			}
		} else {
			UpdateAttachmentDto attachmentDto;
			try {
				attachmentDto = JacksonUtil.parseJson(body, UpdateAttachmentDto.class);
			} catch (Exception e) {
				log.error("发送短信MQ出现异常={}", e.getMessage(), e);
				throw new IllegalArgumentException("JSON转换异常", e);
			}
			opcAttachmentService.updateAttachment(attachmentDto);
		}
	}
}
