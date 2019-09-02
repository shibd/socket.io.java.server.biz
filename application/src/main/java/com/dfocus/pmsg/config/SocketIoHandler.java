package com.dfocus.pmsg.config;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: baozi
 * @date: 2019/9/2 10:09
 * @description:
 */
@Slf4j
@Component
public class SocketIoHandler {

	@Autowired
	SocketIOServer server;

	/**
	 * 添加连接监听事件，监听是否与客户端连接到服务器
	 */
	public void onConnect() {
		server.addConnectListener(client -> {
			// 判断是否有客户端连接
			if (client != null) {
				log.info(("连接成功, clientId=" + client.getSessionId().toString()));
			}
			else {
				log.info("并没有人连接上。。。");
			}
		});
	}

	/**
	 * 断开连接事件
	 */
	public void onDisconnect() {
		server.addDisconnectListener(client -> {
			log.info("断开连接, clientId=" + client.getSessionId().toString());
		});
	}

	/**
	 * 监听订阅事件
	 */
	public void onSubscribeEvent(String namespace) {
		SocketIONamespace namespaceServer = server.addNamespace(namespace);
		namespaceServer.addEventListener("subscribe", List.class, (client, topics, ackRequest) -> {
			log.info("{}客户端订阅消息：topics = {}", client.getSessionId(), topics);
			for (Object topic : topics) {
				client.joinRoom(topic.toString());
			}
			// check is ack requested by client, but it's not required check
			if (ackRequest.isAckRequested()) {
				// send ack response with data to client
				ackRequest.sendAckData("订阅成功topic", topics);
			}
		});
	}

}
