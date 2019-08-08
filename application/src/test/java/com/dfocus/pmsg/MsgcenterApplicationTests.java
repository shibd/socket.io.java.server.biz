package com.dfocus.pmsg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: baozi
 * @Date: 2019/6/14 16:00
 * @Description:
 */
@TestPropertySource(properties = { "app.scheduling.enable=false" })
@RunWith(SpringRunner.class)
@SpringBootTest
public class MsgcenterApplicationTests {

	@Test
	public void test() {
	}

}
