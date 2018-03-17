package com.paascloud.security.core.social.weixin.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.paascloud.security.core.social.weixin.api.Weixin;

/**
 * 微信连接工厂
 *
 * @author paascloud.net @gmail.com
 */
public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {

	/**
	 * Instantiates a new Weixin connection factory.
	 *
	 * @param providerId the provider id
	 * @param appId      the app id
	 * @param appSecret  the app secret
	 */
	public WeixinConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new WeixinServiceProvider(appId, appSecret), new WeixinAdapter());
	}

	/**
	 * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
	 *
	 * @param accessGrant the access grant
	 *
	 * @return the string
	 */
	@Override
	protected String extractProviderUserId(AccessGrant accessGrant) {
		if (accessGrant instanceof WeixinAccessGrant) {
			return ((WeixinAccessGrant) accessGrant).getOpenId();
		}
		return null;
	}

	/**
	 * Create connection connection.
	 *
	 * @param accessGrant the access grant
	 *
	 * @return the connection
	 */
	@Override
	public Connection<Weixin> createConnection(AccessGrant accessGrant) {
		return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
				accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
	}

	/**
	 * Create connection connection.
	 *
	 * @param data the data
	 *
	 * @return the connection
	 */
	@Override
	public Connection<Weixin> createConnection(ConnectionData data) {
		return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
	}

	private ApiAdapter<Weixin> getApiAdapter(String providerUserId) {
		return new WeixinAdapter(providerUserId);
	}

	private OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<Weixin>) getServiceProvider();
	}


}
