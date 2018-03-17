package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * The class Uac group.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_uac_group")
@Alias(value = "uacGroup")
public class UacGroup extends BaseEntity {
	private static final long serialVersionUID = 1752542793067966068L;

	/**
	 * 组织编码
	 */
	@Column(name = "group_code")
	private String groupCode;

	/**
	 * 组织名称
	 */
	@Column(name = "group_name")
	private String groupName;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 组织类型 1：仓库 2：基地
	 */
	private String type;

	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 是否叶子节点,1不是0是
	 */
	private Integer leaf;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 组织地址
	 */
	@Column(name = "group_address")
	private String groupAddress;

	/**
	 * 省名称
	 */
	@Column(name = "province_name")
	private String provinceName;

	/**
	 * 省编码
	 */
	@Column(name = "province_id")
	private Long provinceId;

	/**
	 * 城市名称
	 */
	@Column(name = "city_name")
	private String cityName;

	/**
	 * 城市编码
	 */
	@Column(name = "city_id")
	private Long cityId;

	/**
	 * 区名称
	 */
	@Column(name = "area_name")
	private String areaName;

	/**
	 * 区编码
	 */
	@Column(name = "area_id")
	private Long areaId;

	/**
	 * 街道编码
	 */
	@Column(name = "street_id")
	private Long streetId;

	/**
	 * 街道名称
	 */
	@Column(name = "street_name")
	private String streetName;

	/**
	 * 详细地址
	 */
	@Column(name = "detail_address")
	private String detailAddress;

	/**
	 * 联系人手机号
	 */
	@Column(name = "contact_phone")
	private String contactPhone;

	/**
	 * 描述
	 */
	private String remark;

	/**
	 * 预留排序字段
	 */
	private Integer number;

	/**
	 * 上级组织编码
	 */
	@Transient
	private String parentGroupCode;

	/**
	 * 上级组织名称
	 */
	@Transient
	private String parentGroupName;

	/**
	 * 四级地址数组
	 */
	@Transient
	private List<Long> addressList;

}