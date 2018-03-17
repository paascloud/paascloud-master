package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Opc sms setting.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_opc_sms_setting")
@Alias(value = "opcSmsSetting")
public class OpcSmsSetting extends BaseEntity {

	private static final long serialVersionUID = -1811960778993500439L;
	/**
	 * 可再次发送时间（毫秒）
	 */
	@Column(name = "again_send_time")
	private Integer againSendTime;

	/**
	 * 失效时间（分钟）
	 */
	@Column(name = "invalid_time")
	private Integer invalidTime;

	/**
	 * 短信类型
	 */
	private String type;

	/**
	 * 类型描述
	 */
	@Column(name = "type_desc")
	private String typeDesc;

	/**
	 * 模板code
	 */
	@Column(name = "templet_code")
	private String templetCode;

	/**
	 * 模板内容
	 */
	@Column(name = "templet_content")
	private String templetContent;

	/**
	 * 一天中可发送的最大数量
	 */
	@Column(name = "send_max_num")
	private Integer sendMaxNum;

	/**
	 * 一个IP一天中可发送的最大数量
	 */
	@Column(name = "ip_send_max_num")
	private Integer ipSendMaxNum;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 删除标识(1-已删除；0-未删除)
	 */
	private Integer yn;
}