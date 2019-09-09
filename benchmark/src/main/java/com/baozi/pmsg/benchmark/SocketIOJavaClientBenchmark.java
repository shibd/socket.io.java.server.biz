package com.baozi.pmsg.benchmark;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: baozi
 * @date: 2019/8/28 11:20
 * @description: 该压测程序在1000连接时会报错java.lang.OutOfMemoryError: Unable to create new native
 * thread 待找原因
 */
public class SocketIOJavaClientBenchmark {

	public static final String token = "eyJ1c2VyTmFtZSI6ImJhb3ppIiwiYWxnIjoiUlMyNTYifQ.eyJleHAiOjE1Njc1OTE0OTUsInVzZXJOYW1lIjoiYmFvemkiLCJpYXQiOjE1NjQ5OTk0OTV9.LT_BS85442djhR8YWbRVOob6yNFRYZomMBWLQkIomFfppTOU1-g3R7OmitDqwDGmTFjj12ElUch0Ov9GkP46mxX1eWuDZxXssOp8ePtoNX69exYgfXO0wRX-C4W15oPQra_PhWbqn0RxOoIGN1Uxn4CPjQ8s8dFgwP4MJjrnE3g";

	private static final String projectIdFm = "fm";

	private static final List<String> subscribes = Arrays.asList("group_1", "group_2");

	private static String url = "http://127.0.0.1:9095/";

	private static IO.Options optionsFm;

	public static void main(String[] args) throws Exception {

		long clientThreadNum = 100;
		if (args.length > 0) {
			clientThreadNum = Long.valueOf(args[0]);
			System.out.println("客户端建立链接数:" + clientThreadNum);
		}

		if (args.length > 1) {
			url = args[1];
			System.out.println("远程连接地址:" + args[1]);
		}

		optionsFm = new IO.Options();
		optionsFm.transports = new String[] { "websocket" };
		optionsFm.reconnectionAttempts = 2; // 重连尝试次数
		optionsFm.reconnectionDelay = 1000; // 失败重连的时间间隔(ms)
		optionsFm.timeout = 20000; // 连接超时时间(ms)
		optionsFm.forceNew = true;

		ExecutorService executorService = new ThreadPoolExecutor(50, 1000, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(500));
		// 每条线程处理链接数
		long singeDelNum = 100;
		while (clientThreadNum > 0) {
			try {
				Long delNum = clientThreadNum > singeDelNum ? singeDelNum : clientThreadNum;
				executorService.submit(new DealThread(delNum));
			}
			catch (Exception e) {
				Thread.sleep(5000);
				continue;
			}
			clientThreadNum -= singeDelNum;
		}

		// 停止线程池
		executorService.shutdown();
	}

	public static class DealThread implements Runnable {

		private long dealNum;

		public DealThread(long dealNum) {
			this.dealNum = dealNum;
		}

		@Override
		public void run() {
			int waitTime = 5;
			for (int i = 0; i < dealNum;) {
				try {
					Thread.sleep(waitTime);
					Socket socketFm = IO.socket(url + projectIdFm, optionsFm);
					HashMap<String, String> authData = new HashMap<>(2);
					authData.put("projectId", projectIdFm);
					authData.put("token", token);
					SocketIOJavaClientCommon socketIOJavaClientCommon = new SocketIOJavaClientCommon(socketFm, authData,
							subscribes);
					socketIOJavaClientCommon.start();
				}
				catch (Exception e) {
					waitTime += 3000;
					System.out.println("connect fail, reconnect sleep 10s");
					continue;
				}

				// 走到后面说明连接成功,置回正常等待时间,处理下一位
				if (waitTime > 3000) {
					waitTime = 1;
				}
				i++;
			}

			System.out.println("thread die, execution connect num:" + dealNum);
		}

	}

}
