package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.ProductDetailVo;

/**
 * The interface Mdc product service.
 *
 * @author paascloud.net @gmail.com
 */
public interface MdcProductService {
	/**
	 * Select by id product dto.
	 *
	 * @param productId the product id
	 *
	 * @return the product dto
	 */
	ProductDto selectById(Long productId);

	/**
	 * Gets product detail.
	 *
	 * @param productId the product id
	 *
	 * @return the product detail
	 */
	ProductDetailVo getProductDetail(Long productId);

	/**
	 * Update product stock by id int.
	 *
	 * @param productDto the product dto
	 *
	 * @return the int
	 */
	int updateProductStockById(ProductDto productDto);

	/**
	 * Gets main image.
	 *
	 * @param productId the product id
	 *
	 * @return the main image
	 */
	String getMainImage(Long productId);
}
