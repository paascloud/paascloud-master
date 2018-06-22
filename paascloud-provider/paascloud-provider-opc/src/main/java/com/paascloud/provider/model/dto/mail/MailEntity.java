/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MailEntity.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.mail;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.paascloud.PubUtils;
import com.paascloud.PublicUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;
import java.util.Set;

/**
 * The class Mail entity.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Data
public class MailEntity {
	/**
	 * 获取或设置电子邮件的回复地址。
	 */
	private String replyTo;
	/**
	 * 获取包含此电子邮件的收件人的地址集合。
	 */
	private Set<String> to;
	/**
	 * 获取包含此电子邮件的抄送 (CC) 收件人的地址集合。
	 */
	private Set<String> cc;
	/**
	 * 获取包含此电子邮件的密件抄送 (BCC) 收件人的地址集合。
	 */
	private Set<String> bcc;
	/**
	 * 发送时间
	 */
	private Date sentDate;
	/**
	 * 获取或设置此电子邮件的主题行。
	 */
	private String subject;
	/**
	 * 内容
	 */
	private String text;

	/**
	 * Instantiates a new Mail entity.
	 *
	 * @param subject the subject
	 * @param text    the text
	 * @param to      the to
	 */
	public MailEntity(String subject, String text, Set<String> to) {
		this.subject = subject;
		this.text = text;
		this.to = to;
		this.sentDate = new Date();
	}

	/**
	 * Instantiates a new Mail entity.
	 *
	 * @param subject the subject
	 * @param text    the text
	 * @param to      the to
	 * @param cc      the cc
	 */
	public MailEntity(String subject, String text, Set<String> to, Set<String> cc) {
		this.subject = subject;
		this.text = text;
		this.to = to;
		this.cc = cc;
		this.sentDate = new Date();
	}

	/**
	 * Create simple mail message simple mail message.
	 *
	 * @param subject the subject
	 * @param text    the text
	 * @param to      the to
	 *
	 * @return the simple mail message
	 */
	public static SimpleMailMessage createSimpleMailMessage(String subject, String text, Set<String> to) {
		log.info("参数异常, 邮件信息不完整 subject={}, text={}, to={}", subject, text, to);
		Preconditions.checkArgument(!PubUtils.isNull(subject, text, to), "参数异常, 邮件信息不完整");

		String[] toArray = setToArray(to);
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		simpleMailMessage.setTo(toArray);
		return simpleMailMessage;
	}

	private static String[] setToArray(Set<String> to) {
		Set<String> toSet = Sets.newHashSet();
		for (String toStr : to) {
			toStr = toStr.trim();
			if (PubUtils.isEmail(toStr)) {
				toSet.add(toStr);
			}
		}
		if (PublicUtil.isEmpty(toSet)) {
			return null;
		}
		return toSet.toArray(new String[toSet.size()]);
	}


}
