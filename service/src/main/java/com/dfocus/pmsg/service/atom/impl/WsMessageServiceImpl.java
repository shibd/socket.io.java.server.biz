package com.dfocus.pmsg.service.atom.impl;

import com.dfocus.pmsg.facade.model.WsMessage;
import com.dfocus.pmsg.service.atom.IWsMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: baozi
 * @date: 2019/8/8 17:05
 * @description:
 */
@Slf4j
@Service
public class WsMessageServiceImpl implements IWsMessageService {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Override
	public boolean sendToTopic(WsMessage.WsTopicMessage message) {

		log.info("project:{}|group:{}|message:{}", message.getProjectId(), message.getTopic(), message.getPlayLoad());

		String destination = String.format("/topic/%s/%s", message.getProjectId(), message.getTopic());

		// 发送消息
		messagingTemplate.convertAndSend(destination, message.getPlayLoad());

		return true;
	}

	@Override
	public boolean sendToUser(WsMessage.WsUserMessage message) {

		log.info("project:{}|user:{}|message:{}", message.getProjectId(), message.getUser(), message.getPlayLoad());

		String destination = String.format("/queue/%s/", message.getProjectId());

		// 发送消息
		messagingTemplate.convertAndSendToUser(message.getUser(), destination, message.getPlayLoad());

		return true;
	}

}
