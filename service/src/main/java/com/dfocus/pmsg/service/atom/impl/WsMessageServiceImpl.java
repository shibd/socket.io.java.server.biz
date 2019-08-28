package com.dfocus.pmsg.service.atom.impl;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.Packet;
import com.dfocus.pmsg.facade.model.WsMessage;
import com.dfocus.pmsg.service.atom.IWsMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    // SocketIOServer --> 多SocketIONamespace(NameSpace) --> 多Room（BroadcastOperations） --> 多SocketIOClient
    //                                                  send(event, )          send（event, )

    // NameSpaceClient 拥有 NameSpace






    // server --> namespaces
//        SocketIONamespace namespace = server.getNamespace("sdsd");
//
//        // namespace --> rooms
//        BroadcastOperations roomOperations = namespace.getRoomOperations();
//        roomOperations.send();
//
//        // room --> clients
//        Collection<SocketIOClient> clients = roomOperations.getClients();
//        for (SocketIOClient client : clients) {
//            client.send();
//		}


	@Override
	public boolean sendToTopic(WsMessage.WsTopicMessage message) {

        log.info("project:{}|group:{}|message:{}", message.getProjectId(), message.getTopic(), message.getPlayLoad());

        SocketIONamespace namespace = socketIOServer.getNamespace(message.getProjectId());
        if (namespace == null) {
            log.error("该项目下没发现客户端");
		}


        BroadcastOperations roomOperations = namespace.getRoomOperations(message.getTopic());

        roomOperations.sendEvent(message.getTopic(), message.getPlayLoad());

		return true;
	}

	@Override
	public boolean sendToUser(WsMessage.WsUserMessage message) {

		log.info("project:{}|user:{}|message:{}", message.getProjectId(), message.getUser(), message.getPlayLoad());

        SocketIONamespace namespace = socketIOServer.getNamespace(message.getProjectId());
        if (namespace == null) {
            log.error("该项目下没发现客户端");
        }

        SocketIOClient client = namespace.getClient(UUID.randomUUID());

        client.sendEvent(message.getUser(), message.getPlayLoad());

		return true;
	}

}
