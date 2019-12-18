package com.dfocus.pmsg.support;

import com.dfocus.pmsg.facade.web.rsp.Response;
import com.dfocus.pmsg.service.common.constant.ResultCode;
import com.dfocus.pmsg.service.common.exception.BizException;
import com.dfocus.pmsg.service.common.exception.SystemRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 统一异常处理 */
@Slf4j
@RestControllerAdvice
public class HandleExceptionFriendly {

	@ExceptionHandler(BizException.class)
	public Response<Void> bizException(BizException ex) {
		log.error("接口发生业务异常", ex);
		return Response.error(ex.getStatus(), ex.getMessage());
	}

	@ExceptionHandler(SystemRuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response<Void> internalException(SystemRuntimeException ex) {
		log.error("系统内部出现异常", ex);
		return Response.error(ResultCode.SYS_ERROR.getCode(), ResultCode.SYS_ERROR.getMsg());
	}

	@ExceptionHandler(BindException.class)
	public Response<Void> bindException(BindException ex) {
		log.info("校验参数出现异常", ex);
		StringBuilder errors = new StringBuilder();
		ex.getAllErrors().forEach(e -> {
			if (e instanceof FieldError) {
				FieldError fe = (FieldError) e;
				errors.append(fe.getField()).append(":").append(fe.getDefaultMessage()).append(";;");
			}
		});
		return Response.error(ResultCode.ERROR_INPUT_PARAMS.getCode(), errors.toString());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Response<Void> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.info("校验参数出现异常", ex);
		StringBuilder errors = new StringBuilder();
		ex.getBindingResult().getFieldErrors().forEach(e -> {
			errors.append(e.getField()).append(":").append(e.getDefaultMessage()).append(";;");
		});
		return Response.error(ResultCode.ERROR_INPUT_PARAMS.getCode(), errors.toString());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response<Void> otherwiseException(Throwable ex) {
		log.error("系统出现未知错误", ex);
		return Response.error(ResultCode.UNKNOWN_ERROR.getCode(), ResultCode.UNKNOWN_ERROR.getMsg());
	}

}
