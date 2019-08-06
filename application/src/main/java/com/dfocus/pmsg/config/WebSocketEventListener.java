package com.dfocus.pmsg.config;

import com.dfocus.pmsg.service.atom.ISessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

/**
 * @author baozi
 * @date: 2019/8/6 10:53
 * @description:
 */
@Component
public class WebSocketEventListener {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	ISessionService sessionService;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		logger.info("Received a new web socket connection");
		MessageHeaders headers = event.getMessage().getHeaders();
		GenericMessage genericMessage = (GenericMessage) headers.get("simpConnectMessage");
		Map<String, Object> simpSessionAttributes = (Map<String, Object>) genericMessage.getHeaders()
				.get("simpSessionAttributes");

		String sessionId = headers.get("simpSessionId").toString();
		String remoteUrl = simpSessionAttributes.get("remoteUrl").toString();
		sessionService.createSession(sessionId, remoteUrl);
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
		String remoteUrl = sessionAttributes.get("remoteUrl").toString();
		String user = ((User) sessionAttributes.get("user")).getName();
		logger.info("User Disconnected,{}:{}", user, remoteUrl);
		sessionService.deleteSession(headerAccessor.getSessionId());
	}

}
