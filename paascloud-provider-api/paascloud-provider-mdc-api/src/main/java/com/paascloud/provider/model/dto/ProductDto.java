package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The class Product dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class ProductDto implements Serializable {
	private static final long serialVersionUID = 6932649538854879183L;
	@ApiModelProperty("货品ID")
	private Long id;
	@ApiModelProperty("分类ID")
	private Long categoryId;
	private String name;
	@ApiModelProperty("子标题")
	private String subtitle;
	@ApiModelProperty("首图")
	private String mainImage;
	@ApiModelProperty("价格")
	private BigDecimal price;
	@ApiModelProperty("状态")
	private Integer status;
	@ApiModelProperty("图片服务器前缀")
	private String imageHost;
	@ApiModelProperty("库存数量")
	private Integer stock;
	@ApiModelProperty("商品序列号")
	private String productCode;
	@ApiModelProperty("变化的库存数量")
	private Integer changeStock;
}
