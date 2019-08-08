package com.dfocus.pmsg.service.common.exception;

import com.dfocus.pmsg.service.common.constant.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * @Author:
 * @Date: 2019/6/27 10:14
 * @Description: 业务异常
 */
@Data
@ToString
public class BizException extends RuntimeException {

	private int status;

	public BizException(String msg) {
		super(msg);
	}

	public BizException(int status, String msg) {
		this(msg);
		this.status = status;
	}

	public BizException(ResultCode error) {
		this(error.getMsg());
		this.status = error.getCode();
	}

}
