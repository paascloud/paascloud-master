package com.paascloud.provider.model.dto.sms;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The class Sms message.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@NoArgsConstructor
public class SmsMessage implements Serializable {
	private static final long serialVersionUID = -8708881656765856624L;
	/**
	 * 手机号码
	 */
	private String mobileNo;

	/**
	 * 加密token
	 */
	private String smsToken;

	/**
	 * 验证码
	 */
	private String smsCode;

	/**
	 * 短信模板Code
	 */
	private String smsTemplateCode;

	/**
	 * 业务单号(这里使用ip地址)
	 */
	private String outId;
}
