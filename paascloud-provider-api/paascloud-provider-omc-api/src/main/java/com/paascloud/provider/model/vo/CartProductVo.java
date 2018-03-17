package com.paascloud.provider.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The class Cart product vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class CartProductVo implements Serializable {

	private static final long serialVersionUID = 975884590599516501L;
	private Long id;
	private Long userId;
	private Long productId;
	/**
	 * 购物车中此商品的数量
	 */
	private Integer quantity;
	private String productName;
	private String productSubtitle;
	private String productMainImage;
	private BigDecimal productPrice;
	private Integer productStatus;
	private BigDecimal productTotalPrice;
	private Integer productStock;
	/**
	 * 此商品是否勾选
	 */
	private Integer checked;
	/**
	 * 限制数量的一个返回结果
	 */
	private String limitQuantity;

}
