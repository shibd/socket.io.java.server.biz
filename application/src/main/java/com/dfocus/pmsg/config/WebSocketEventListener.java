package com.dfocus.pmsg.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
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

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		event.getMessage().getHeaders().forEach((key, value) -> System.out.println(key + ":" + value));
		System.out.println(event.getMessage().getHeaders().keySet());
		// StompHeaderAccessor headerAccessor =
		// StompHeaderAccessor.wrap(event.getMessage());
		// System.out.println(headerAccessor.getSessionAttributes().keySet());
		logger.info("Received a new web socket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		logger.info("User Disconnected : ");

		Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
		sessionAttributes.forEach((key, value) -> System.out.println(key + ":" + String.valueOf(value)));
		System.out.println(sessionAttributes.keySet());

		// String username = (String) headerAccessor.getSessionAttributes().get("user");
		// if (username != null) {
		// logger.info("User Disconnected : " + username);
		//
		// // ChatMessage chatMessage = new ChatMessage();
		// // chatMessage.setType(ChatMessage.MessageType.LEAVE);
		// // chatMessage.setSender(username);
		// //
		// // messagingTemplate.convertAndSend("/topic/public", chatMessage);
		// }
	}

}
