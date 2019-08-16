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
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
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
	 * 注册STOMP协议节点并映射url
	 * @param registry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
				// 注册一个 /websocket 的 websocket 节点
				.addEndpoint("/websocket").addInterceptors()
				// 添加 websocket握手拦截器
				.addInterceptors(myHandshakeInterceptor())
				// 添加 websocket握手处理器
				.setHandshakeHandler(myDefaultHandshakeHandler())
				// 设置允许可跨域的域名(一定程度预防CSRF攻击)
				.setAllowedOrigins("*")
				// 指定使用SockJS协议
				.withSockJS();
	}

	/**
	 * WebSocket 握手拦截器 可做一些用户认证拦截处理
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
				ServletServerHttpRequest req = (ServletServerHttpRequest) request;

				// 根据token认证用户，不通过返回拒绝握手
				String token = req.getServletRequest().getParameter("token");
				String projectId = req.getServletRequest().getParameter("projectId");
				Principal user = authenticate(projectId, token);
				if (user == null) {
					return false;
				}

				// 保存会话信息
				attributes.put("user", user);
				attributes.put("remoteUrl", request.getRemoteAddress());
				attributes.put("projectId", projectId);
				return true;
			}

			@Override
			public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
					WebSocketHandler wsHandler, Exception exception) {
			}
		};
	}

	/**
	 * WebSocket 握手处理器
	 */
	private DefaultHandshakeHandler myDefaultHandshakeHandler() {
		return new DefaultHandshakeHandler() {
			@Override
			protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
					Map<String, Object> attributes) {
				// 设置认证通过的用户到当前会话中
				return (Principal) attributes.get("user");
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
	 * @param token
	 */
	private Principal authenticate(String projectId, String token) {
		String publicKey = projectKeyService.selectPublicKeyByProjectId(projectId);
		Map<String, Claim> payLoad = JwtRsaUtils.verify(publicKey, token);
		if (payLoad == null) {
			return null;
		}
		// 用户信息需继承 Principal 并实现 getName() 方法，返回全局唯一值
		String userName = payLoad.get("userName").asString();
		log.info("userName connect success, projectId:{}, userName:{}", projectId, userName);
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

}
