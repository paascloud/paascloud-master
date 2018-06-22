/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：CoreHeaderInterceptor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.core.interceptor;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The class Core header interceptor.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
public class CoreHeaderInterceptor extends HandlerInterceptorAdapter {
	/**
	 * The constant HEADER_LABEL.
	 */
	public static final String HEADER_LABEL = "x-label";
	/**
	 * The constant HEADER_LABEL_SPLIT.
	 */
	public static final String HEADER_LABEL_SPLIT = ",";

	/**
	 * The constant LABEL.
	 */
	public static final HystrixRequestVariableDefault<List<String>> LABEL = new HystrixRequestVariableDefault<>();

	private static void initHystrixRequestContext(String labels) {
		log.info("LABEL={}", labels);
		if (!HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.initializeContext();
		}

		if (!StringUtils.isEmpty(labels)) {
			CoreHeaderInterceptor.LABEL.set(Arrays.asList(labels.split(CoreHeaderInterceptor.HEADER_LABEL_SPLIT)));
		} else {
			CoreHeaderInterceptor.LABEL.set(Collections.emptyList());
		}
	}

	private static void shutdownHystrixRequestContext() {
		if (HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.getContextForCurrentThread().shutdown();
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		CoreHeaderInterceptor.initHystrixRequestContext(request.getHeader(CoreHeaderInterceptor.HEADER_LABEL));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		CoreHeaderInterceptor.shutdownHystrixRequestContext();
	}
}
