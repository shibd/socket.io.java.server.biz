package com.dfocus.pmsg.benchmark;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: baozi
 * @date: 2019/8/28 11:43
 * @description:
 */
public class SocketIONettyServer {

	public static void main(String[] args) {
		/*
		 * 创建Socket，并设置监听端口
		 */
		Configuration config = new Configuration();
		// 设置主机名
		config.setHostname("localhost");
		// 设置监听端口
		config.setPort(9092);
		// 协议升级超时时间（毫秒），默认10秒。HTTP握手升级为ws协议超时时间
		config.setUpgradeTimeout(10000);
		// Ping消息超时时间（毫秒），默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
		config.setPingTimeout(180000);
		// Ping消息间隔（毫秒），默认25秒。客户端向服务器发送一条心跳消息间隔
		config.setPingInterval(60000);
		// 连接认证，这里使用token更合适
		config.setAuthorizationListener(new AuthorizationListener() {
			@Override
			public boolean isAuthorized(HandshakeData data) {
				// String token = data.getSingleUrlParam("token");
				// String username = JWTUtil.getSocketUsername(token);
				// return JWTUtil.verifySocket(token, "secret");
				return true;
			}
		});

		final SocketIOServer server = new SocketIOServer(config);

		/*
		 * 添加连接监听事件，监听是否与客户端连接到服务器
		 */
		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				// 判断是否有客户端连接
				if (client != null) {
					System.out.println(("连接成功。clientId=" + client.getSessionId().toString()));
					client.joinRoom(client.getHandshakeData().getSingleUrlParam("appid"));
				}
				else {
					System.out.println("并没有人连接上。。。");
				}
			}
		});

		/*
		 * 添加监听事件，监听客户端的事件 1.第一个参数eventName需要与客户端的事件要一致 2.第二个参数eventClase是传输的数据类型
		 * 3.第三个参数listener是用于接收客户端传的数据，数据类型需要与eventClass一致
		 */
		server.addEventListener("login", String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
				System.out.println(("接收到客户端login消息：code = " + data));
				// check is ack requested by client, but it's not required check
				if (ackRequest.isAckRequested()) {
					// send ack response with data to client
					ackRequest.sendAckData("已成功收到客户端登录请求", "yeah");
				}
				// 向客户端发送消息
				List<String> list = new ArrayList<>();
				list.add("登录成功，sessionId=" + client.getSessionId());
				// 第一个参数必须与eventName一致，第二个参数data必须与eventClass一致
				String room = client.getHandshakeData().getSingleUrlParam("appid");
				server.getRoomOperations(room).sendEvent("login", list.toString());
			}
		});
		// 启动服务
		server.start();
	}

}
