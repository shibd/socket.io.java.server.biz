package com.dfocus.pmsg.service.atom.impl;

import com.dfocus.pmsg.service.atom.ISessionService;
import com.dfocus.pmsg.service.dto.WsSessionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

	private Map<String, WsSessionDto> sessionCache = new ConcurrentHashMap<>();

	@Override
	public void createSession(WsSessionDto wsSessionDto) {
		sessionCache.put(wsSessionDto.getSessionId(), wsSessionDto);
	}

	@Override
	public void deleteSession(String sessionId) {
		sessionCache.remove(sessionId);
	}

	@Override
	public WsSessionDto getSessionById(String sessionId) {
		return sessionCache.get(sessionId);
	}

	@Override
	public List<WsSessionDto> getSessions() {
		return new ArrayList<>(sessionCache.values());
	}

}
