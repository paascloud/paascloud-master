package com.paascloud.base.exception;

/**
 * The class Reference model null exception.
 *
 * @author paascloud.net@gmail.com
 */
public class ReferenceModelNullException extends RuntimeException {
	private static final long serialVersionUID = -318154770875589045L;

	/**
	 * Instantiates a new Reference model null exception.
	 *
	 * @param message the message
	 */
	public ReferenceModelNullException(String message) {
		super(message);
	}
}
