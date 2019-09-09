package com.baozi.pmsg.benchmark;

import io.socket.client.Ack;
import io.socket.client.Socket;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: baozi
 * @date: 2019/9/3 09:49
 * @description:
 */
public class SocketIOJavaClientCommon {

	/**
	 * 是否重连标识符
	 */
	private volatile boolean isReConnect = true;

	/**
	 * socket Client
	 */
	private Socket socket;

	/**
	 * 认证数据
	 */
	private Map<String, String> authData;

	/**
	 * 订阅集合
	 */
	private List<String> subscribes;

	public SocketIOJavaClientCommon(Socket socket, Map<String, String> authData, List<String> subscribes) {
		this.socket = socket;
		this.authData = authData;
		this.subscribes = subscribes;
	}

	public void start() {
		onClientEvent();
		socket.connect();
	}

	/**
	 * 事件监听
	 */
	public void onClientEvent() {

		// 普通消息
		socket.on("event_1", args -> System.out.println("-----------event_1接受到消息啦--------" + Arrays.toString(args)));

		// 普通消息
		socket.on("event_2", args -> System.out.println("-----------event_2接受到消息啦--------" + Arrays.toString(args)));

		// 连接成功消息
		socket.on(Socket.EVENT_CONNECT, args -> {
			System.out.println("客户端连接成功事件, 准备发起登录认证请求");
			// 认证,订阅
			socket.emit("auth", authData, (Ack) authAcks -> {
				String authAckCode = (String) authAcks[0];
				System.out.println("auth ack code:" + authAckCode);
				if ("auth_success".equals(authAckCode)) {
					socket.emit("subscribe", subscribes, (Ack) subAcks -> {
						String subAckCode = (String) subAcks[0];
						System.out.println("sub ack code:" + subAckCode);
						if ("sub_success".equals(subAckCode)) {
							System.out.println("subscribe success");
						}
						else {
							isReConnect = false;
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

	/**
	 * 重连
	 * @param socket
	 */
	public void reConenect(Socket socket) {
		try {
			if (!isReConnect) {
				socket.disconnect();
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
