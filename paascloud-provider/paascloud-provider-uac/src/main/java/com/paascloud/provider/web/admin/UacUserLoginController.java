package com.paascloud.provider.web.admin;

import com.google.common.base.Preconditions;
import com.paascloud.base.dto.UserTokenDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.model.dto.user.LoginRespDto;
import com.paascloud.provider.model.enums.UacUserTokenStatusEnum;
import com.paascloud.provider.service.UacLoginService;
import com.paascloud.provider.service.UacUserTokenService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 登录相关.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacUserLoginController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserLoginController extends BaseController {

	@Resource
	private UacLoginService uacLoginService;
	@Resource
	private UacUserTokenService uacUserTokenService;
	@Resource
	private ClientDetailsService clientDetailsService;
	private static final String BEARER_TOKEN_TYPE = "Basic ";


	/**
	 * 登录成功获取菜单信息和用户信息.
	 *
	 * @param applicationId the application id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/user/loginAfter/{applicationId}")
	@ApiOperation(httpMethod = "POST", value = "登录成功获取用户菜单")
	public Wrapper<LoginRespDto> loginAfter(@PathVariable Long applicationId) {
		logger.info("登录成功获取用户菜单. applicationId={}", applicationId);
		LoginRespDto result = uacLoginService.loginAfter(applicationId);
		return WrapMapper.ok(result);
	}

	/**
	 * 登出.
	 *
	 * @param accessToken the access token
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/user/logout")
	@ApiOperation(httpMethod = "POST", value = "登出")
	public Wrapper loginAfter(String accessToken) {
		if (!StringUtils.isEmpty(accessToken)) {
			// 修改用户在线状态
			UserTokenDto userTokenDto = uacUserTokenService.getByAccessToken(accessToken);
			userTokenDto.setStatus(UacUserTokenStatusEnum.OFF_LINE.getStatus());
			uacUserTokenService.updateUacUserToken(userTokenDto, getLoginAuthDto());
		}
		return WrapMapper.ok();
	}

	/**
	 * 刷新token.
	 *
	 * @param request      the request
	 * @param refreshToken the refresh token
	 * @param accessToken  the access token
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/auth/user/refreshToken")
	@ApiOperation(httpMethod = "POST", value = "刷新token")
	public Wrapper<String> refreshToken(HttpServletRequest request, @RequestParam(value = "refreshToken") String refreshToken, @RequestParam(value = "accessToken") String accessToken) {
		String token;
		try {
			Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotEmpty(accessToken), "accessToken is null");
			Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotEmpty(refreshToken), "refreshToken is null");
			String header = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (header == null || !header.startsWith(BEARER_TOKEN_TYPE)) {
				throw new UnapprovedClientAuthenticationException("请求头中无client信息");
			}
			String[] tokens = RequestUtil.extractAndDecodeHeader(header);
			assert tokens.length == 2;

			String clientId = tokens[0];
			String clientSecret = tokens[1];

			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

			if (clientDetails == null) {
				throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
			} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
				throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
			}

			token = uacUserTokenService.refreshToken(accessToken, refreshToken, request);
		} catch (Exception e) {
			logger.error("refreshToken={}", e.getMessage(), e);
			return WrapMapper.error();
		}
		return WrapMapper.ok(token);
	}

}