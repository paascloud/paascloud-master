package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * The class Uac user token.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Alias(value = "uacUserToken")
@Table(name = "pc_uac_user_token")
public class UacUserToken extends BaseEntity {
	private static final long serialVersionUID = 4341237600124353997L;
	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 登录名
	 */
	@Column(name = "login_name")
	private String loginName;

	/**
	 * 姓名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 登陆人Ip地址
	 */
	@Column(name = "login_ip")
	private String loginIp;

	/**
	 * 登录地址
	 */
	@Column(name = "login_location")
	private String loginLocation;

	/**
	 * 登录地址
	 */
	@Column(name = "login_time")
	private Date loginTime;

	/**
	 * 操作系统
	 */
	private String os;

	/**
	 * 浏览器类型
	 */
	private String browser;

	/**
	 * 访问token
	 */
	@Column(name = "access_token")
	private String accessToken;

	/**
	 * 刷新token
	 */
	@Column(name = "refresh_token")
	private String refreshToken;

	/**
	 * 访问token的生效时间(秒)
	 */
	@Column(name = "access_token_validity")
	private Integer accessTokenValidity;

	/**
	 * 刷新token的生效时间(秒)
	 */
	@Column(name = "refresh_token_validity")
	private Integer refreshTokenValidity;

	/**
	 * 0 在线 10已刷新 20 离线
	 */
	private Integer status;

	/**
	 * 组织流水号
	 */
	@Column(name = "group_id")
	private Long groupId;

	/**
	 * 组织名称
	 */
	@Column(name = "group_name")
	private String groupName;

	/**
	 * 失效时间(秒)
	 */
	@Transient
	private Integer expiresIn;
}