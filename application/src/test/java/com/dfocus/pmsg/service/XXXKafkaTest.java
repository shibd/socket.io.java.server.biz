package com.dfocus.pmsg.service;

import com.dfocus.pmsg.MsgcenterApplicationTests;
import com.dfocus.pmsg.service.kafka.XXXReceiver;
import com.dfocus.pmsg.service.kafka.XXXSender;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

/**
 * @Author: baozi
 * @Date: 2019/7/9 17:24
 * @Description:
 */
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = "${xxx.topic}")
@TestPropertySource(properties = { "kafka.enable=true" })
public class XXXKafkaTest extends MsgcenterApplicationTests {

	@Autowired
	private XXXReceiver receiver;

	@Autowired
	private XXXSender sender;

	// @Test
	// public void testReceive() throws Exception {
	// sender.send("Hello Spring Kafka!");
	//
	// receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	// Assert.assertEquals(receiver.getLatch().getCount(), 0);
	// }

	/**
	 * @throws Exception
	 */
	@Test
	public void mockTestReceive() throws Exception {
		receiver.receive("Hello Mock Test");
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		Assert.assertEquals(receiver.getLatch().getCount(), 0);
	}

}
