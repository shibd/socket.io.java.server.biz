package com.dfocus.pmsg.service.atom.impl;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.dfocus.pmsg.facade.model.WsMessage;
import com.dfocus.pmsg.service.atom.IWsMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author: baozi
 * @date: 2019/8/8 17:05
 * @description:
 */
@Slf4j
@Service
public class WsMessageServiceImpl implements IWsMessageService {

	@Autowired
	SocketIOServer socketIOServer;

	// SocketIOServer --> 多SocketIONamespace(NameSpace) --> 多Room（BroadcastOperations） -->
	// 多SocketIOClient
	// send(event, ) send（event, )

	// NameSpaceClient 拥有 NameSpace

	// server --> namespaces
	// SocketIONamespace namespace = server.getNamespace("sdsd");
	//
	// // namespace --> rooms
	// BroadcastOperations roomOperations = namespace.getRoomOperations();
	// roomOperations.send();
	//
	// // room --> clients
	// Collection<SocketIOClient> clients = roomOperations.getClients();
	// for (SocketIOClient client : clients) {
	// client.send();
	// }

	@Override
	public boolean send(WsMessage message) {

		log.info("project:{}|group:{}|message:{}", message.getProjectId(), message.getTopic(), message.getPlayLoad());

		// 获取namespace
		SocketIONamespace namespace = socketIOServer.getNamespace(message.getProjectId());
		if (namespace == null) {
			log.error("该项目下没发现客户端");
		}

		// 获取room
		BroadcastOperations roomOperations = namespace.getRoomOperations(message.getTopic());

		roomOperations.sendEvent(StringUtils.isEmpty(message.getEvent()) ? "message" : message.getEvent(),
				message.getPlayLoad());

		return true;
	}

}
