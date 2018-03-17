package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.service.MdcProductFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

/**
 * The class Mdc product feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MdcProductFeignHystrix implements MdcProductFeignApi {

	@Override
	public Wrapper<Integer> updateProductStockById(final ProductDto productDto) {
		return null;
	}

	@Override
	public Wrapper<String> getMainImage(final Long productId) {
		return null;
	}
}
