package com.paascloud.provider.model.service;


import com.paascloud.provider.model.service.hystrix.UacUserTokenFeignApiHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The interface Uac user token feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacUserTokenFeignApiHystrix.class)
public interface UacUserTokenFeignApi {

	/**
	 * 超时token更新为离线.
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/uac/token/updateTokenOffLine")
	Wrapper<Integer> updateTokenOffLine();
}
