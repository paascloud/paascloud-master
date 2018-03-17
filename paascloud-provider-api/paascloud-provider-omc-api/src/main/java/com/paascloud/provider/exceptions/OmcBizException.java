package com.paascloud.provider.exceptions;

import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;


/**
 * The class Omc biz exception.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class OmcBizException extends BusinessException {

	private static final long serialVersionUID = -6552248511084911254L;

	/**
	 * Instantiates a new Uac rpc exception.
	 */
	public OmcBizException() {
	}

	/**
	 * Instantiates a new Uac rpc exception.
	 *
	 * @param code      the code
	 * @param msgFormat the msg format
	 * @param args      the args
	 */
	public OmcBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
		log.info("<== OmcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Uac rpc exception.
	 *
	 * @param code the code
	 * @param msg  the msg
	 */
	public OmcBizException(int code, String msg) {
		super(code, msg);
		log.info("<== OmcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Omc rpc exception.
	 *
	 * @param codeEnum the code enum
	 */
	public OmcBizException(ErrorCodeEnum codeEnum) {
		super(codeEnum.code(), codeEnum.msg());
		log.info("<== OmcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Omc rpc exception.
	 *
	 * @param codeEnum the code enum
	 * @param args     the args
	 */
	public OmcBizException(ErrorCodeEnum codeEnum, Object... args) {
		super(codeEnum, args);
		log.info("<== OmcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}
}
