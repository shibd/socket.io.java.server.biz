package com.dfocus.pmsg.benchmark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: baozi
 * @date: 2019/8/9 14:55
 * @description:
 */
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

	private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);

	private AtomicInteger counter = new AtomicInteger(0);

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
		counter.incrementAndGet();
		logger.info(
				Thread.currentThread().getName() + ", messageId:" + headers.getMessageId() + ", message::" + payload);
	}

	public Integer getCounter() {
		return counter.get();
	}

}
