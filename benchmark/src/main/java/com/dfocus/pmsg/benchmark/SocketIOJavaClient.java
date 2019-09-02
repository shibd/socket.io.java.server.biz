package com.dfocus.pmsg.benchmark;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: baozi
 * @date: 2019/8/28 11:20
 * @description:
 */
public class SocketIOJavaClient {

	public static final String token = "eyJ1c2VyTmFtZSI6ImJhb3ppIiwiYWxnIjoiUlMyNTYifQ.eyJleHAiOjE1Njc1OTE0OTUsInVzZXJOYW1lIjoiYmFvemkiLCJpYXQiOjE1NjQ5OTk0OTV9.LT_BS85442djhR8YWbRVOob6yNFRYZomMBWLQkIomFfppTOU1-g3R7OmitDqwDGmTFjj12ElUch0Ov9GkP46mxX1eWuDZxXssOp8ePtoNX69exYgfXO0wRX-C4W15oPQra_PhWbqn0RxOoIGN1Uxn4CPjQ8s8dFgwP4MJjrnE3g";

	private static final String projectIdFm = "fm";

	private static final List<String> subscribes = Arrays.asList("group_1", "group_2");

	private static volatile boolean isReConnect = true;

	public static void main(String[] args) throws URISyntaxException, InterruptedException {
		IO.Options optionsFm = new IO.Options();
		optionsFm.transports = new String[] { "websocket" };
		optionsFm.reconnectionAttempts = 2; // 重连尝试次数
		optionsFm.reconnectionDelay = 1000; // 失败重连的时间间隔(ms)
		optionsFm.timeout = 20000; // 连接超时时间(ms)
		optionsFm.forceNew = true;
		Socket socketFm = IO.socket("http://localhost:9092/" + projectIdFm, optionsFm);
		HashMap<String, String> reqData = new HashMap<>(2);
		reqData.put("projectId", projectIdFm);
		reqData.put("token", token);
		NamespaceClient(socketFm, reqData);
		socketFm.connect();
	}

	private static void NamespaceClient(Socket socket, Map<String, String> reqData) {

		// 普通消息
		socket.on("event_1", args -> System.out.println("-----------event_1接受到消息啦--------" + Arrays.toString(args)));

		// 普通消息
		socket.on("event_2", args -> System.out.println("-----------event_2接受到消息啦--------" + Arrays.toString(args)));

		// 连接成功消息
		socket.on(Socket.EVENT_CONNECT, args -> {
			System.out.println("客户端连接成功事件, 准备发起登录认证请求");
			// 认证,订阅
			socket.emit("auth", reqData, (Ack) authAcks -> {
				String authAckCode = (String) authAcks[0];
				System.out.println("auth ack code:" + authAckCode);
				if (authAckCode.equals("auth_success")) {
					socket.emit("subscribe", subscribes, (Ack) subAcks -> {
						String subAckCode = (String) subAcks[0];
						System.out.println("sub ack code:" + subAckCode);
						if (subAckCode.equals("sub_success")) {
							System.out.println("subscribe success");
						}
						else {
							socket.disconnect();
						}
					});
				}
				else {
					System.out.println("校验失败断开连接");
					isReConnect = false;
					socket.disconnect();
				}
			});
		});

		// 连接失败消息
		socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
			System.out.println("Socket.EVENT_CONNECT_ERROR" + Arrays.toString(args));
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
		    if (!isReConnect) {
		        return;
			}
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
