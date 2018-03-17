package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Ptc pay info.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "pc_ptc_pay_info")
@Data
public class PtcPayInfo extends BaseEntity {

	private static final long serialVersionUID = 7949091072343450552L;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 订单号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 支付平台:1-支付宝,2-微信
	 */
	@Column(name = "pay_platform")
	private Integer payPlatform;

	/**
	 * 支付宝支付流水号
	 */
	@Column(name = "platform_number")
	private String platformNumber;

	/**
	 * 支付宝支付状态
	 */
	@Column(name = "platform_status")
	private String platformStatus;

}