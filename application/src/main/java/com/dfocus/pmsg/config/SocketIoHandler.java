package com.dfocus.pmsg.config;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.dfocus.pmsg.common.utils.JwtRsaUtils;
import com.dfocus.pmsg.service.atom.ISecretService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: baozi
 * @date: 2019/9/2 10:09
 * @description:
 */
@Slf4j
@Component
public class SocketIoHandler {

	@Autowired
	private SocketIOServer server;

	@Autowired
	private ISecretService secretService;

	/**
	 * todo 认证缓存,注意线程安全,性能,内存溢出等
	 */
	private Set<String> authClients = ConcurrentHashMap.newKeySet();

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
				log.info("并没有人连接上...");
			}
		});
	}

	/**
	 * 断开连接事件
	 */
	public void onDisconnect() {
		server.addDisconnectListener(client -> {
			log.info("断开连接, clientId=" + client.getSessionId().toString());
			authClients.remove(client.getSessionId().toString());
		});
	}

	/**
	 * 监听所有命名空间的认证事件
	 * @param namespace
	 */
	public void onAuthEvent(String namespace) {
		SocketIONamespace namespaceServer = server.addNamespace(namespace);
		namespaceServer.addEventListener("auth", Map.class, (client, reqData, ackRequest) -> {
			String projectId = String.valueOf(reqData.get("projectId"));
			String token = String.valueOf(reqData.get("token"));
			log.info("{}客户端认证事件:projectId={},token={}", client.getSessionId(), projectId, token);
			// check is ack requested by client, but it's not required check
			if (ackRequest.isAckRequested()) {
				// send ack response with data to client
				if (authenticate(token, projectId)) {
					// 保存认证的客户端
					authClients.add(client.getSessionId().toString());
					ackRequest.sendAckData("auth_success");
				}
				else {
					ackRequest.sendAckData("auth_fail");
				}
			}
		});
	}

	/**
	 * 监听所有命名空间订阅事件
	 */
	public void onSubscribeEvent(String namespace) {
		SocketIONamespace namespaceServer = server.addNamespace(namespace);
		namespaceServer.addEventListener("subscribe", List.class, (client, topics, ackRequest) -> {
			// 非认证的客户端不能加入topic
			if (authClients.contains(client.getSessionId().toString())) {
				log.info("{}客户端加入主题:topics={}", client.getSessionId(), topics);
				for (Object topic : topics) {
					client.joinRoom(topic.toString());
				}
			}
			// check is ack requested by client, but it's not required check
			if (ackRequest.isAckRequested()) {
				// send ack response with data to client
				if (authClients.contains(client.getSessionId().toString())) {
					ackRequest.sendAckData("sub_success");
				}
				else {
					ackRequest.sendAckData("sub_fail, please authenticate first");
				}
			}
		});
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
