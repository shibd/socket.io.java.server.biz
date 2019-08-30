package com.dfocus.pmsg;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1000)
@Component
@SpringBootApplication
public class MsgcenterApplication implements CommandLineRunner {

	@Autowired
	private SocketIOServer server;

	public static void main(String[] args) {
		SpringApplication.run(MsgcenterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("ServerRunner 开始启动啦...");
		server.start();
	}

}