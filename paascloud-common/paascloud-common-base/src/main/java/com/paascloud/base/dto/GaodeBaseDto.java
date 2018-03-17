package com.paascloud.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Gaode base dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel
public class GaodeBaseDto implements Serializable{

	private static final long serialVersionUID = 5894304327211503218L;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private String status;

	/**
	 * 响应信息
	 */
	@ApiModelProperty(value = "响应信息")
	private String info;

	/**
	 * 响应编码
	 */
	@ApiModelProperty(value = "响应编码")
	private String infocode;
}
