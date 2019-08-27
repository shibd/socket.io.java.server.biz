package com.dfocus.pmsg.config;

import com.auth0.jwt.interfaces.Claim;
import com.dfocus.pmsg.common.utils.JwtRsaUtils;
import com.dfocus.pmsg.service.atom.ISecretService;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: baozi
 * @date: 2019/8/2 17:08
 * @description: 开启使用STOMP协议来传输基于代理（message broker）的消息 启用后控制器支持@MessgeMapping注解.
 * 继承AbstractWebSocketMessageBrokerConfigurer 的配置类实现 WebSocket 配置
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private ISecretService projectKeyService;

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
	}

	/**
	 * STOMP入口拦截, 解析token如果成果就放入user
	 * @param registration
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					Map<String, LinkedList> headers = (Map) message.getHeaders()
							.get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
					String token = headers.get("token").get(0).toString();
					String projectId = headers.get("projectId").get(0).toString();
					Principal user = authenticate(token, projectId);
					if (user != null) {
						accessor.setUser(user);
					}
				}
				return message;
			}
		});
	}

	/**
	 * STOMP响应拦截, 在CONNECT_ACK时发送认证结果
	 * @param registration
	 */
	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {

		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				SimpMessageType simpMessageType = (SimpMessageType) message.getHeaders().get("simpMessageType");
				if (simpMessageType == SimpMessageType.CONNECT_ACK) {
					MessageHeaders headers = message.getHeaders();
					GenericMessage simpConnectMessage = (GenericMessage) headers.get("simpConnectMessage");

					Map<String, Object> nativeHeaders = (Map<String, Object>) simpConnectMessage.getHeaders()
							.get("nativeHeaders");

					String token = ((List<String>) nativeHeaders.get("token")).get(0);
					String projectId = ((List<String>) nativeHeaders.get("projectId")).get(0);

					Principal user = authenticate(token, projectId);
					if (user == null) {
						StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.ERROR);
						headerAccessor.setMessage("auth_fail");
						headerAccessor.setSessionId(headers.get("simpSessionId").toString());
						return MessageBuilder.createMessage("AUTH_FAIL".getBytes(), headerAccessor.getMessageHeaders());
					}

				}
				return message;
			}
		});
	}

	/**
	 * 注册STOMP协议节点并映射url
	 * @param registry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
				// 注册一个 /dfocus 的 websocket 节点
				.addEndpoint("/dfocus").addInterceptors()
				// 添加 websocket握手拦截器
				.addInterceptors(myHandshakeInterceptor())
				// 设置允许可跨域的域名(一定程度预防CSRF攻击)
				.setAllowedOrigins("*")
				// 指定使用SockJS协议
				.withSockJS();
	}

	/**
	 * WebSocket 握手拦截器 可做一些用户认证,保存会话信息等
	 */
	private HandshakeInterceptor myHandshakeInterceptor() {
		return new HandshakeInterceptor() {
			/**
			 * websocket握手连接
			 * @return 返回是否同意握手
			 */
			@Override
			public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
					WebSocketHandler wsHandler, Map<String, Object> attributes) {
				// 保存会话信息
				attributes.put("remoteUrl", request.getRemoteAddress());
				return true;
			}

			@Override
			public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
					WebSocketHandler wsHandler, Exception exception) {
			}
		};
	}

	@Bean
	public ThreadPoolTaskScheduler webSocketHeartbeatTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}

	/**
	 * 定义一些消息连接规范（也可不设置）
	 * @param registry
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 设置客户端接收消息地址的前缀（可不设置）
		registry.enableSimpleBroker(
				// 广播消息前缀
				"/topic",
				// 点对点消息前缀
				"/queue")
				// 设置心跳调度器
				.setTaskScheduler(webSocketHeartbeatTaskScheduler())
				// 心跳频率 {服务端发送频率, 客户端发送频率}ms
				.setHeartbeatValue(new long[] { 10000, 10000 });
		// 设置客户端接收点对点消息地址的前缀，默认为 /user
		registry.setUserDestinationPrefix("/user");
		// 设置客户端向服务器发送消息的地址前缀（可不设置）
		registry.setApplicationDestinationPrefixes("/app");
	}

	/**
	 * 根据jwt认证授权
	 */
	private Principal authenticate(String token, String projectId) {
		String userName;
		try {
			String publicKey = projectKeyService.selectPublicKeyByProjectId(projectId);
			Map<String, Claim> payLoad = JwtRsaUtils.verify(publicKey, token);
			// 用户信息需继承 Principal 并实现 getName() 方法，返回全局唯一值
			userName = payLoad.get("userName").asString();
			log.info("userName connect success, projectId:{}, userName:{}", projectId, userName);
		}
		catch (Exception e) {
			log.warn("auth fail: " + e.getMessage());
			return null;
		}
		return new User(userName);
	}

	/**
	 * @auther: baozi
	 * @date: 2019/8/5 10:44
	 * @description:
	 */
	@ToString
	static class User implements Principal {

		private final String name;

		public User(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

	}

	/**
	 * @auther: baozi
	 * @date: 2019/8/5 10:44
	 * @description: 认证失败异常
	 */
	private class AuthFailException extends RuntimeException {

		public AuthFailException(String message) {
			super(message);
		}

	}

}
