package com.paascloud.discovery;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The class Security config.
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * Configure.
	 *
	 * @param http the http
	 *
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable()
				.and()
				.formLogin()
				.loginPage("/login.html")
				.loginProcessingUrl("/login")
				.and()
				.logout().logoutUrl("/logout")
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/**", "/applications/**", "/api/applications/**", "/login.html", "/**/*.css", "/img/**", "/third-party/**")
				.permitAll()
				.anyRequest().authenticated();
	}
}