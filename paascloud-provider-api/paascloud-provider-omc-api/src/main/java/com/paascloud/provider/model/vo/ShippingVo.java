package com.paascloud.provider.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * The class Shipping vo.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class ShippingVo implements Serializable {
	private static final long serialVersionUID = 1650395734521093800L;
	private String receiverName;

	private String receiverPhone;

	private String receiverMobile;

	private String receiverProvince;

	private String receiverCity;

	private String receiverDistrict;

	private String receiverAddress;

	private String receiverZip;
}
