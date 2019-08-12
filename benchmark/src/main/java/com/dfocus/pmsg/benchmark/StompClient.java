package com.dfocus.pmsg.benchmark;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: baozi
 * @date: 2019/8/9 14:57
 * @description:
 */
public class StompClient {

	public static String TOKEN = "eyJ1c2VyTmFtZSI6Ind1ZGl4aWFvYmFvemkiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1Njc1OTE1NDYsInVzZXJOYW1lIjoid3VkaXhpYW9iYW96aSIsImlhdCI6MTU2NDk5OTU0Nn0.dEJzjgwwZCL6qh3NtluVSo0uZZZdUEzrNF2pLsUxprVOSE-pzaUVlOw2EmntXd4IpFs3qI0IwA4F51VOFIX65lc1RoX93AFeb44CYt9JpXKcGtGYWQr2D4nsNMaS7je8abtastBC8QIInCYtC7s8tvaAQRzYTvCZmSM8vtgu06g";

	public static String REQ_URL = "ws://139.217.99.53:8080/msg-center/websocket?token=" + TOKEN + "&projectId=fm";

	public static WebSocketStompClient stompClient;

	public static StompSessionHandler sessionHandler;

	public static void main(String[] args) throws InterruptedException {

		long clientThreadNum = 3000;
		if (args.length > 0 && !StringUtils.isEmpty(args[0])) {
			clientThreadNum = Long.valueOf(args[0]);
			System.out.println("客户端建立链接数:" + clientThreadNum);
		}

		if (args.length > 1 && !StringUtils.isEmpty(args[1])) {
			REQ_URL = "ws://" + args[1] + "/msg-center/websocket?token=" + TOKEN + "&projectId=fm";
			System.out.println("远程连接地址:" + args[1]);
		}

		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		WebSocketClient transport = new SockJsClient(transports);
		stompClient = new WebSocketStompClient(transport);
		stompClient.setMessageConverter(new StringMessageConverter());
		sessionHandler = new MyStompSessionHandler();

		ExecutorService executorService = new ThreadPoolExecutor(50, 1000, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(500));

		// 每条线程处理链接数
		long singeDelNum = 100;
		while (clientThreadNum > 0) {
			long delNum = clientThreadNum > singeDelNum ? singeDelNum : clientThreadNum;
			try {
				executorService.submit(new DealThread(delNum));
			}
			catch (Exception e) {
				Thread.sleep(5000);
				continue;
			}
			clientThreadNum -= singeDelNum;
		}

		Thread.sleep(50000000);

	}

	public static class DealThread implements Runnable {

		private long dealNum;

		public DealThread(long dealNum) {
			this.dealNum = dealNum;
		}

		@Override
		public void run() {
			int waitTime = 1;
			while (dealNum > 0) {

				try {
					Thread.sleep(waitTime);
					ListenableFuture<StompSession> connect = stompClient.connect(REQ_URL, sessionHandler);
					StompSession stompSession = connect.get();
					if (stompSession == null) {
						waitTime += 1000;
						continue;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					waitTime += 1000;
					continue;
				}

				// 走到后面说明连接成功,置回正常等待时间,处理下一位
				if (waitTime > 1000) {
					waitTime = 1;
				}
				dealNum--;
			}

			System.out.println(Thread.currentThread().getName() + " is die");
		}

	}

}
