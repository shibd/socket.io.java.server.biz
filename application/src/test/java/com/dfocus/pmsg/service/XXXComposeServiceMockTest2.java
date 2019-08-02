package com.dfocus.pmsg.service;

import com.dfocus.pmsg.MsgcenterApplicationTests;
import com.dfocus.pmsg.service.atom.IMintService;
import com.dfocus.pmsg.service.compose.XXXComposeService;
import com.dfocus.pmsg.service.dto.mint.SearchMint;
import com.dfocus.pmsg.service.dto.mint.MintDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/7/11 09:49
 * @Description: 第二种方式,结合spring容器
 */
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = "${xxx.topic}")
@TestPropertySource(properties = { "kafka.enable=true" })
public class XXXComposeServiceMockTest2 extends MsgcenterApplicationTests {

	@Autowired
	XXXComposeService xxxComposeService;

	@MockBean
	IMintService mintService;

	// mock搜索条件
	SearchMint searchMint = new SearchMint();

	@Before
	public void befor() {
		// mock
		searchMint.setAccount("test1");
		searchMint.setNickName("test2");

		MintDto mintDto = new MintDto();
		mintDto.setAccount("mock2-account");
		mintDto.setNickName("mock2-nickName");

		Mockito.when(mintService.getMints(searchMint)).thenReturn(Arrays.asList(mintDto));
	}

	@Test
	public void testXXXMockTest() {
		List<MintDto> mints = xxxComposeService.testComposeSearchMintAndSendEvent(searchMint);
		System.out.println(mints);
		Assert.assertTrue(mints.size() == 1);
		Assert.assertTrue(mints.get(0).getAccount().equals("mock2-account"));
	}

}
