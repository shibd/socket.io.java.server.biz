package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author baozi Websocket配置类
 */
@Configuration
@EnableWebSocketMessageBroker // 使用此注解启动websocket,使用broker来处理消息
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	// 实现WebSocketMessageBrokerConfigurer中的此方法，配置消息代理（broker）
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 启用SimpleBroker，使得订阅到此"topic"前缀的客户端可以收到greeting消息.
		config.enableSimpleBroker("/topic");
	}

	@Override
	// 用来注册Endpoint，“/gs-guide-websocket”即为客户端尝试建立连接的后缀地址。
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/gs-guide-websocket").withSockJS();
	}

}