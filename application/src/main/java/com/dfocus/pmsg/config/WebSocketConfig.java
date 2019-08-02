package com.dfocus.pmsg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author baozi
 * @Date: 2019/7/23 17:28
 * @Description:
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker(
				// 广播消息前缀
				"/topic"
				// 点对点消息前缀
				, "/queue");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 允许连接的域,只能以http或https开头,这里设置origin校验可以一定程度预防CSRF攻击
		String[] allowsOrigins = { "*" };
		registry.addEndpoint("/guide-websocket").setAllowedOrigins(allowsOrigins).withSockJS();
	}

	/**
	 * 配置客户端入站通道拦截器
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new UserInterceptor());
	}

}