package com.paascloud.security.core.social.qq.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The class Qq.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

	private String appId;

	private String openId;

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Instantiates a new Qq.
	 *
	 * @param accessToken the access token
	 * @param appId       the app id
	 */
	public QQImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

		this.appId = appId;

		String url = String.format(URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);

		log.info("result={}", result);

		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}

	/**
	 * Gets user info.
	 *
	 * @return the user info
	 */
	@Override
	public QQUserInfo getUserInfo() {

		String url = String.format(URL_GET_USERINFO, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);

		log.info("result={}", result);

		QQUserInfo userInfo;
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败", e);
		}
	}

}
