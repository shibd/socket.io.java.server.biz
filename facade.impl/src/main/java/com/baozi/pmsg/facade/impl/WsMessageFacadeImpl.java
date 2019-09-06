package com.baozi.pmsg.facade.impl;

import com.baozi.mint.web.rsp.Response;
import com.baozi.pmsg.facade.api.WsMessageFacade;
import com.baozi.pmsg.facade.model.WsMessage;
import com.baozi.pmsg.service.atom.IWsMessageService;
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
	public Response<Boolean> sendMessage(@RequestBody WsMessage message) {
		return Response.success(wsMessageService.send(message));
	}

}
