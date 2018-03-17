package com.paascloud.provider.model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The class Omc api constant.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OmcApiConstant {

	/**
	 * The interface Cart.
	 */
	public interface Cart {
		/**
		 * 即购物车选中状态
		 */
		int CHECKED = 1;

		/**
		 * 购物车中未选中状态
		 */
		int UN_CHECKED = 0;
		/**
		 * The constant LIMIT_NUM_FAIL.
		 */
		String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
		/**
		 * The constant LIMIT_NUM_SUCCESS.
		 */
		String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
	}

	/**
	 * The interface Shipping.
	 */
	public interface Shipping {
		/**
		 * The constant DEFAULT.
		 */
		int DEFAULT = 1;
		/**
		 * The constant NOT_DEFAULT.
		 */
		int NOT_DEFAULT = 0;
	}

	/**
	 * The enum Order status enum.
	 */
	public enum OrderStatusEnum {
		/**
		 * Canceled order status enum.
		 */
		CANCELED(0, "已取消"),
		/**
		 * No pay order status enum.
		 */
		NO_PAY(10, "未支付"),
		/**
		 * Paid order status enum.
		 */
		PAID(20, "已付款"),
		/**
		 * Shipped order status enum.
		 */
		SHIPPED(40, "已发货"),
		/**
		 * Order success order status enum.
		 */
		ORDER_SUCCESS(50, "订单完成"),
		/**
		 * Order close order status enum.
		 */
		ORDER_CLOSE(60, "订单关闭");


		OrderStatusEnum(int code, String value) {
			this.code = code;
			this.value = value;
		}

		private String value;
		private int code;

		/**
		 * Gets value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets code.
		 *
		 * @return the code
		 */
		public int getCode() {
			return code;
		}

		/**
		 * Code of order status enum.
		 *
		 * @param code the code
		 *
		 * @return the order status enum
		 */
		public static OrderStatusEnum codeOf(int code) {
			OrderStatusEnum result = null;
			for (OrderStatusEnum paymentTypeEnum : values()) {
				if (paymentTypeEnum.getCode() == code) {
					result = paymentTypeEnum;
					break;
				}
			}
			return result;
		}
	}
}
