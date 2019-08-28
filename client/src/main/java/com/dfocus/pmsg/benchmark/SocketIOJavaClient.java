package com.dfocus.pmsg.benchmark;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author: baozi
 * @date: 2019/8/28 11:20
 * @description:
 */
public class SocketIOJavaClient {

	private static Socket socket;

	public static void main(String[] args) throws URISyntaxException {
		IO.Options options = new IO.Options();
		options.transports = new String[] { "websocket" };
		options.reconnectionAttempts = 2; // 重连尝试次数
		options.reconnectionDelay = 1000; // 失败重连的时间间隔(ms)
		options.timeout = 20000; // 连接超时时间(ms)
		options.forceNew = true;
		options.query = "projectId=fm&topic=group&token=test_token";


		socket = IO.socket("http://localhost:9092/fm", options);

		socket.on(Socket.EVENT_CONNECT, args12 -> {
            // 客户端一旦连接成功，开始发起登录请求
            LoginRequest loginRequest = new LoginRequest(11, "ceshisss");
            System.out.println("连接成功事件");
            socket.emit("login", loginRequest);
        }).on("login", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println(("接受到服务器房间广播的登录消息：" + Arrays.toString(args)));
			}
		}).on("test", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println(("my test" + Arrays.toString(args)));
			}
		}).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println("Socket.EVENT_CONNECT_ERROR:" + args.toString());
				socket.disconnect();
			}
		}).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println(("Socket.EVENT_CONNECT_TIMEOUT"));
				socket.disconnect();
			}
		}).on(Socket.EVENT_PING, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println(("Socket.EVENT_PING"));
			}
		}).on(Socket.EVENT_PONG, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println(("Socket.EVENT_PONG"));
			}
		}).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println(("-----------接受到消息啦--------" + Arrays.toString(args)));
			}
		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println(("客户端断开连接啦。。。"));
				socket.disconnect();
			}
		});
		socket.connect();
	}

}
