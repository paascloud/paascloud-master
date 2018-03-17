package com.paascloud.provider.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The class Order item vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class OrderItemVo implements Serializable {

	private static final long serialVersionUID = -8309014197153665106L;
	private String orderNo;

	private Long productId;

	private String productName;
	private String productImage;

	private BigDecimal currentUnitPrice;

	private Integer quantity;

	private BigDecimal totalPrice;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
}
