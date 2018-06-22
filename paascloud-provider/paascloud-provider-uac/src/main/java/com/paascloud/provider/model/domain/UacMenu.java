/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMenu.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Uac menu.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_uac_menu")
@Alias(value = "uacMenu")
public class UacMenu extends BaseEntity {
	private static final long serialVersionUID = 454644589405700059L;

	/**
	 * 菜单编码
	 */
	@Column(name = "menu_code")
	private String menuCode;

	/**
	 * 菜单名称
	 */
	@Column(name = "menu_name")
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
	@Column(name = "application_id")
	private Long applicationId;
}