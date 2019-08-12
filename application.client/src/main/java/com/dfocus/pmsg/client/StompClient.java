package com.dfocus.pmsg.client;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: baozi
 * @date: 2019/8/9 14:57
 * @description:
 */
public class StompClient {

	private static String TOKEN = "eyJ1c2VyTmFtZSI6Ind1ZGl4aWFvYmFvemkiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1Njc1OTE1NDYsInVzZXJOYW1lIjoid3VkaXhpYW9iYW96aSIsImlhdCI6MTU2NDk5OTU0Nn0.dEJzjgwwZCL6qh3NtluVSo0uZZZdUEzrNF2pLsUxprVOSE-pzaUVlOw2EmntXd4IpFs3qI0IwA4F51VOFIX65lc1RoX93AFeb44CYt9JpXKcGtGYWQr2D4nsNMaS7je8abtastBC8QIInCYtC7s8tvaAQRzYTvCZmSM8vtgu06g";

	private static String REQ_URL = "ws://localhost:8080/msg-center/websocket?token=" + TOKEN + "&projectId=fm";

	public static void main(String[] args) {

		long clientThreadNum = 5000;
		if (args.length > 0 && !StringUtils.isEmpty(args[0])) {
			clientThreadNum = Long.valueOf(args[0]);
			System.out.println("客户端建立链接数:" + clientThreadNum);
		}

		if (args.length > 0 && !StringUtils.isEmpty(args[1])) {
			REQ_URL = "ws://" + args[1] + "/msg-center/websocket?token=" + TOKEN + "&projectId=fm";
			System.out.println("远程连接地址:" + args[1]);
		}

		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		WebSocketClient transport = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(transport);

		stompClient.setMessageConverter(new StringMessageConverter());

		StompSessionHandler sessionHandler = new MyStompSessionHandler();
		while (clientThreadNum-- > 0) {
			stompClient.connect(REQ_URL, sessionHandler);
		}
		// Don't close immediately.
		new Scanner(System.in).nextLine();
	}

}
