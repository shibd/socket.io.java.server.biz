package com.dfocus.pmsg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author baozi
 * @date 2018/6/11
 */
@Slf4j
public class UserInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
			if (raw instanceof Map) {
				Object name = ((Map) raw).get("userId");
				if (name instanceof LinkedList) {
					// 设置当前访问器的认证用户
					User user = new User(((LinkedList) name).get(0).toString());
					accessor.setUser(user);
					log.info("register websocket channel by userId<{}>", user.getName());
				}
			}
		}
		return message;
	}

}

class User implements Principal {

	private final String name;

	public User(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
