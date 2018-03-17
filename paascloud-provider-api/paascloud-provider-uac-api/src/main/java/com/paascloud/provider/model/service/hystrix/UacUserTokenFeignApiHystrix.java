package com.paascloud.provider.model.service.hystrix;


import com.paascloud.provider.model.service.UacUserTokenFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

/**
 * The class Uac user token feign api hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacUserTokenFeignApiHystrix implements UacUserTokenFeignApi {

	@Override
	public Wrapper<Integer> updateTokenOffLine() {
		return null;
	}
}
