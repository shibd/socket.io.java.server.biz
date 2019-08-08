package com.dfocus.pmsg.facade.api;

import com.dfocus.mint.web.rsp.Response;
import com.dfocus.pmsg.facade.model.WsMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@RequestMapping("/facade/ws_message")
public interface WsMessageFacade {

	/**
	 * 发送广播消息
	 * @param message
	 * @return
	 */
	@PostMapping("/send/topic")
	Response<Boolean> sendMessage(@RequestBody WsMessage.WsTopicMessage message);

	/**
	 * 发送
	 * @param message
	 * @return
	 */
	@PostMapping("/send/user")
	Response<Boolean> sendMessageToUser(@RequestBody WsMessage.WsUserMessage message);

}
