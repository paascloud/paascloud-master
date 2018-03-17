package com.paascloud.provider.service;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.MdcProduct;
import com.paascloud.provider.model.dto.MdcEditProductDto;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.provider.model.vo.ProductVo;

import java.util.List;

/**
 * The interface Mdc product service.
 *
 * @author paascloud.net @gmail.com
 */
public interface MdcProductService extends IService<MdcProduct> {

	/**
	 * Select by name and category ids list.
	 *
	 * @param productName    the product name
	 * @param categoryIdList the category id list
	 * @param orderBy        the order by
	 *
	 * @return the list
	 */
	List<MdcProduct> selectByNameAndCategoryIds(String productName, List<Long> categoryIdList, String orderBy);

	/**
	 * 根据商品ID查询商品详细信息.
	 *
	 * @param productId the product id
	 *
	 * @return the product detail
	 */
	ProductDetailVo getProductDetail(Long productId);

	/**
	 * 更新商品库存.
	 *
	 * @param productDto the product dto
	 *
	 * @return the int
	 */
	int updateProductStockById(ProductDto productDto);

	/**
	 * 查询商品集合.
	 *
	 * @param mdcProduct the mdc product
	 *
	 * @return the list
	 */
	List<ProductVo> queryProductListWithPage(MdcProduct mdcProduct);

	/**
	 * 新增商品信息.
	 *
	 * @param mdcEditProductDto the mdc edit product dto
	 * @param loginAuthDto      the login auth dto
	 */
	void saveProduct(MdcEditProductDto mdcEditProductDto, LoginAuthDto loginAuthDto);

	/**
	 * 删除商品信息.
	 *
	 * @param id the id
	 */
	void deleteProductById(Long id);

	/**
	 * Gets product vo.
	 *
	 * @param id the id
	 *
	 * @return the product vo
	 */
	ProductVo getProductVo(Long id);

	/**
	 * Gets main image.
	 *
	 * @param productId the product id
	 *
	 * @return the main image
	 */
	String getMainImage(Long productId);
}
