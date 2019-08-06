package com.dfocus.pmsg.service.atom.impl;

import com.dfocus.pmsg.service.atom.ISessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: baozi
 * @date: 2019/8/6 17:18
 * @description:
 */
@Slf4j
@Service
public class ISessionServiceImpl implements ISessionService {

	private Map<String, String> sessionRemoteUrls = new ConcurrentHashMap<>();

	@Override
	public void createSession(String sessionId, String remoteUrl) {
		sessionRemoteUrls.put(sessionId, remoteUrl);
	}

	@Override
	public void deleteSession(String sessionId) {
		sessionRemoteUrls.remove(sessionId);
	}

	@Override
	public String getRemoteUrlBySession(String sessionId) {
		return sessionRemoteUrls.get(sessionId);
	}

	@Override
	public Map<String, String> getSessions() {
		return sessionRemoteUrls;
	}

}
