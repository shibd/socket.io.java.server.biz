package com.baozi.pmsg;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @Author: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@SpringBootApplication
public class MsgCenterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(MsgCenterApplication.class, args);

		SocketIOServer socketIOServer = run.getBean("socketIOServer", SocketIOServer.class);
		run.addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> {
			System.out.println("优雅停机SocketIOServer");
			socketIOServer.stop();
		});
	}

}
