package com.paascloud.provider.model.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * The class Mdc edit category dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class MdcEditCategoryDto implements Serializable {
	private static final long serialVersionUID = 8578699727403591194L;
	/**
	 * 首图的流水号
	 */
	private Long imgId;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 父类别id当id=0时说明是根节点,一级类别
	 */
	private Long pid;

	/**
	 * 类别名称
	 */
	private String name;

	/**
	 * 类别状态1-正常,2-已废弃
	 */
	private Integer status;

	/**
	 * 排序编号,同类展示顺序,数值相等则自然排序
	 */
	private Integer sortOrder;

	/**
	 * 分类编码
	 */
	private String categoryCode;
}
