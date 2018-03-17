package com.paascloud.provider.model.vo;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * The class Kaptcha vo.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@Api
public class KaptchaVo {
	private String cookieCode;
	private String base64Code;
	private String kaptchaCode;
}
