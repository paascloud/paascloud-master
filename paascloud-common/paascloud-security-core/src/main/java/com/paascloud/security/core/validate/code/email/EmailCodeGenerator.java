package com.paascloud.security.core.validate.code.email;

import com.paascloud.security.core.properties.SecurityProperties;
import com.paascloud.security.core.validate.code.ValidateCode;
import com.paascloud.security.core.validate.code.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 短信验证码生成器
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component("emailValidateCodeGenerator")
public class EmailCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	/**
	 * Generate validate code.
	 *
	 * @param request the request
	 *
	 * @return the validate code
	 */
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
		String[] emails = parameterMap.get("email");
		log.info(Arrays.toString(emails));
		String code = Arrays.toString(emails);
		return new ValidateCode(code, securityProperties.getCode().getEmail().getExpireIn());
	}
}
