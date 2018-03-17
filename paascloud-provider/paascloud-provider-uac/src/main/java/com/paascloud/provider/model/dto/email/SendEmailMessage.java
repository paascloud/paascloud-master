package com.paascloud.provider.model.dto.email;

import lombok.Data;

import java.io.Serializable;

/**
 * The class Send email message.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class SendEmailMessage implements Serializable {
	private static final long serialVersionUID = -8708881656765856624L;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 手机号码
	 */
	private String email;

	/**
	 * 验证码
	 */
	private String emailCode;

	/**
	 * 邮件模板Code
	 */
	private String emailTemplateCode;

	/**
	 * Instantiates a new Send email message.
	 */
	public SendEmailMessage() {
	}

	/**
	 * Instantiates a new Send email message.
	 *
	 * @param email the email
	 */
	public SendEmailMessage(String email) {
		this.email = email;
	}
}
