package com.paascloud.provider.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * The class Order product vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class OrderProductVo implements Serializable {
	private static final long serialVersionUID = 9200251296138955758L;
	private List<OrderItemVo> orderItemVoList;
	private BigDecimal productTotalPrice;
	private String imageHost;
}
