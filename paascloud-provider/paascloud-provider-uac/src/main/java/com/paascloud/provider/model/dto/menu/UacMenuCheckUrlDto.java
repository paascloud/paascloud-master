package com.paascloud.provider.model.dto.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * The class Uac menu check url dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "UacMenuCheckUrlDto")
public class UacMenuCheckUrlDto implements Serializable {
	private static final long serialVersionUID = 839437721293973234L;
	/**
	 * 菜单的id
	 */
	@ApiModelProperty(value = "菜单的id")
	private Long menuId;
	/**
	 * 菜单的url
	 */
	@ApiModelProperty(value = "菜单地址")
	@NotBlank(message = "菜单地址不能为空")
	private String url;


}
