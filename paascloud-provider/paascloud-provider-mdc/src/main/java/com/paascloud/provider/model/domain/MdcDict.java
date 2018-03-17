package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 数据字典.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_mdc_dict")
@Alias(value = "mdcDict")
public class MdcDict extends BaseEntity {

	private static final long serialVersionUID = -4257346287088805156L;
	/**
	 * 字典类型 -0 常量 - 1 文件夹
	 */
	private Integer type;

	/**
	 * 枚举编码
	 */
	@Column(name = "dict_key")
	private String dictKey;

	/**
	 * 枚举扩展字段
	 */
	@Column(name = "extend_key")
	private String extendKey;

	/**
	 * 枚举值
	 */
	@Column(name = "dict_value")
	private String dictValue;

	/**
	 * 字典编码
	 */
	@Column(name = "dict_code")
	private String dictCode;

	/**
	 * 字典名称
	 */
	@Column(name = "dict_name")
	private String dictName;

	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 序号
	 */
	private Integer number;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;
}