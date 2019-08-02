package com.dfocus.pmsg.facade.model;

import lombok.Data;

/**
 * @Auther: baozi
 * @Date: 2019/6/25 11:27
 * @Description:
 */
@Data
public class MintFaReq {

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickName;

}
