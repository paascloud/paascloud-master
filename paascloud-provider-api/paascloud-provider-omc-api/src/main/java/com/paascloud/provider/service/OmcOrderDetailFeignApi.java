package com.paascloud.provider.service;

import com.paascloud.provider.service.hystrix.OmcOrderDetailFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * The interface Omc order detail feign api.
 *
 * @author paascloud.net@gmail.com
 */
@FeignClient(value = "paascloud-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcOrderDetailFeignHystrix.class)
public interface OmcOrderDetailFeignApi {
}
