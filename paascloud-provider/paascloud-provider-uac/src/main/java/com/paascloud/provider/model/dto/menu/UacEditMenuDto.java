package com.paascloud.provider.model.dto.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * The class Uac menu add dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "菜单新增Dto")
public class UacEditMenuDto implements Serializable {
	private static final long serialVersionUID = -9219271060355200235L;

	/**
	 * 菜单的Id
	 */
	@ApiModelProperty(value = "菜单的Id", required = true)
	private Long id;
	/**
	 * 菜单的父Id
	 */
	@ApiModelProperty(value = "菜单的父Id", required = true)
	@NotBlank(message = "上级菜单不能为空")
	private Long pid;
	/**
	 * 菜单编码
	 */
	@ApiModelProperty(value = "菜单编码", required = true)
	@NotBlank(message = "菜单编码不能为空")
	private String menuCode;
	/**
	 * Icon编码
	 */
	@ApiModelProperty(value = "Icon编码")
	private String icon;
	/**
	 * 菜单名称
	 */
	@ApiModelProperty(value = "菜单名称", required = true)
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;
	/**
	 * 菜单排序
	 */
	@ApiModelProperty(value = "菜单排序", required = true)
	private Integer number;
	/**
	 * 菜单地址
	 */
	@ApiModelProperty(value = "菜单地址", required = true)
	@NotBlank(message = "菜单地址不能为空")
	private String url;
	/**
	 * 备注说明
	 */
	@ApiModelProperty(value = "备注说明")
	private String remark;
}
