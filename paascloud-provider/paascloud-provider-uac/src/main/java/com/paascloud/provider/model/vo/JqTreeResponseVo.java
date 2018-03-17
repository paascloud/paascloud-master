package com.paascloud.provider.model.vo;


import lombok.Data;

import java.util.Date;

/**
 * The class Jq tree response vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class JqTreeResponseVo {

	/**
	 * 菜单ID
	 */
	private Long id;

	/**
	 * 父菜单ID
	 */
	private Long pid;

	/**
	 * 层级(最多三级1,2,3)
	 */
	private Integer level;

	/**
	 * 是否叶子节点,1不是0是
	 */
	private boolean isLeaf;

	private boolean expanded;

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
	private int status;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 序号
	 */
	private String number;

	/**
	 * 备注(研发中心)
	 */
	private String remark;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建人ID
	 */
	private Long creatorId;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 最近操作人
	 */
	private String lastOperator;

	/**
	 * 最后操作人ID
	 */
	private String lastOperatorId;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
