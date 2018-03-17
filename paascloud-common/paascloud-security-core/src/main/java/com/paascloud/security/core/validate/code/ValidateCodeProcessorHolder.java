package com.paascloud.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 校验码处理器管理器
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class ValidateCodeProcessorHolder {

	private final Map<String, ValidateCodeProcessor> validateCodeProcessors;

	/**
	 * Instantiates a new Validate code processor holder.
	 *
	 * @param validateCodeProcessors the validate code processors
	 */
	@Autowired
	public ValidateCodeProcessorHolder(Map<String, ValidateCodeProcessor> validateCodeProcessors) {
		this.validateCodeProcessors = validateCodeProcessors;
	}

	/**
	 * Find validate code processor validate code processor.
	 *
	 * @param type the type
	 *
	 * @return validate code processor
	 */
	ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

	/**
	 * Find validate code processor validate code processor.
	 *
	 * @param type the type
	 *
	 * @return validate code processor
	 */
	ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}

}
