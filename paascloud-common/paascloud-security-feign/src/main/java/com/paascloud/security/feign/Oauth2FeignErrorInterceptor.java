/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：Oauth2FeignErrorInterceptor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;


/**
 * The class Feign error interceptor.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class Oauth2FeignErrorInterceptor implements ErrorDecoder {
	private final ErrorDecoder defaultErrorDecoder = new Default();

	/**
	 * Decode exception.
	 *
	 * @param methodKey the method key
	 * @param response  the response
	 *
	 * @return the exception
	 */
	@Override
	public Exception decode(final String methodKey, final Response response) {
		if (response.status() >= HttpStatus.BAD_REQUEST.value() && response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			return new HystrixBadRequestException("request exception wrapper");
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap map = mapper.readValue(response.body().asInputStream(), HashMap.class);
			Integer code = (Integer) map.get("code");
			String message = (String) map.get("message");
			if (code != null) {
				ErrorCodeEnum anEnum = ErrorCodeEnum.getEnum(code);
				if (anEnum != null) {
					if (anEnum == ErrorCodeEnum.GL99990100) {
						throw new IllegalArgumentException(message);
					} else {
						throw new BusinessException(anEnum);
					}
				} else {
					throw new BusinessException(ErrorCodeEnum.GL99990500, message);
				}
			}
		} catch (IOException e) {
			log.info("Failed to process response body");
		}
		return defaultErrorDecoder.decode(methodKey, response);
	}
}
