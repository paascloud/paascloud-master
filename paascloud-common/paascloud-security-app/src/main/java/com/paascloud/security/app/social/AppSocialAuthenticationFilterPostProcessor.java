package com.paascloud.security.app.social;


import com.paascloud.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor{

	@Autowired
	private AuthenticationSuccessHandler pcAuthenticationSuccessHandler;

	@Override
	public void process(final SocialAuthenticationFilter socialAuthenticationFilter) {
		socialAuthenticationFilter.setAuthenticationSuccessHandler(pcAuthenticationSuccessHandler);
	}
}
