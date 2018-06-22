/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUser.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paascloud.core.mybatis.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * The class Uac user.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_uac_user")
@Alias(value = "uacUser")
public class UacUser extends BaseEntity {
	private static final long serialVersionUID = 5465775492730080699L;

	/**
	 * 登录名
	 */
	@Column(name = "login_name")
	private String loginName;

	/**
	 * 登录密码
	 */
	@Column(name = "login_pwd")
	private String loginPwd;

	/**
	 * 盐,用于shiro加密, 字段停用
	 */
	private String salt;

	/**
	 * 工号
	 */
	@Column(name = "user_code")
	private String userCode;

	/**
	 * 姓名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 手机号
	 */
	@Column(name = "mobile_no")
	private String mobileNo;

	/**
	 * 状态
	 */
	private String status;

	private String email;

	/**
	 * 用户来源
	 */
	@Column(name = "user_source")
	private String userSource;

	/**
	 * 操作员类型（2000伙伴, 3000客户, 1000运营）
	 */
	private String type;

	/**
	 * 最后登录IP地址
	 */
	@Column(name = "last_login_ip")
	private String lastLoginIp;

	/**
	 * 最后登录位置
	 */
	@Column(name = "last_login_location")
	private String lastLoginLocation;

	/**
	 * 描述
	 */
	private String remark;

	/**
	 * 最后登录时间
	 */
	@Column(name = "last_login_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;

	/**
	 * 是否更改过密码
	 */
	@Column(name = "is_changed_pwd")
	private Short isChangedPwd;

	/**
	 * 连续输错密码次数（连续5次输错就冻结帐号）
	 */
	@Column(name = "pwd_error_count")
	private Short pwdErrorCount;

	/**
	 * 最后输错密码时间
	 */
	@Column(name = "pwd_error_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date pwdErrorTime;

	/**
	 * 用户所属的组织ID
	 */
	@ApiModelProperty(value = "用户所属的组织ID")
	@Transient
	private Long groupId;

	@ApiModelProperty(value = "用户所属的组织名称")
	@Transient
	private String groupName;
}