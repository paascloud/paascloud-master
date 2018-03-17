
package com.paascloud.provider.service.impl;

import com.paascloud.base.constant.AliyunSmsConstants;
import com.paascloud.provider.model.dto.sms.SmsMessage;
import com.paascloud.provider.service.SmsService;
import com.paascloud.security.core.validate.code.sms.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 默认的短信验证码发送器
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class PcSmsCodeSender implements SmsCodeSender {
	@Resource
	private SmsService smsService;

	@Override
	public void send(String mobile, String code, String ip) {
		log.info("ip地址:{}向手机: {}发送短信验证码:{}", ip, mobile, code);
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setMobileNo(mobile);
		smsMessage.setSmsCode(code);
		smsMessage.setSmsTemplateCode(AliyunSmsConstants.SmsTempletEnum.UAC_PC_GLOBAL_TEMPLATE.getTempletCode());
		smsService.sendSmsCode(smsMessage, ip);
	}

}
