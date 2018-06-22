/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;


import com.google.common.base.Preconditions;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.provider.exceptions.MdcBizException;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.provider.service.MdcProductFeignApi;
import com.paascloud.provider.service.MdcProductQueryFeignApi;
import com.paascloud.provider.service.MdcProductService;
import com.paascloud.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The class Mdc product service.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Service
public class MdcProductServiceImpl implements MdcProductService {
	@Resource
	private MdcProductQueryFeignApi mdcProductQueryFeignApi;
	@Resource
	private MdcProductFeignApi mdcProductFeignApi;

	@Override
	public ProductDto selectById(Long productId) {
		log.info("查询商品信息. productId={}", productId);
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.msg());
		Wrapper<ProductDto> productDtoWrapper = mdcProductQueryFeignApi.selectById(productId);

		if (productDtoWrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021003);
		}
		if (productDtoWrapper.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021004, productId);
		}
		return productDtoWrapper.getResult();

	}

	@Override
	public ProductDetailVo getProductDetail(Long productId) {
		log.info("获取商品详情. productId={}", productId);
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.msg());

		Wrapper<ProductDetailVo> wrapper = mdcProductQueryFeignApi.getProductDetail(productId);

		if (wrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		if (wrapper.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021004, productId);
		}
		return wrapper.getResult();
	}

	@Override
	public int updateProductStockById(ProductDto productDto) {
		Preconditions.checkArgument(productDto.getId() != null, ErrorCodeEnum.MDC10021021.msg());
		Wrapper<Integer> wrapper = mdcProductFeignApi.updateProductStockById(productDto);
		if (wrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		if (wrapper.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021022, productDto.getId());
		}
		return wrapper.getResult();
	}

	@Override
	public String getMainImage(final Long productId) {
		Wrapper<String> wrapper = mdcProductFeignApi.getMainImage(productId);
		if (wrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		return wrapper.getResult();
	}
}
