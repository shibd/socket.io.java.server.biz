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


    private static final String projectIdFm = "fm";
    private static final String projectIdAm = "am";
    private static final String topic = "group1";
    private static final String token = "test_token";

    public static void main(String[] args) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"};
        options.reconnectionAttempts = 2;     // 重连尝试次数
        options.reconnectionDelay = 1000;     // 失败重连的时间间隔(ms)
        options.timeout = 20000;              // 连接超时时间(ms)
        options.forceNew = true;

        options.query = String.format("projectId=%s&topic=%s&token=%s", projectIdFm, topic, token);
        Socket socketFm = IO.socket("http://localhost:9092/" + projectIdFm, options);


        options.query = String.format("projectId=%s&topic=%s&token=%s", projectIdAm, topic, token);
        Socket socketAm = IO.socket("http://localhost:9092/" + projectIdAm, options);

        NamespaceClient(socketFm);
        NamespaceClient(socketAm);

        socketFm.connect();
        socketAm.connect();
    }

    private static void NamespaceClient(Socket socket) {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("客户端连接成功事件, 准备发起登录请求");
                // 客户端一旦连接成功，开始发起登录请求
                socket.emit("login", "**这是登录消息**", (Ack) args1 -> {
                    System.out.println("回执消息=" + Arrays.stream(args1).map(Object::toString).collect(Collectors.joining(",")));
                });
            }
        }).on("login", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("接受到服务器房间广播的登录消息：" + Arrays.toString(args));
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Socket.EVENT_CONNECT_ERROR");
                socket.disconnect();
            }
        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Socket.EVENT_CONNECT_TIMEOUT");
                socket.disconnect();
            }
        }).on(Socket.EVENT_PING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Socket.EVENT_PING");
            }
        }).on(Socket.EVENT_PONG, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Socket.EVENT_PONG");
            }
        }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("-----------接受到消息啦--------" + Arrays.toString(args));
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("客户端断开连接啦。。。");
                socket.disconnect();
            }
        });
    }

}
