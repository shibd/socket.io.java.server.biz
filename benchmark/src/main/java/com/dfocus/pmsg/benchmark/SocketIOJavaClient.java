package com.dfocus.pmsg.benchmark;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author: baozi
 * @date: 2019/8/28 11:20
 * @description:
 */
public class SocketIOJavaClient {

	public static final String token = "eyJ1c2VyTmFtZSI6ImJhb3ppIiwiYWxnIjoiUlMyNTYifQ.eyJleHAiOjE1Njc1OTE0OTUsInVzZXJOYW1lIjoiYmFvemkiLCJpYXQiOjE1NjQ5OTk0OTV9.LT_BS85442djhR8YWbRVOob6yNFRYZomMBWLQkIomFfppTOU1-g3R7OmitDqwDGmTFjj12ElUch0Ov9GkP46mxX1eWuDZxXssOp8ePtoNX69exYgfXO0wRX-C4W15oPQra_PhWbqn0RxOoIGN1Uxn4CPjQ8s8dFgwP4MJjrnE3g";

	private static final String projectIdFm = "fm";

	private static final List<String> subscribes = Arrays.asList("group_1", "group_2");

	public static void main(String[] args) throws URISyntaxException {

		IO.Options optionsFm = new IO.Options();
		optionsFm.transports = new String[] { "websocket" };
		optionsFm.reconnectionAttempts = 2; // 重连尝试次数
		optionsFm.reconnectionDelay = 1000; // 失败重连的时间间隔(ms)
		optionsFm.timeout = 20000; // 连接超时时间(ms)
		optionsFm.forceNew = true;

		Socket socketFm = IO.socket("http://139.217.99.53:9092/" + projectIdFm, optionsFm);
		HashMap<String, String> authData = new HashMap<>(2);
		authData.put("projectId", projectIdFm);
		authData.put("token", token);

		SocketIOJavaClientCommon socketIOJavaClientCommon = new SocketIOJavaClientCommon(socketFm, authData,
				subscribes);
		socketIOJavaClientCommon.start();
	}

}
