package com.paascloud.core.registry.exception;

/**
 * 注册中心异常.
 *
 * @author zhangliang
 */
public final class RegException extends RuntimeException {

	private static final long serialVersionUID = -6417179023552012152L;

	/**
	 * Instantiates a new Reg exception.
	 *
	 * @param errorMessage the error message
	 * @param args         the args
	 */
	public RegException(final String errorMessage, final Object... args) {
		super(String.format(errorMessage, args));
	}

	/**
	 * Instantiates a new Reg exception.
	 *
	 * @param cause the cause
	 */
	public RegException(final Exception cause) {
		super(cause);
	}
}
