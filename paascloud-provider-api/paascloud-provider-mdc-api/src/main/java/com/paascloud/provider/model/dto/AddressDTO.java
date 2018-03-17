package com.paascloud.provider.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * The class Address dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = -154171216711236047L;
	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private Long id;

	/**
	 * 地址名称
	 */
	@ApiModelProperty(value = "地址名称")
	private String name;

	/**
	 * 父ID
	 */
	@ApiModelProperty(value = "父ID")
	private Long pid;

	/**
	 * 城市编码
	 */
	@ApiModelProperty(value = "城市编码")
	private String cityCode;

	/**
	 * 级别（省市区县）
	 */
	@ApiModelProperty(value = "级别（省市区县）")
	private Integer level;

	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String adCode;

	/**
	 * 行政区边界坐标点
	 */
	@ApiModelProperty(value = "行政区边界坐标点")
	private String polyline;

	/**
	 * 城市中心点
	 */
	@ApiModelProperty(value = "城市中心点")
	private String center;
}
