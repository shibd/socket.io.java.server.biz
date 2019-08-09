package com.dfocus.pmsg.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

/**
 * @author: baozi
 * @date: 2019/8/9 14:55
 * @description:
 */
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

	private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		logger.info("New session established : " + session.getSessionId());
		session.subscribe("/topic/fm/all/group1/", this);
		logger.info("Subscribed to /topic/fm/test");
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		logger.error("Got an exception", exception);
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		logger.info(Thread.currentThread().getName() + "--> Received : " + payload);
	}

	/**
	 * A sample message instance.
	 * @return instance of <code>Message</code>
	 */
	private Message getSampleMessage() {
		Message msg = new Message();
		msg.setFrom("Nicky");
		msg.setText("Howdy!!");
		return msg;
	}

}
