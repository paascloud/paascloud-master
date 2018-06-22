/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：CoreHttpRequestInterceptor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * The class Core http request interceptor.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
public class CoreHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	/**
	 * Intercept client http response.
	 *
	 * @param request   the request
	 * @param body      the body
	 * @param execution the execution
	 *
	 * @return the client http response
	 *
	 * @throws IOException the io exception
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
	                                    ClientHttpRequestExecution execution) throws IOException {
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);

		String header = StringUtils.collectionToDelimitedString(
				CoreHeaderInterceptor.LABEL.get(),
				CoreHeaderInterceptor.HEADER_LABEL_SPLIT);
		log.info("header={} ", header);
		requestWrapper.getHeaders().add(CoreHeaderInterceptor.HEADER_LABEL, header);

		return execution.execute(requestWrapper, body);
	}
}
