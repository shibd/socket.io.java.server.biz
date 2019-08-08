package com.dfocus.pmsg.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: baozi
 * @Date: 2019/7/9 18:27
 * @Description:
 */
@Slf4j
@Component
public class XXXSender {

	@Value("${xxx.topic}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String payload) {
		log.info("sending payload='{}'", payload);
		kafkaTemplate.send(topic, payload);
	}

}
