package com.paascloud.security.core.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片验证码配置项
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageCodeProperties extends SmsCodeProperties {

	/**
	 * Instantiates a new Image code properties.
	 */
	ImageCodeProperties() {
		super.setLength(4);
	}

}
