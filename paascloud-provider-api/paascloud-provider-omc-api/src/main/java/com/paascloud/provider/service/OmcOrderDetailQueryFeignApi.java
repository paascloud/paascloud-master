package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.OrderDetailDto;
import com.paascloud.provider.service.hystrix.OmcOrderDetailQueryFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * The interface Omc order detail query feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcOrderDetailQueryFeignHystrix.class)
public interface OmcOrderDetailQueryFeignApi {
	/**
	 * Gets list by order no user id.
	 *
	 * @param orderNo the order no
	 * @param userId  the user id
	 *
	 * @return the list by order no user id
	 */
	@PostMapping(value = "/api/orderDetail/getListByOrderNoUserId/{userId}/{orderNo}")
	Wrapper<List<OrderDetailDto>> getListByOrderNoUserId(@PathVariable("orderNo") String orderNo, @PathVariable("userId") Long userId);
}
