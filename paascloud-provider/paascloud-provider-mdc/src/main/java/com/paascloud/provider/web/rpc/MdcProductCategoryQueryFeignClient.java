package com.paascloud.provider.web.rpc;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.paascloud.PublicUtil;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.model.domain.MdcProduct;
import com.paascloud.provider.model.domain.MdcProductCategory;
import com.paascloud.provider.model.dto.ProductCategoryDto;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.dto.ProductReqDto;
import com.paascloud.provider.service.MdcProductCategoryQueryFeignApi;
import com.paascloud.provider.service.MdcProductCategoryService;
import com.paascloud.provider.service.MdcProductService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc product category query feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - MdcProductCategoryQueryFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcProductCategoryQueryFeignClient extends BaseService<MdcProductCategory> implements MdcProductCategoryQueryFeignApi {

	@Resource
	private MdcProductCategoryService mdcProductCategoryService;
	@Resource
	private MdcProductService mdcProductService;

	/**
	 * 获取商品品类信息.
	 *
	 * @param pid the pid
	 *
	 * @return the product category data
	 */
	@Override
	@ApiOperation(httpMethod = "POST", value = "获取商品品类信息")
	public Wrapper<List<ProductCategoryDto>> getProductCategoryData(@PathVariable("pid") Long pid) {
		logger.info("获取商品品类信息. pid={}", pid);
		List<ProductCategoryDto> list;
		if (0L == pid) {
			// 查询所有一级分类

			list = mdcProductCategoryService.getCategoryDtoList(pid);
			for (ProductCategoryDto productCategoryDto : list) {
				Long categoryPid = productCategoryDto.getCategoryId();
				if (PublicUtil.isEmpty(categoryPid)) {
					continue;
				}
				List<ProductCategoryDto> product2CategoryDtoList = mdcProductCategoryService.getCategoryDtoList(categoryPid);
				if (product2CategoryDtoList.size() > 5) {
					product2CategoryDtoList = product2CategoryDtoList.subList(0, 4);
				}
				productCategoryDto.setCategoryList(product2CategoryDtoList);
			}

		} else {
			// 根据分类ID 查询分类下的所有二级分类
			list = mdcProductCategoryService.getCategoryDtoList(pid);
		}

		return WrapMapper.ok(list);
	}

	/**
	 * 获取商品列表信息.
	 *
	 * @param productReqDto the product req dto
	 *
	 * @return the product list
	 */
	@Override
	@ApiOperation(httpMethod = "POST", value = "获取商品列表信息")
	public Wrapper<PageInfo> getProductList(@RequestBody ProductReqDto productReqDto) {
		logger.info("获取商品列表信息. productReqDto={}", productReqDto);
		Long categoryId = productReqDto.getCategoryId();
		String keyword = productReqDto.getKeyword();
		Integer pageNum = productReqDto.getPageNum();
		Integer pageSize = productReqDto.getPageSize();
		String orderBy = productReqDto.getOrderBy();
		if (StringUtils.isBlank(keyword) && null == categoryId) {
			return WrapMapper.ok(new PageInfo());
		}
		List<Long> categoryIdList = Lists.newArrayList();

		if (categoryId != null) {
			MdcProductCategory category = mdcProductCategoryService.selectByKey(categoryId);
			if (category == null && StringUtils.isBlank(keyword)) {
				// 没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
				PageHelper.startPage(pageNum, pageSize);
				return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, new PageInfo());
			}
			categoryIdList = mdcProductCategoryService.selectCategoryAndChildrenById(categoryId);
		}

		PageHelper.startPage(pageNum, pageSize);
		//排序处理
		List<MdcProduct> productList = mdcProductService.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, PublicUtil.isEmpty(categoryIdList) ? null : categoryIdList, orderBy);

		List<ProductDto> productListVoList = Lists.newArrayList();
		for (MdcProduct product : productList) {
			ProductDto productListVo = assembleProductListVo(product);
			String url = mdcProductService.getMainImage(product.getId());
			productListVo.setMainImage(url);
			productListVoList.add(productListVo);
		}


		return PublicUtil.isNotEmpty(productListVoList) ? WrapMapper.ok(new PageInfo<>(productListVoList)) : WrapMapper.ok();
	}

	private ProductDto assembleProductListVo(MdcProduct product) {
		ProductDto productListVo = new ProductDto();
		productListVo.setId(product.getId());
		productListVo.setName(product.getName());
		productListVo.setCategoryId(product.getId());
		productListVo.setImageHost("");
		productListVo.setMainImage(product.getMainImage());
		productListVo.setPrice(product.getPrice());
		productListVo.setSubtitle(product.getSubtitle());
		productListVo.setStatus(product.getStatus());
		return productListVo;
	}
}
