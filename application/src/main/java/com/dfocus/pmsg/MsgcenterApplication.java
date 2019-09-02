package com.dfocus.pmsg;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
public class MsgcenterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(MsgcenterApplication.class, args);

		SocketIOServer socketIOServer = run.getBean("socketIOServer", SocketIOServer.class);
		run.addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> {
            System.out.println("优雅停机SocketIOServer");
            socketIOServer.stop();
        });
	}

}