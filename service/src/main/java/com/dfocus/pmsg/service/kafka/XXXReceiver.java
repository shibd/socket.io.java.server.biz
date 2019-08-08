package com.dfocus.pmsg.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: baozi
 * @Date: 2019/7/9 17:01
 * @Description:
 */
@Slf4j
@Component
// todo 脚手架默认生成了下面开关,为了满足应用环境期初没有kafka,以及测试环境需要模拟kafka演示<>当你的应用连接kafka正常后,应去掉该注释!!
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true")
public class XXXReceiver {

	private CountDownLatch latch = new CountDownLatch(1);

	public CountDownLatch getLatch() {
		return latch;
	}

	@KafkaListener(groupId = "${xxx.group}", topics = "${xxx.topic}")
	public void receive(String payload) {
		log.info("received payload='{}'", payload);
		latch.countDown();
	}

}
