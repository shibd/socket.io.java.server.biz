package com.dfocus.pmsg.config;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.dfocus.pmsg.common.utils.JwtRsaUtils;
import com.dfocus.pmsg.config.properties.MyProperties;
import com.dfocus.pmsg.service.atom.ISecretService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * NettySocketConfig
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/1/19
 */
@Slf4j
@Configuration
public class NettySocketConfig {

	@Resource
	private MyProperties myProperties;

	@Autowired
	private ISecretService secretService;

	@Bean
	public SocketIOServer socketIOServer() {
		/*
		 * 创建Socket，并设置监听端口
		 */
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		// 设置主机名，默认是0.0.0.0
		// config.setHostname("localhost");
		// 设置监听端口
		config.setPort(myProperties.getSocketPort());
		// 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
		config.setUpgradeTimeout(10000);
		// Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
		config.setPingInterval(myProperties.getPingInterval());
		// Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
		config.setPingTimeout(myProperties.getPingTimeout());
		// 握手协议参数使用JWT的Token认证方案
		config.setAuthorizationListener(data -> {
			// jwt认证
			String projectId = data.getSingleUrlParam("projectId");
			String token = data.getSingleUrlParam("token");
			return authenticate(token, projectId);

		});

		final SocketIOServer server = new SocketIOServer(config);

		/**
		 * 添加连接监听事件，监听是否与客户端连接到服务器
		 */
		server.addConnectListener(client -> {
			// 判断是否有客户端连接
			if (client != null) {
				System.out.println(("连接成功。clientId=" + client.getSessionId().toString()));
			}
			else {
				System.out.println("并没有人连接上。。。");
			}
		});

		/**
		 * 断开连接事件
		 */
		server.addDisconnectListener(client -> {
			log.info("断开连接, clientId=" + client.getSessionId().toString());
		});


		/**
		 * 监听订阅事件
		 */
		SocketIONamespace namespace = server.addNamespace("/fm");
		namespace.addEventListener("subscribe", List.class, (client, topics, ackRequest) -> {
			System.out.println("接收到客户端订阅消息：code = " + topics.size());
			for (Object topic : topics) {
				client.joinRoom(topic.toString());
			}
			// check is ack requested by client, but it's not required check
			if (ackRequest.isAckRequested()) {
				// send ack response with data to client
				ackRequest.sendAckData("订阅成功topic", topics);
			}
		});

		return server;
	}

	/**
	 * 根据jwt认证授权
	 */
	private boolean authenticate(String token, String projectId) {
		try {
			String publicKey = secretService.selectPublicKeyByProjectId(projectId);
			JwtRsaUtils.verify(publicKey, token);
		}
		catch (Exception e) {
			log.warn("auth fail: " + e.getMessage());
			return false;
		}
		return true;
	}
}
