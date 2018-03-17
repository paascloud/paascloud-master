package com.paascloud.core.interceptor;

import com.paascloud.RedisKeyUtil;
import com.paascloud.ThreadLocalMap;
import com.paascloud.annotation.NoNeedAccessAuthentication;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UserTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * The class Token interceptor.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

	@Value("${paascloud.oauth2.jwtSigningKey}")
	private String jwtSigningKey;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	private static final String OPTIONS = "OPTIONS";
	private static final String AUTH_PATH1 = "/auth";
	private static final String AUTH_PATH2 = "/oauth";
	private static final String AUTH_PATH3 = "/error";
	private static final String AUTH_PATH4 = "/api";

	/**
	 * After completion.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param arg2     the arg 2
	 * @param ex       the ex
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) throws Exception {
		if (ex != null) {
			log.error("<== afterCompletion - 解析token失败. ex={}", ex.getMessage(), ex);
			this.handleException(response);
		}
	}

	/**
	 * Post handle.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param arg2     the arg 2
	 * @param mv       the mv
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv) {
	}

	/**
	 * Pre handle boolean.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 *
	 * @return the boolean
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String uri = request.getRequestURI();
		log.info("<== preHandle - 权限拦截器.  url={}", uri);
		if (uri.contains(AUTH_PATH1) || uri.contains(AUTH_PATH2) || uri.contains(AUTH_PATH3) || uri.contains(AUTH_PATH4)) {
			log.info("<== preHandle - 配置URL不走认证.  url={}", uri);
			return true;
		}
		log.info("<== preHandle - 调试模式不走认证.  OPTIONS={}", request.getMethod().toUpperCase());

		if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
			log.info("<== preHandle - 调试模式不走认证.  url={}", uri);
			return true;
		}

		if (isHaveAccess(handler)) {
			log.info("<== preHandle - 不需要认证注解不走认证.  token={}");
			return true;
		}

		String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");
		log.info("<== preHandle - 权限拦截器.  token={}", token);
		LoginAuthDto loginUser = (UserTokenDto) redisTemplate.opsForValue().get(RedisKeyUtil.getAccessTokenKey(token));
		if (loginUser == null) {
			log.error("获取用户信息失败, 不允许操作");
			return false;
		}
		log.info("<== preHandle - 权限拦截器.  loginUser={}", loginUser);
		ThreadLocalMap.put(GlobalConstant.Sys.TOKEN_AUTH_DTO, loginUser);
		log.info("<== preHandle - 权限拦截器.  url={}, loginUser={}", uri, loginUser);
		return true;
	}

	private void handleException(HttpServletResponse res) throws IOException {
		res.resetBuffer();
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write("{\"code\":100009 ,\"message\" :\"解析token失败\"}");
		res.flushBuffer();
	}

	private boolean isHaveAccess(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		Method method = handlerMethod.getMethod();

		NoNeedAccessAuthentication responseBody = AnnotationUtils.findAnnotation(method, NoNeedAccessAuthentication.class);
		return responseBody != null;
	}

}
  