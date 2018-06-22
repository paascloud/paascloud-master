/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacAction.java
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
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * The class Uac action.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_uac_action")
@Alias(value = "uacAction")
public class UacAction extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 6943147531573339665L;

	/**
	 * 资源路径
	 */
	private String url;

	/**
	 * 权限名称
	 */
	@Column(name = "action_name")
	private String actionName;

	/**
	 * 权限编码
	 */
	@Column(name = "action_code")
	private String actionCode;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 菜单ID
	 */
	@Column(name = "menu_id")
	private Long menuId;

	/**
	 * 菜单ID
	 */
	@Transient
	private List<Long> menuIdList;
}