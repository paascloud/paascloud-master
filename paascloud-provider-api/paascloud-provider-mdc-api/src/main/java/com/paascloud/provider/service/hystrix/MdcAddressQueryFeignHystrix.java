package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.AddressDTO;
import com.paascloud.provider.service.MdcAddressQueryFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

/**
 * The class Mdc product query feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MdcAddressQueryFeignHystrix implements MdcAddressQueryFeignApi {

	@Override
	public Wrapper<AddressDTO> getById(final Long addressId) {
		return null;
	}
}
