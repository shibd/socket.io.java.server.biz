package com.dfocus.pmsg.benchmark;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.EngineIOException;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author: baozi
 * @date: 2019/8/28 11:20
 * @description:
 */
public class SocketIOJavaClient {

	private static final String projectIdFm = "fm";

	private static final String topicFm = "group_fm";

	public static final String token = "eyJ1c2VyTmFtZSI6ImJhb3ppIiwiYWxnIjoiUlMyNTYifQ.eyJleHAiOjE1Njc1OTE0OTUsInVzZXJOYW1lIjoiYmFvemkiLCJpYXQiOjE1NjQ5OTk0OTV9.LT_BS85442djhR8YWbRVOob6yNFRYZomMBWLQkIomFfppTOU1-g3R7OmitDqwDGmTFjj12ElUch0Ov9GkP46mxX1eWuDZxXssOp8ePtoNX69exYgfXO0wRX-C4W15oPQra_PhWbqn0RxOoIGN1Uxn4CPjQ8s8dFgwP4MJjrnE3g";

	public static void main(String[] args) throws URISyntaxException {
		IO.Options optionsFm = new IO.Options();
		optionsFm.transports = new String[] { "websocket" };
		optionsFm.reconnectionAttempts = 2; // 重连尝试次数
		optionsFm.reconnectionDelay = 1000; // 失败重连的时间间隔(ms)
		optionsFm.timeout = 20000; // 连接超时时间(ms)
		optionsFm.forceNew = true;
		optionsFm.query = String.format("projectId=%s&topic=%s&token=%s", projectIdFm, topicFm, token);
		Socket socketFm = IO.socket("http://localhost:9092/" + projectIdFm, optionsFm);
		NamespaceClient(socketFm);
		socketFm.connect();
	}

	private static void NamespaceClient(Socket socket) {
		// 连接消息
		socket.on(Socket.EVENT_CONNECT, args -> {
			System.out.println("客户端连接成功事件, 准备发起登录请求");
			// 客户端一旦连接成功，开始发订阅录请求
			socket.emit("subscribe", Arrays.asList("group_1", "group_2"), (Ack) args1 -> System.out
					.println("订阅回执消息=" + Arrays.stream(args1).map(Object::toString).collect(Collectors.joining(","))));
		});
		// 组消息
		socket.on("event_1", args -> System.out.println("-----------event_1接受到消息啦--------" + Arrays.toString(args)));
		// 组消息
		socket.on("event_2", args -> System.out.println("-----------event_2接受到消息啦--------" + Arrays.toString(args)));
		// 连接失败消息
		socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
			System.out.println("Socket.EVENT_CONNECT_ERROR" + Arrays.toString(args));
			// 鉴权失败，不重连
			Object object = args[0];
			if (object != null && object instanceof EngineIOException) {
				EngineIOException engineIOException = (EngineIOException) object;
				String message = engineIOException.getCause().getMessage();
				if (message.contains("Unauthorized")) {
					System.out.println("认证失败断开连接");
					socket.disconnect();
					return;
				}
			}
			// 其他错误重连
			reConenect(socket);
		});
		// 连接超时
		socket.on(Socket.EVENT_CONNECT_TIMEOUT, args -> {
			System.out.println("Socket.EVENT_CONNECT_TIMEOUT" + Arrays.toString(args));
			reConenect(socket);
		});
		// ping
		socket.on(Socket.EVENT_PING, args -> System.out.println("Socket.EVENT_PING"));
		// pong
		socket.on(Socket.EVENT_PONG, args -> System.out.println("Socket.EVENT_PONG"));
		// 断开连接
		socket.on(Socket.EVENT_DISCONNECT, args -> {
			System.out.println("端断开连接啦。。。" + Arrays.toString(args));
			reConenect(socket);
		});
	}

	public static void reConenect(Socket socket) {
		try {
			socket.disconnect();
			System.out.println("正在进行重连");
			Thread.sleep(10000);
			socket.connect();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
