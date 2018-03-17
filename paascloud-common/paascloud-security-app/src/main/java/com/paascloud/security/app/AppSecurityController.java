
package com.paascloud.security.app;

import com.paascloud.security.app.social.AppSingUpUtils;
import com.paascloud.security.core.properties.SecurityConstants;
import com.paascloud.security.core.social.BaseSocialController;
import com.paascloud.security.core.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * The class App security controller.
 *
 * @author paascloud.net@gmail.comg
 */
@RestController
public class AppSecurityController extends BaseSocialController {

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	@Autowired
	private AppSingUpUtils appSingUpUtils;

	/**
	 * 需要注册时跳到这里，返回401和用户信息给前端
	 *
	 * @param request the request
	 *
	 * @return social user info
	 */
	@GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		appSingUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
		return buildSocialUserInfo(connection);
	}

}
