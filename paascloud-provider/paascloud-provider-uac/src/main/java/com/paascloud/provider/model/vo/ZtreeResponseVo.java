/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ZtreeResponseVo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.vo;


import com.paascloud.provider.model.enums.ZtreeAuthTypeEnum;

import java.io.Serializable;

/**
 * The class Z tree response vo.
 *
 * @author paascloud.net@gmail.com
 */
public class ZtreeResponseVo implements Serializable {

	private static final long serialVersionUID = -5567984072691093330L;

	private Long id;

	private Long pId;

	private String name;

	private boolean open;

	/**
	 * 菜单选中状态, true选中, false为未选中
	 */
	private boolean checked;

	private String iconOpen;

	private String iconClose;

	/**
	 * {@link ZtreeAuthTypeEnum}
	 */
	private String type;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Long getpId() {
		return pId;
	}

	/**
	 * Sets id.
	 *
	 * @param pId the p id
	 */
	public void setpId(Long pId) {
		this.pId = pId;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Is open boolean.
	 *
	 * @return the boolean
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Sets open.
	 *
	 * @param open the open
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * Gets icon open.
	 *
	 * @return the icon open
	 */
	public String getIconOpen() {
		return iconOpen;
	}

	/**
	 * Sets icon open.
	 *
	 * @param iconOpen the icon open
	 */
	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	/**
	 * Gets icon close.
	 *
	 * @return the icon close
	 */
	public String getIconClose() {
		return iconClose;
	}

	/**
	 * Sets icon close.
	 *
	 * @param iconClose the icon close
	 */
	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Equals boolean.
	 *
	 * @param o the o
	 *
	 * @return the boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ZtreeResponseVo that = (ZtreeResponseVo) o;

		return id != null ? id.equals(that.id) : that.id == null;

	}

	/**
	 * Hash code int.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	/**
	 * Is checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * Sets checked.
	 *
	 * @param checked the checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
