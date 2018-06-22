/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：RedisKeyUtil.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * The class Redis key util.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisKeyUtil {

	/**
	 * The constant RESET_PWD_TOKEN_KEY.
	 */
	private static final String RESET_PWD_TOKEN_KEY = "paascloud:restPwd";
	private static final String ACTIVE_USER = "paascloud:activeUser";
	private static final String SEND_SMS_COUNT = "paascloud:sms:count";
	private static final String SEND_EMAIL_CODE = "paascloud:email:code";
	private static final String ACCESS_TOKEN = "paascloud:token:accessToken";
	private static final String UPLOAD_FILE_SIZE = "paascloud:file:upload_file_size";
	private static final int REF_NO_MAX_LENGTH = 100;

	/**
	 * Gets reset pwd token key.
	 *
	 * @param resetPwdKey the rest pwd key
	 *
	 * @return the reset pwd token key
	 */
	public static String getResetPwdTokenKey(String resetPwdKey) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(resetPwdKey), "参数不能为空");
		return RESET_PWD_TOKEN_KEY + ":" + resetPwdKey;

	}

	public static String getSendEmailCodeKey(String loginName, String email) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(loginName), "Email不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(email), "用户名不能为空");
		return SEND_EMAIL_CODE + ":" + loginName + email;

	}

	/**
	 * Gets active user key.
	 *
	 * @param activeToken the active token
	 *
	 * @return the active user key
	 */
	public static String getActiveUserKey(String activeToken) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(activeToken), "参数不能为空");
		return ACTIVE_USER + ":" + activeToken;

	}

	/**
	 * Gets send sms count key.
	 *
	 * @param ipAddr the ip addr
	 * @param type   mobile;ip;total
	 *
	 * @return the send sms count key
	 */
	public static String getSendSmsCountKey(String ipAddr, String type) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(ipAddr), "请不要篡改IP地址");
		return SEND_SMS_COUNT + ":" + type + ":" + ipAddr;

	}


	/**
	 * Gets send sms rate key.
	 *
	 * @param ipAddr the ip addr
	 *
	 * @return the send sms rate key
	 */
	public static String getSendSmsRateKey(String ipAddr) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(ipAddr), "请不要篡改IP地址");
		return SEND_SMS_COUNT + ":" + ipAddr;

	}

	public static String getAccessTokenKey(String token) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(token), "非法请求token参数不存在");
		return ACCESS_TOKEN + ":" + token;
	}

	public static String createMqKey(String topic, String tag, String refNo, String body) {
		String temp = refNo;
		Preconditions.checkArgument(StringUtils.isNotEmpty(topic), "topic is null");
		Preconditions.checkArgument(StringUtils.isNotEmpty(tag), "tag is null");
		Preconditions.checkArgument(StringUtils.isNotEmpty(refNo), "refNo is null");
		Preconditions.checkArgument(StringUtils.isNotEmpty(body), "body is null");

		if (refNo.length() > REF_NO_MAX_LENGTH) {
			temp = StringUtils.substring(refNo, 0, REF_NO_MAX_LENGTH) + "...";
		}
		return topic + "_" + tag + "_" + temp + "-" + body.hashCode();
	}

	public static String getFileSizeKey() {
		return UPLOAD_FILE_SIZE;
	}
}
