package com.dfocus.pmsg.service.atom;

import java.util.Map;

/**
 * @author: baozi
 * @date: 2019/8/6 17:18
 * @description:
 */
public interface ISessionService {

	/**
	 * @param sessionId
	 * @param remoteUrl
	 */
	void createSession(String sessionId, String remoteUrl);

	/**
	 * @param sessionId
	 */
	void deleteSession(String sessionId);

	/**
	 * @param sessionId
	 * @return
	 */
	String getRemoteUrlBySession(String sessionId);

	/**
	 * @return
	 */
	Map<String, String> getSessions();

}
