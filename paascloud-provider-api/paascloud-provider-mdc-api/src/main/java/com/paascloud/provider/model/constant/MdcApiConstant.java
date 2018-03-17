package com.paascloud.provider.model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The class Mdc api constant.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MdcApiConstant {

	/**
	 * The enum Product status enum.
	 *
	 * @author paascloud.net@gmail.com
	 */
	public enum ProductStatusEnum {
		/**
		 * On sale product status enum.
		 */
		ON_SALE(1, "在线"),
		/**
		 * Off sale product status enum.
		 */
		OFF_SALE(2, "下架"),
		/**
		 * Delete product status enum.
		 */
		DELETE(3, "删除");
		private String value;
		private int code;

		ProductStatusEnum(int code, String value) {
			this.code = code;
			this.value = value;
		}

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
	}
}
