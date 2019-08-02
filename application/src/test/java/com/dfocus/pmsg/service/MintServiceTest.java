package com.dfocus.pmsg.service;

import com.dfocus.pmsg.MsgcenterApplicationTests;
import com.dfocus.pmsg.service.atom.IMintService;
import com.dfocus.pmsg.service.dto.mint.SearchMint;
import com.dfocus.pmsg.service.dto.mint.MintDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/27 11:29
 * @Description:
 */
public class MintServiceTest extends MsgcenterApplicationTests {

	@Autowired
	IMintService mintService;

	@Test
	public void testSearch() {
		SearchMint searchMint = new SearchMint();
		searchMint.setAccount("test1");
		searchMint.setNickName("test2");
		List<MintDto> mints = mintService.getMints(searchMint);
		Assert.assertTrue(mints.size() == 2);
	}

}
