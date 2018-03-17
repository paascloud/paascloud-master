package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.OrderDetailDto;
import com.paascloud.provider.service.OmcOrderDetailQueryFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class Omc order detail query feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class OmcOrderDetailQueryFeignHystrix implements OmcOrderDetailQueryFeignApi {

	@Override
	public Wrapper<List<OrderDetailDto>> getListByOrderNoUserId(final String orderNo, final Long userId) {
		return null;
	}
}
