/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategory.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import com.paascloud.provider.model.enums.MdcCategoryStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Mdc product category.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_mdc_product_category")
@Alias(value = "mdcProductCategory")
public class MdcProductCategory extends BaseEntity {

	private static final long serialVersionUID = 6966836239624795099L;

	/**
	 * 分类编码
	 */
	@Column(name = "category_code")
	private String categoryCode;

	/**
	 * 首图的流水号
	 */
	@Column(name = "img_id")
	private Long imgId;

	/**
	 * 父类别id当id=0时说明是根节点,一级类别
	 */
	private Long pid;

	/**
	 * 类别名称
	 */
	private String name;

	/**
	 * 类别状态1-启用,2-禁用
	 * {@link MdcCategoryStatusEnum}
	 */
	private Integer status;

	/**
	 * 排序编号,同类展示顺序,数值相等则自然排序
	 */
	@Column(name = "sort_order")
	private Integer sortOrder;

}