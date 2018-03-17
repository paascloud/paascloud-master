package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;


/**
 * The class Mdc category check name dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel
public class MdcCategoryCheckNameDto implements Serializable {
	private static final long serialVersionUID = 8687848883145768024L;
	/**
	 * 菜单的id
	 */
	@ApiModelProperty(value = "分类id")
	private Long categoryId;
	/**
	 * 菜单的url
	 */
	@ApiModelProperty(value = "分类名称")
	@NotBlank(message = "分类名称不能为空")
	private String categoryName;
}
