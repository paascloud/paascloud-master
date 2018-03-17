package com.paascloud.provider.model.vo;

import com.paascloud.base.dto.BaseTree;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class Mdc dict vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MdcCategoryVo extends BaseTree {
	private static final long serialVersionUID = 5310269803239754048L;
	/**
	 * 父分类名称
	 */
	private String parentCategoryName;
	/**
	 * 字典类型 -0 常量 - 1 文件夹
	 */
	private Integer type;

	/**
	 * 字典名称
	 */
	private String name;

	/**
	 * 序号
	 */
	private Integer number;

	/**
	 * 状态
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
