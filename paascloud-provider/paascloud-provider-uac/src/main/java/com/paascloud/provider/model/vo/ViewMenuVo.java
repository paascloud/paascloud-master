package com.paascloud.provider.model.vo;

import com.paascloud.base.dto.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * The class Uac menu.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class ViewMenuVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 2713936834061516613L;
	/**
	 * 菜单编码
	 */
	private String menuCode;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 上级菜单名称
	 */
	private String parentMenuName;

	/**
	 * 层级(最多三级1,2,3)
	 */
	private Integer level;

	/**
	 * 是否叶子节点,1不是0是
	 */
	private Integer leaf;

	/**
	 * 序号
	 */
	private Integer number;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 系统ID
	 */
	private Long applicationId;
}