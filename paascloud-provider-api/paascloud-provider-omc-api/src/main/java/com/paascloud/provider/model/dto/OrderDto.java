package com.paascloud.provider.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The class Order dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class OrderDto implements Serializable {

	private static final long serialVersionUID = 529837303004214782L;
	private Long id;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 用户id
	 */
	private Long userId;

	private Long shippingId;

	/**
	 * 实际付款金额,单位是元,保留两位小数
	 */
	private BigDecimal payment;

	/**
	 * 支付类型,1-在线支付
	 */
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
	private Date paymentTime;

	/**
	 * 发货时间
	 */
	private Date sendTime;

	/**
	 * 交易完成时间
	 */
	private Date endTime;

	/**
	 * 交易关闭时间
	 */
	private Date closeTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建人ID
	 */
	private Long creatorId;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 最近操作人
	 */
	private String lastOperator;

	/**
	 * 最后操作人ID
	 */
	private Long lastOperatorId;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
