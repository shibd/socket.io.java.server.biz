package com.dfocus.pmsg.service;

import com.dfocus.pmsg.MsgcenterApplicationTests;
import com.dfocus.pmsg.facade.model.WsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

/**
 * @author: baozi
 * @date: 2019/8/8 15:47
 * @description:
 */
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = "${ws.topic}")
@TestPropertySource(properties = { "kafka.enable=true" })
public class WsMessageReceiver extends MsgcenterApplicationTests {

	@Value("${ws.topic}")
	private String wsTopic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testReceive() throws JsonProcessingException {

		WsMessage.WsTopicMessage wsTopicMessage = new WsMessage.WsTopicMessage("fm", "jiagouzu", "我是测试");

		kafkaTemplate.send(wsTopic, wsTopicMessage.getClass().getName(),
				objectMapper.writeValueAsString(wsTopicMessage));
	}

}
