/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcShipping.java
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

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Omc shipping.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_omc_shipping")
public class OmcShipping extends BaseEntity {

	private static final long serialVersionUID = 7337074530378267740L;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 收货姓名
	 */
	@Column(name = "receiver_name")
	private String receiverName;

	/**
	 * 收货固定电话
	 */
	@Column(name = "receiver_phone_no")
	private String receiverPhoneNo;

	/**
	 * 收货移动电话
	 */
	@Column(name = "receiver_mobile_no")
	private String receiverMobileNo;

	/**
	 * 收货人省ID
	 */
	@Column(name = "province_id")
	private Long provinceId;

	/**
	 * 省份
	 */
	@Column(name = "province_name")
	private String provinceName;

	/**
	 * 收货人城市ID
	 */
	@Column(name = "city_id")
	private Long cityId;

	/**
	 * 收货人城市名称
	 */
	@Column(name = "city_name")
	private String cityName;

	/**
	 * 区/县
	 */
	@Column(name = "district_name")
	private String districtName;

	/**
	 * 区/县 编码
	 */
	@Column(name = "district_id")
	private Long districtId;

	/**
	 * 街道ID
	 */
	@Column(name = "street_id")
	private Long streetId;

	/**
	 * 接到名称
	 */
	@Column(name = "street_name")
	private String streetName;

	/**
	 * 详细地址
	 */
	@Column(name = "detail_address")
	private String detailAddress;

	/**
	 * 邮编
	 */
	@Column(name = "receiver_zip_code")
	private String receiverZipCode;

	/**
	 * 邮编
	 */
	@Column(name = "default_address")
	private Integer defaultAddress;
}