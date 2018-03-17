package com.paascloud.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;


/**
 * The class Validate code exception.
 *
 * @author paascloud.net@gmail.com
 */
public class ValidateCodeException extends AuthenticationException {


	private static final long serialVersionUID = -7285211528095468156L;

	/**
	 * Instantiates a new Validate code exception.
	 *
	 * @param msg the msg
	 */
	public ValidateCodeException(String msg) {
		super(msg);
	}

}
