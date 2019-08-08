package com.dfocus.pmsg.service.atom;

import com.dfocus.pmsg.facade.model.WsMessage;

/**
 * @author: baozi
 * @date: 2019/8/8 17:01
 * @description:
 */
public interface IWsMessageService {

	/**
	 * 发送消息到广播主题
	 * @param wsTopicMessage
	 * @return
	 */
	boolean sendToTopic(WsMessage.WsTopicMessage wsTopicMessage);

	/**
	 * 发送消息到用户
	 * @param wsUserMessage
	 * @return
	 */
	boolean sendToUser(WsMessage.WsUserMessage wsUserMessage);

}
