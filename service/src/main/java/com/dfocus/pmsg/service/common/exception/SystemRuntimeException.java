package com.dfocus.pmsg.service.common.exception;

/**
 * @Author:
 * @Date: 2019/6/27 10:14
 * @Description: 系统异常（相对业务异常BizException而言），用于包装一些不需要业务关心的检查异常向外抛出
 */
public class SystemRuntimeException extends RuntimeException {

	public SystemRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemRuntimeException(Throwable cause) {
		super(cause);
	}

}
