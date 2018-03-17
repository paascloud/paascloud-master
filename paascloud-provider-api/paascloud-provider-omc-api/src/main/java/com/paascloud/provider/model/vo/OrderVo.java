package com.paascloud.provider.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The class Order vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class OrderVo implements Serializable {

	private static final long serialVersionUID = 5829292780030349525L;
	private String orderNo;

	private BigDecimal payment;

	private Integer paymentType;

	private String paymentTypeDesc;
	private Integer postage;

	private Integer status;


	private String statusDesc;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date paymentTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date sendTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date closeTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private String creator;

	/**
	 * 订单的明细
	 */
	private List<OrderItemVo> orderItemVoList;

	private String imageHost;
	private Long shippingId;
	private String receiverName;

	private ShippingVo shippingVo;
}
