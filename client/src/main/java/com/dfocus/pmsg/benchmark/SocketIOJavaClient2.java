//package com.dfocus.pmsg.benchmark;
//
//import io.socket.client.Ack;
//import io.socket.client.IO;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
//
//import java.net.URISyntaxException;
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
///**
// * @author: baozi
// * @date: 2019/8/28 11:20
// * @description:
// */
//public class SocketIOJavaClient2 {
//
//	private static final String projectIdFm = "fm";
//
//	private static final String projectIdAm = "am";
//
//	private static final String topicFm = "group_fm";
//
//	private static final String topicAm = "group_am";
//
//	private static final String token = "test_token";
//
//	public static void main(String[] args) throws URISyntaxException {
//		IO.Options optionsFm = new IO.Options();
//		optionsFm.transports = new String[] { "websocket" };
//		optionsFm.reconnectionAttempts = 2; // 重连尝试次数
//		optionsFm.reconnectionDelay = 1000; // 失败重连的时间间隔(ms)
//		optionsFm.timeout = 20000; // 连接超时时间(ms)
//		optionsFm.forceNew = true;
//		optionsFm.query = String.format("projectId=%s&topic=%s&token=%s", projectIdFm, topicFm, token);
//		Socket socketFm = IO.socket("http://localhost:9092/" + projectIdFm, optionsFm);
//		NamespaceClient(socketFm);
//		socketFm.connect();
//
//		IO.Options optionsAm = new IO.Options();
//		optionsAm.transports = new String[] { "websocket" };
//		optionsAm.reconnectionAttempts = 2; // 重连尝试次数
//		optionsAm.reconnectionDelay = 1000; // 失败重连的时间间隔(ms)
//		optionsAm.timeout = 20000; // 连接超时时间(ms)
//		optionsAm.forceNew = true;
//		optionsAm.query = String.format("projectId=%s&topic=%s&token=%s", projectIdAm, topicAm, token);
//		Socket socketAm = IO.socket("http://localhost:9092/" + projectIdAm, optionsAm);
//		NamespaceClient(socketAm);
//		socketAm.connect();
//
//
//        IO.Options optionsAm2 = new IO.Options();
//        optionsAm2.transports = new String[] { "websocket" };
//        optionsAm2.reconnectionAttempts = 2; // 重连尝试次数
//        optionsAm2.reconnectionDelay = 1000; // 失败重连的时间间隔(ms)
//        optionsAm2.timeout = 20000; // 连接超时时间(ms)
//        optionsAm2.forceNew = true;
//        optionsAm2.query = String.format("projectId=%s&topic=%s&token=%s", projectIdAm, topicAm, token);
//        Socket socketAm2 = IO.socket("http://localhost:9092/" + projectIdAm, optionsAm);
//        NamespaceClient(socketAm2);
//        socketAm2.connect();
//	}
//
//	private static void NamespaceClient(Socket socket) {
//		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//			@Override
//			public void call(Object... args) {
//				System.out.println("客户端连接成功事件, 准备发起登录请求");
//				// 客户端一旦连接成功，开始发起登录请求
//				socket.emit("subscribe", Arrays.asList("group_1", "group_2"), (Ack) args1 -> System.out.println(
//						"订阅回执消息=" + Arrays.stream(args1).map(Object::toString).collect(Collectors.joining(","))));
//			}
//        }).on("group_1", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                System.out.println("-----------group1接受到消息啦--------" + Arrays.toString(args));
//            }
//        }).on("group_2", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                System.out.println("-----------group2接受到消息啦--------" + Arrays.toString(args));
//            }
//		}).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
//			@Override
//			public void call(Object... args) {
//				System.out.println("Socket.EVENT_CONNECT_ERROR");
//				socket.disconnect();
//			}
//		}).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
//			@Override
//			public void call(Object... args) {
//				System.out.println("Socket.EVENT_CONNECT_TIMEOUT");
//
//                try {
//                    Thread.sleep(30000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                socket.connect();
//			}
//		}).on(Socket.EVENT_PING, new Emitter.Listener() {
//			@Override
//			public void call(Object... args) {
//				System.out.println("Socket.EVENT_PING");
//			}
//		}).on(Socket.EVENT_PONG, new Emitter.Listener() {
//			@Override
//			public void call(Object... args) {
//				System.out.println("Socket.EVENT_PONG");
//			}
//		}).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
//			@Override
//			public void call(Object... args) {
//				System.out.println("-----------接受到消息啦--------" + Arrays.toString(args));
//			}
//		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//			@Override
//			public void call(Object... args) {
//				System.out.println("客户端断开连接啦。。。");
//
//                try {
//                    Thread.sleep(30000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                socket.connect();
//            }
//		});
//	}
//
//}
