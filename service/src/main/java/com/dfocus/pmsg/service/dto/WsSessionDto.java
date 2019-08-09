package com.dfocus.pmsg.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: baozi
 * @date: 2019/8/9 10:05
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsSessionDto {

	/**
	 * 会话Id
	 */
	private String sessionId;

	/**
	 * 客户端地址
	 */
	private String remoteUrl;

	/**
	 * 项目Id
	 */
	private String projectId;

}
