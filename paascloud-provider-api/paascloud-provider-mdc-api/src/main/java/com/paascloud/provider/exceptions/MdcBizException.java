package com.paascloud.provider.exceptions;

import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;


/**
 * The class Mdc biz exception.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class MdcBizException extends BusinessException {

	private static final long serialVersionUID = -6552248511084911254L;

	/**
	 * Instantiates a new Uac rpc exception.
	 */
	public MdcBizException() {
	}

	/**
	 * Instantiates a new Uac rpc exception.
	 *
	 * @param code      the code
	 * @param msgFormat the msg format
	 * @param args      the args
	 */
	public MdcBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
		log.info("<== MdcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Uac rpc exception.
	 *
	 * @param code the code
	 * @param msg  the msg
	 */
	public MdcBizException(int code, String msg) {
		super(code, msg);
		log.info("<== MdcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Mdc rpc exception.
	 *
	 * @param codeEnum the code enum
	 */
	public MdcBizException(ErrorCodeEnum codeEnum) {
		super(codeEnum.code(), codeEnum.msg());
		log.info("<== MdcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Mdc rpc exception.
	 *
	 * @param codeEnum the code enum
	 * @param args     the args
	 */
	public MdcBizException(ErrorCodeEnum codeEnum, Object... args) {
		super(codeEnum, args);
		log.info("<== MdcRpcException, code:{}, message:{}", this.code, super.getMessage());
	}
}
