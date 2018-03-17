package com.paascloud.security.app.social;

import com.paascloud.security.app.AppSecretException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * app环境下替换providerSignInUtils，避免由于没有session导致读不到社交用户信息的问题
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class AppSingUpUtils {

	private final RedisTemplate<String, Object> redisTemplate;

	private final UsersConnectionRepository usersConnectionRepository;

	private final ConnectionFactoryLocator connectionFactoryLocator;

	/**
	 * Instantiates a new App sing up utils.
	 *
	 * @param redisTemplate             the redis template
	 * @param usersConnectionRepository the users connection repository
	 * @param connectionFactoryLocator  the connection factory locator
	 */
	@Autowired
	public AppSingUpUtils(RedisTemplate<String, Object> redisTemplate, UsersConnectionRepository usersConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator) {
		this.redisTemplate = redisTemplate;
		this.usersConnectionRepository = usersConnectionRepository;
		this.connectionFactoryLocator = connectionFactoryLocator;
	}

	/**
	 * 缓存社交网站用户信息到redis
	 *
	 * @param request        the request
	 * @param connectionData the connection data
	 */
	public void saveConnectionData(WebRequest request, ConnectionData connectionData) {
		redisTemplate.opsForValue().set(getKey(request), connectionData, 10, TimeUnit.MINUTES);
	}

	/**
	 * 将缓存的社交网站用户信息与系统注册用户信息绑定
	 *
	 * @param request the request
	 * @param userId  the user id
	 */
	public void doPostSignUp(WebRequest request, String userId) {
		String key = getKey(request);
		if (!redisTemplate.hasKey(key)) {
			throw new AppSecretException("无法找到缓存的用户社交账号信息");
		}
		ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
		Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
				.createConnection(connectionData);
		usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);

		redisTemplate.delete(key);
	}

	/**
	 * 获取redis key
	 */
	private String getKey(WebRequest request) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			throw new AppSecretException("设备id参数不能为空");
		}
		return "pc:security:social.connect." + deviceId;
	}

}
