package com.dfocus.pmsg.service.common.constant;

import lombok.Getter;

@Getter
public enum ResultCode {

	SUCCESS(0, "请求成功"), UNAVAILABLE_SERVICE(19997, "系统繁忙请稍后再试"), UNKNOWN_ERROR(19998, "未知异常"), ERROR_INPUT_PARAMS(20006,
			"输入参数错误"), SYS_ERROR(20007, "系统异常"),;

	private int code;

	private String msg;

	ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
