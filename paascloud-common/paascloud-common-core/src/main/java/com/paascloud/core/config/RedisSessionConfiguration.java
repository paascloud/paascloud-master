package com.paascloud.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * The class Redis session configuration.
 *
 * @author paascloud.net @gmail.com
 */
@EnableRedisHttpSession
public class RedisSessionConfiguration {
	/**
	 * Configure redis action configure redis action.
	 *
	 * @return the configure redis action
	 */
	@Bean
	public static ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}
}
