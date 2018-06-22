/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：GroupZtreeVo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * The class Group z tree vo.
 *
 * @author paascloud.net@gmail.com
 */
@Getter
@Setter
public class GroupZtreeVo extends ZtreeResponseVo implements Serializable {

	private static final long serialVersionUID = 8835704500635133372L;
	/**
	 * 组织类型
	 */
	private String groupType;
	/**
	 * 组织编码
	 */
	private String groupCode;

	/**
	 * 组织流水号
	 */
	private String groupSerialNo;

	/**
	 * 组织名称
	 */
	private String groupName;

	/**
	 * 详细地址
	 */
	private String groupAddress;

	/**
	 * 所在城市
	 */
	private String groupCity;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 联系人电话
	 */
	private String contactPhone;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 图标样式
	 */
	private String iconSkin;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdTime;

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 层级
	 */
	private Integer level;
	/**
	 * 是否是节点
	 */
	private Integer leaf;

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
		if (!super.equals(o)) {
			return false;
		}

		GroupZtreeVo that = (GroupZtreeVo) o;

		return this.getId().equals(that.getId());

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * To string string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "GroupZTreeVo{" +
				"groupType='" + groupType + '\'' +
				", groupCode='" + groupCode + '\'' +
				", groupSerialNo='" + groupSerialNo + '\'' +
				", groupName='" + groupName + '\'' +
				", groupAddress='" + groupAddress + '\'' +
				", groupCity='" + groupCity + '\'' +
				", contact='" + contact + '\'' +
				", contactPhone='" + contactPhone + '\'' +
				", creator='" + creator + '\'' +
				", iconSkin='" + iconSkin + '\'' +
				", createdTime=" + createdTime +
				", status='" + status + '\'' +
				", level=" + level +
				", leaf=" + leaf +
				'}';
	}
}
