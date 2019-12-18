package com.dfocus.pmsg.facade.impl;

import com.dfocus.pmsg.facade.api.WsMessageFacade;
import com.dfocus.pmsg.facade.model.WsMessage;
import com.dfocus.pmsg.facade.web.rsp.Response;
import com.dfocus.pmsg.service.atom.IWsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: baozi
 * @date: 2019/8/8 14:57
 * @description:
 */
@RestController
public class WsMessageFacadeImpl implements WsMessageFacade {

	@Autowired
	private IWsMessageService wsMessageService;

	@Override
	public Response<Boolean> sendMessage(@RequestBody WsMessage.WsTopicMessage message) {
		return Response.success(wsMessageService.sendToTopic(message));
	}

	@Override
	public Response<Boolean> sendMessageToUser(@RequestBody WsMessage.WsUserMessage message) {
		return Response.success(wsMessageService.sendToUser(message));
	}

}
