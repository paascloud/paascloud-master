package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.provider.service.MdcProductQueryFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

/**
 * The class Mdc product query feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MdcProductQueryFeignHystrix implements MdcProductQueryFeignApi {

	@Override
	public Wrapper<ProductDetailVo> getProductDetail(final Long productId) {
		return null;
	}

	@Override
	public Wrapper<ProductDto> selectById(final Long productId) {
		return null;
	}
}
