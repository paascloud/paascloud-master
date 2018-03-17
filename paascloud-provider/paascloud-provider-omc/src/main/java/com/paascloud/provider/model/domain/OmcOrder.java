package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The class Omc order.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_omc_order")
public class OmcOrder extends BaseEntity {

	private static final long serialVersionUID = -8434937678211570532L;
	/**
	 * 订单号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "shipping_id")
	private Long shippingId;

	/**
	 * 实际付款金额,单位是元,保留两位小数
	 */
	private BigDecimal payment;

	/**
	 * 支付类型,1-在线支付
	 */
	@Column(name = "payment_type")
	private Integer paymentType;

	/**
	 * 运费,单位是元
	 */
	private Integer postage;

	/**
	 * 订单状态:0-已取消-10-未付款, 20-已付款, 40-已发货, 50-交易成功, 60-交易关闭
	 */
	private Integer status;

	/**
	 * 支付时间
	 */
	@Column(name = "payment_time")
	private Date paymentTime;

	/**
	 * 发货时间
	 */
	@Column(name = "send_time")
	private Date sendTime;

	/**
	 * 交易完成时间
	 */
	@Column(name = "end_time")
	private Date endTime;

	/**
	 * 交易关闭时间
	 */
	@Column(name = "close_time")
	private Date closeTime;
}