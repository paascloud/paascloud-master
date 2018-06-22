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

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.paascloud.PubUtils;
import com.paascloud.PublicUtil;
import com.paascloud.RedisKeyUtil;
import com.paascloud.base.constant.AliyunMqTopicConstants;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.exceptions.MdcBizException;
import com.paascloud.provider.manager.MdcProductManager;
import com.paascloud.provider.mapper.MdcProductMapper;
import com.paascloud.provider.model.constant.MallConstant;
import com.paascloud.provider.model.domain.MdcProduct;
import com.paascloud.provider.model.domain.MdcProductCategory;
import com.paascloud.provider.model.domain.MqMessageData;
import com.paascloud.provider.model.dto.MdcEditProductDto;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.dto.UpdateAttachmentDto;
import com.paascloud.provider.model.dto.oss.ElementImgUrlDto;
import com.paascloud.provider.model.dto.oss.OptBatchGetUrlRequest;
import com.paascloud.provider.model.dto.oss.OptGetUrlRequest;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.provider.model.vo.ProductVo;
import com.paascloud.provider.service.MdcProductCategoryService;
import com.paascloud.provider.service.MdcProductService;
import com.paascloud.provider.service.OpcRpcService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * The class Mdc product service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdcProductServiceImpl extends BaseService<MdcProduct> implements MdcProductService {

	@Resource
	private MdcProductMapper mdcProductMapper;
	@Resource
	private MdcProductCategoryService mdcProductCategoryService;
	@Resource
	private MdcProductManager mdcProductManager;
	@Resource
	private OpcRpcService opcRpcService;

	@Override
	public List<MdcProduct> selectByNameAndCategoryIds(String productName, List<Long> categoryIdList, String orderBy) {
		return mdcProductMapper.selectByNameAndCategoryIds(productName, categoryIdList, orderBy);
	}

	@Override
	public ProductDetailVo getProductDetail(Long productId) {
		logger.info("获取商品明细信息, productId={}", productId);
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.msg());

		MdcProduct product = mdcProductMapper.selectByPrimaryKey(productId);
		if (product == null) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021017, productId);
		}
		if (product.getStatus() != MallConstant.ProductStatusEnum.ON_SALE.getCode()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021017, productId);
		}

		return assembleProductDetailVo(product);
	}

	@Override
	public int updateProductStockById(ProductDto productDto) {
		Preconditions.checkArgument(!PubUtils.isNull(productDto, productDto.getId()), ErrorCodeEnum.MDC10021021.msg());
		return mdcProductMapper.updateProductStockById(productDto);
	}

	@Override
	public List<ProductVo> queryProductListWithPage(final MdcProduct mdcProduct) {
		return mdcProductMapper.queryProductListWithPage(mdcProduct);
	}

	@Override
	public void saveProduct(final MdcEditProductDto mdcEditProductDto, final LoginAuthDto loginAuthDto) {
		String productCode = mdcEditProductDto.getProductCode();
		MdcProduct product = new MdcProduct();
		BeanUtils.copyProperties(mdcEditProductDto, product);
		List<Long> categoryIdList = mdcEditProductDto.getCategoryIdList();
		Long categoryId = categoryIdList.get(categoryIdList.size() - 1);
		product.setCategoryId(categoryId);
		List<Long> attachmentIdList = mdcEditProductDto.getAttachmentIdList();
		product.setUpdateInfo(loginAuthDto);
		if (PublicUtil.isNotEmpty(attachmentIdList)) {
			product.setMainImage(String.valueOf(attachmentIdList.get(0)));
			product.setSubImages(Joiner.on(GlobalConstant.Symbol.COMMA).join(attachmentIdList));
		}
		MqMessageData mqMessageData;
		if (product.isNew()) {
			productCode = String.valueOf(generateId());
		} else {
			Preconditions.checkArgument(StringUtils.isNotEmpty(productCode), ErrorCodeEnum.MDC10021024.msg());
		}
		product.setProductCode(productCode);
		UpdateAttachmentDto updateAttachmentDto = new UpdateAttachmentDto(productCode, attachmentIdList, loginAuthDto);

		String body = JSON.toJSONString(updateAttachmentDto);
		String topic = AliyunMqTopicConstants.MqTagEnum.UPDATE_ATTACHMENT.getTopic();
		String tag = AliyunMqTopicConstants.MqTagEnum.UPDATE_ATTACHMENT.getTag();
		String key = RedisKeyUtil.createMqKey(topic, tag, product.getProductCode(), body);

		if (product.isNew() && PublicUtil.isNotEmpty(attachmentIdList)) {
			product.setId(generateId());
			mqMessageData = new MqMessageData(body, topic, tag, key);
			mdcProductManager.saveProduct(mqMessageData, product, true);
		} else if (product.isNew() && PublicUtil.isEmpty(attachmentIdList)) {
			product.setId(generateId());
			mdcProductMapper.insertSelective(product);
		} else {
			mqMessageData = new MqMessageData(body, topic, tag, key);
			mdcProductManager.saveProduct(mqMessageData, product, false);
		}
	}

	@Override
	public void deleteProductById(final Long id) {
		MdcProduct product = mdcProductMapper.selectByPrimaryKey(id);
		if (product != null) {
			String body = product.getProductCode();
			String topic = AliyunMqTopicConstants.MqTagEnum.DELETE_ATTACHMENT.getTopic();
			String tag = AliyunMqTopicConstants.MqTagEnum.DELETE_ATTACHMENT.getTag();
			String key = RedisKeyUtil.createMqKey(topic, tag, product.getProductCode(), body);
			MqMessageData mqMessageData = new MqMessageData(body, topic, tag, key);
			mdcProductManager.deleteProduct(mqMessageData, id);
		}
	}

	@Override
	public ProductVo getProductVo(final Long id) {
		MdcProduct mdcProduct = mdcProductMapper.selectByPrimaryKey(id);
		ProductVo productVo = new ModelMapper().map(mdcProduct, ProductVo.class);
		List<Long> categoryIdList = Lists.newArrayList();
		buildCategoryIdList(categoryIdList, mdcProduct.getCategoryId());
		// 获取分类节点集合
		Collections.reverse(categoryIdList);
		productVo.setCategoryIdList(categoryIdList);
		// 获取图片集合
		final OptBatchGetUrlRequest request = new OptBatchGetUrlRequest(mdcProduct.getProductCode());
		request.setEncrypt(true);
		List<ElementImgUrlDto> imgUrlList = opcRpcService.listFileUrl(request);
		productVo.setImgUrlList(imgUrlList);
		return productVo;
	}

	@Override
	public String getMainImage(final Long productId) {
		MdcProduct product = mdcProductMapper.selectByPrimaryKey(productId);
		String url = null;

		if (product != null) {
			final OptGetUrlRequest request = new OptGetUrlRequest();
			request.setEncrypt(true);
			request.setAttachmentId(Long.valueOf(product.getMainImage()));
			request.setExpires(3600 * 12 * 7L);
			url = opcRpcService.getFileUrl(request);
		}
		return url;
	}

	private List<Long> buildCategoryIdList(List<Long> categoryIdList, Long categoryId) {
		MdcProductCategory category = mdcProductCategoryService.getByCategoryId(categoryId);
		if (category != null) {
			categoryIdList.add(categoryId);
			buildCategoryIdList(categoryIdList, category.getPid());
		}
		return categoryIdList;
	}

	private ProductDetailVo assembleProductDetailVo(MdcProduct product) {
		ProductDetailVo productDetailVo = new ProductDetailVo();
		String mainImage = product.getMainImage();
		String subImages = product.getSubImages();
		if (StringUtils.isNotEmpty(mainImage)) {
			// 图片查询
			OptGetUrlRequest request = new OptGetUrlRequest();
			request.setAttachmentId(Long.valueOf(mainImage));
			request.setEncrypt(true);
			String url = opcRpcService.getFileUrl(request);
			productDetailVo.setMainImage(url);
		}
		if (StringUtils.isNotEmpty(subImages)) {
			List<String> urlList = Lists.newArrayList();
			List<String> subImageList = Splitter.on(GlobalConstant.Symbol.COMMA).trimResults().splitToList(subImages);
			for (final String subImage : subImageList) {
				OptGetUrlRequest request = new OptGetUrlRequest();
				request.setAttachmentId(Long.valueOf(subImage));
				request.setEncrypt(true);
				String url = opcRpcService.getFileUrl(request);
				urlList.add(url);
			}
			productDetailVo.setSubImages(Joiner.on(GlobalConstant.Symbol.COMMA).join(urlList));
		}

		productDetailVo.setId(product.getId());
		productDetailVo.setSubtitle(product.getSubtitle());
		productDetailVo.setPrice(product.getPrice());

		productDetailVo.setDetail(product.getDetail());
		productDetailVo.setName(product.getName());
		productDetailVo.setStatus(product.getStatus());
		productDetailVo.setStock(product.getStock());

		productDetailVo.setImageHost("");

		MdcProductCategory category = mdcProductCategoryService.getByCategoryId(product.getId());
		if (category == null) {
			//默认根节点
			productDetailVo.setPid(0L);
		} else {
			productDetailVo.setPid(category.getPid());
		}

		return productDetailVo;
	}
}
