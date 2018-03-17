package com.paascloud.provider.model.dto.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Uac menu status dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "UacMenuStatusDto")
public class UacMenuStatusDto implements Serializable {
	private static final long serialVersionUID = 7834606418601316142L;
	/**
	 * 菜单的Id
	 */
	@ApiModelProperty(value = "菜单的Id", required = true)
	private Long id;
	/**
	 * 菜单的父Id
	 */
	@ApiModelProperty(value = "菜单的状态", required = true)
	private String status;

}
