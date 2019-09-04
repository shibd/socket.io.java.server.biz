package com.dfocus.pmsg.service.atom;

import com.dfocus.pmsg.facade.model.WsMessage;

/**
 * @author: baozi
 * @date: 2019/8/8 17:01
 * @description:
 */
public interface IWsMessageService {

	/**
	 * 发送消息
	 * @param wsMessage
	 * @return
	 */
	boolean send(WsMessage wsMessage);

}
