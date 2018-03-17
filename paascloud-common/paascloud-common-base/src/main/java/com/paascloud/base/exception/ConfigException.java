package com.paascloud.base.exception;

/**
 * The class Config exception.
 *
 * @author paascloud.net@gmail.com
 */
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = 6480772904575978373L;

	/**
	 * Instantiates a new Config exception.
	 *
	 * @param message the message
	 */
	public ConfigException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new Config exception.
	 */
	public ConfigException() {

	}
}
