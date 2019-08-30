package com.dfocus.pmsg.controller;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.dfocus.mint.web.rsp.Response;
import com.dfocus.pmsg.facade.model.WsMessage;
import com.dfocus.pmsg.service.atom.IWsMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
	 * 发送测试消息
	 * @return
	 */
	@ApiOperation("接口: 发送广播消息")
	@GetMapping("/test")
	Response<Boolean> sendTestMessage() {

		SocketIONamespace namespace = socketIOServer.getNamespace("/fm");
		if (namespace == null) {
			System.out.println("该项目下没发现客户端");
		}

		BroadcastOperations group_fm1 = namespace.getRoomOperations("group_1");
		group_fm1.sendEvent("group_1", Arrays.asList("这是从fm的namespace下找到room发送的广播消息fm-1"));
		BroadcastOperations group_fm2 = namespace.getRoomOperations("group_2");
		group_fm2.sendEvent("group_2", Arrays.asList("这是从fm的namespace下找到room发送的广播消息fm-2"));

		return Response.success(true);
	}

	/**
	 * 发送消息
	 * @param message
	 * @return
	 */
	@ApiOperation("接口: 发送广播消息")
	@PostMapping("/topic")
	Response<Boolean> sendMessage(@RequestBody WsMessage message) {
		return Response.success(wsMessageService.send(message));
	}

}
