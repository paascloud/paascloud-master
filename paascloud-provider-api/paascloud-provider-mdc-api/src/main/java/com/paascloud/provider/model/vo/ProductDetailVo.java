package com.paascloud.provider.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The class Product detail vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class ProductDetailVo implements Serializable {
	private static final long serialVersionUID = 4852861985938951261L;
	private Long id;
	private String name;
	private String subtitle;
	private String mainImage;
	private String subImages;
	private String detail;
	private BigDecimal price;
	private Integer stock;
	private Integer status;
	private String imageHost;
	private Long pid;
}
