package com.baozi.pmsg.runner;

import com.corundumstudio.socketio.SocketIOServer;
import com.baozi.pmsg.MsgcenterApplication;
import com.baozi.pmsg.config.SocketIoHandler;
import com.baozi.pmsg.service.atom.ISecretService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: baozi
 * @date: 2019/9/2 10:13
 * @description:
 */
@Slf4j
@Order(1000)
@Component
@ConditionalOnProperty(value = "socket-io.enable", havingValue = "true", matchIfMissing = true)
public class SocketIoRunner implements CommandLineRunner {

	@Autowired
	private SocketIOServer server;

	@Autowired
	private SocketIoHandler socketIoHandler;

	@Autowired
	private ISecretService iSecretService;

	public static void main(String[] args) {
		SpringApplication.run(MsgcenterApplication.class, args);
	}

	@Override
	public void run(String... args) {

		// 1. 开启socket-server的连接事件监听
		socketIoHandler.onConnect();
		socketIoHandler.onDisconnect();

		// 2. 查找所有项目-->对应namespace注册监听订阅事件
		Map<String, String> projectKeys = iSecretService.selectPublicKeys();
		log.info("init project namespace subscribe:{}", projectKeys.keySet());
		for (String projectId : projectKeys.keySet()) {
			socketIoHandler.onSubscribeEvent("/" + projectId);
			socketIoHandler.onAuthEvent("/" + projectId);
		}

		// 3. 启动服务
		log.info("ServerRunner 开始启动啦...");
		server.start();
	}

}
