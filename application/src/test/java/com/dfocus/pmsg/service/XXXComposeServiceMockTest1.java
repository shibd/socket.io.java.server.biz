package com.dfocus.pmsg.service;

import com.dfocus.pmsg.MsgcenterApplicationTests;
import com.dfocus.pmsg.service.atom.IMintService;
import com.dfocus.pmsg.service.compose.XXXComposeService;
import com.dfocus.pmsg.service.compose.impl.XXXComposeServiceImpl;
import com.dfocus.pmsg.service.dto.mint.SearchMint;
import com.dfocus.pmsg.service.dto.mint.MintDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/7/11 09:49
 * @Description: 第一种测试,没有结合spring容器
 */
public class XXXComposeServiceMockTest1 extends MsgcenterApplicationTests {

	@InjectMocks
	XXXComposeService xxxComposeService = new XXXComposeServiceImpl();

	@Mock
	IMintService mintService;

	// mock搜索条件
	SearchMint searchMint = new SearchMint();

	@Before
	public void befor() {
		// mock
		searchMint.setAccount("test1");
		searchMint.setNickName("test2");

		MintDto mintDto = new MintDto();
		mintDto.setAccount("mock1-account");
		mintDto.setNickName("mock1-nickName");

		Mockito.when(mintService.getMints(searchMint)).thenReturn(Arrays.asList(mintDto));
	}

	@Test
	public void testXXXMockTest() {
		List<MintDto> mints = xxxComposeService.testComposeSearchMint(searchMint);
		System.out.println(mints);
		Assert.assertTrue(mints.size() == 1);
		Assert.assertTrue(mints.get(0).getAccount().equals("mock1-account"));
	}

}
