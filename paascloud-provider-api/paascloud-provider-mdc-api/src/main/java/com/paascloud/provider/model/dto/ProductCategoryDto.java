package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.List;

/**
 * The class Product category dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductCategoryDto implements Serializable {
	private static final long serialVersionUID = -5217129132596232253L;
	@ApiModelProperty(value = "类别ID")
	private Long categoryId;
	@ApiModelProperty(value = "类别名称")
	private String categoryName;
	@ApiModelProperty(value = "首图URL")
	private String imgUrl;

	private List<ProductCategoryDto> categoryList;

}
