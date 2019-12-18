package com.dfocus.pmsg.controller;

import com.dfocus.pmsg.facade.model.WsMessage;
import com.dfocus.pmsg.facade.web.rsp.Response;
import com.dfocus.pmsg.service.atom.IWsMessageService;
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

	/**
	 * 发送广播消息
	 * @param message
	 * @return
	 */
	@ApiOperation("接口: 发送广播消息")
	@PostMapping("/topic")
	Response<Boolean> sendMessage(@RequestBody WsMessage.WsTopicMessage message) {
		return Response.success(wsMessageService.sendToTopic(message));
	}

	/**
	 * 发送用户消息
	 * @param message
	 * @return
	 */
	@ApiOperation("接口: 发送用户消息")
	@PostMapping("/user")
	Response<Boolean> sendMessageToUser(@RequestBody WsMessage.WsUserMessage message) {
		return Response.success(wsMessageService.sendToUser(message));
	}

}
