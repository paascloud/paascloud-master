package com.paascloud.provider.model.vo;

import com.paascloud.base.dto.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * The class User token vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserTokenVo extends BaseVo {
	private static final long serialVersionUID = -7181619498770168776L;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 姓名
	 */
	private String userName;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 登陆人Ip地址
	 */
	private String loginIp;

	/**
	 * 登录地址
	 */
	private String loginLocation;

	/**
	 * 登录地址
	 */
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
	private String accessToken;

	/**
	 * 刷新token
	 */
	private String refreshToken;

	/**
	 * 访问token的生效时间(秒)
	 */
	private Integer accessTokenValidity;

	/**
	 * 刷新token的生效时间(秒)
	 */
	private Integer refreshTokenValidity;

	/**
	 * 0 在线 10已刷新 20 离线
	 */
	private Integer status;

	/**
	 * 组织名称
	 */
	private String groupName;

	/**
	 * 失效时间(秒)
	 */
	private Integer expiresIn;
}
