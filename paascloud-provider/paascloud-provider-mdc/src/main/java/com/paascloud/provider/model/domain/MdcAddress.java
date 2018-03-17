package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * The class Mdc address.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_mdc_address")
public class MdcAddress extends BaseEntity {
	private static final long serialVersionUID = -499010884211394846L;

	/**
	 * 地址名称
	 */
	private String name;

	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 城市编码
	 */
	@Column(name = "city_code")
	private String cityCode;

	/**
	 * 级别（省市区县）
	 */
	private Integer level;

	/**
	 * 区域编码
	 */
	@Column(name = "ad_code")
	private String adCode;

	/**
	 * 行政区边界坐标点
	 */
	private String polyline;

	/**
	 * 城市中心点
	 */
	private String center;
}