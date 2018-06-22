
/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：QQUserInfo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.core.social.qq.api;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Qq user info.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class QQUserInfo implements Serializable {

	private static final long serialVersionUID = -7584208099330390359L;
	/**
	 * 返回码
	 */
	private String ret;
	/**
	 * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
	 */
	private String msg;

	private String openId;
	/**
	 * 不知道什么东西，文档上没写，但是实际api返回里有。
	 */
	@JsonProperty("is_lost")
	private String isLost;
	/**
	 * 省(直辖市)
	 */
	private String province;
	/**
	 * 市(直辖市区)
	 */
	private String city;
	/**
	 * 出生年月
	 */
	private String year;
	/**
	 * 用户在QQ空间的昵称。
	 */
	private String nickname;
	/**
	 * 大小为30×30像素的QQ空间头像URL。
	 */
	@JsonProperty("figureurl")
	private String figureUrl;
	/**
	 * 大小为50×50像素的QQ空间头像URL。
	 */
	@JsonProperty("figureurl_1")
	private String figureUrl1;
	/**
	 * 大小为100×100像素的QQ空间头像URL。
	 */
	@JsonProperty("figureurl_2")
	private String figureUrl2;
	/**
	 * 大小为40×40像素的QQ头像URL。
	 */
	@JsonProperty("figureurl_qq_1")
	private String figureUrlQq1;
	/**
	 * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
	 */
	@JsonProperty("figureurl_qq_2")
	private String figureUrlQq2;
	/**
	 * 性别。 如果获取不到则默认返回”男”
	 */
	private String gender;
	/**
	 * 标识用户是否为黄钻用户（0：不是；1：是）。
	 */
	@JsonProperty("is_yellow_vip")
	private String isYellowVip;
	/**
	 * 标识用户是否为黄钻用户（0：不是；1：是）
	 */
	private String vip;
	/**
	 * 黄钻等级
	 */
	@JsonProperty("yellow_vip_level")
	private String yellowVipLevel;
	/**
	 * 黄钻等级
	 */
	private String level;
	/**
	 * 标识是否为年费黄钻用户（0：不是； 1：是）
	 */
	@JsonProperty("is_yellow_year_vip")
	private String isYellowYearVip;
}
