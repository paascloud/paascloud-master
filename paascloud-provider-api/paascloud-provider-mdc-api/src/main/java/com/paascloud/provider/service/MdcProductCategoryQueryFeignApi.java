package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.annotation.NoNeedAccessAuthentication;
import com.paascloud.provider.model.dto.ProductCategoryDto;
import com.paascloud.provider.model.dto.ProductReqDto;
import com.paascloud.provider.service.hystrix.MdcProductCategoryQueryFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * The interface Mdc product category query feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-mdc", configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcProductCategoryQueryFeignHystrix.class)
public interface MdcProductCategoryQueryFeignApi {

	/**
	 * 查询分类信息.
	 *
	 * @param pid the pid
	 *
	 * @return the product category data
	 */
	@PostMapping(value = "/api/productCategory/getProductCategoryDtoByPid/{pid}")
	@NoNeedAccessAuthentication
	Wrapper<List<ProductCategoryDto>> getProductCategoryData(@PathVariable("pid") Long pid);

	/**
	 * 查询商品列表.
	 *
	 * @param productReqDto the product req dto
	 *
	 * @return the product list
	 */
	@PostMapping(value = "/api/product/getProductList")
	@NoNeedAccessAuthentication
	Wrapper<PageInfo> getProductList(@RequestBody ProductReqDto productReqDto);

}
