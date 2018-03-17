package com.paascloud.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置提供器，各个模块和业务系统可以通过实现此接口向系统添加授权配置。
 *
 * @author paascloud.net @gmail.com
 */
public interface AuthorizeConfigProvider {

	/**
	 * Config boolean.
	 *
	 * @param config the config
	 *
	 * @return 返回的boolean表示配置中是否有针对anyRequest的配置 。在整个授权配置中， 应该有且仅有一个针对anyRequest的配置，如果所有的实现都没有针对anyRequest的配置， 系统会自动增加一个anyRequest().authenticated()的配置。如果有多个针对anyRequest 的配置，则会抛出异常。
	 */
	boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
