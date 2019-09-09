package com.baozi.pmsg.controller;

import com.baozi.mint.web.rsp.Response;
import com.baozi.pmsg.facade.model.WsMessage;
import com.baozi.pmsg.service.atom.IWsMessageService;
import com.corundumstudio.socketio.SocketIOServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@Api("接口: 消息管理类")
@RestController
@RequestMapping("/ws_message")
public class WsMessageController {

	@Autowired
	IWsMessageService wsMessageService;

	@Autowired
	SocketIOServer socketIOServer;

	/**
	 * 发送消息
	 * @param message
	 * @return
	 */
	@ApiOperation("接口: 发送消息")
	@PostMapping("/topic")
	Response<Boolean> sendMessage(@RequestBody WsMessage message) {
		return Response.success(wsMessageService.send(message));
	}

}
