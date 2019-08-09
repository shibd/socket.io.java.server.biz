package com.dfocus.pmsg.service.atom;

import com.dfocus.pmsg.service.dto.WsSessionDto;

import java.util.List;

/**
 * @author: baozi
 * @date: 2019/8/6 17:18
 * @description:
 */
public interface ISessionService {

	/**
	 * 创建session
	 * @param wsSessionDto
	 */
	void createSession(WsSessionDto wsSessionDto);

	/**
	 * 阐述session
	 * @param sessionId
	 */
	void deleteSession(String sessionId);

	/**
	 * 查找session
	 * @param sessionId
	 * @return
	 */
	WsSessionDto getSessionById(String sessionId);

	/**
	 * 获取session列表
	 * @return
	 */
	List<WsSessionDto> getSessions();

}
