package com.dfocus.pmsg.service.compose.impl;

import com.dfocus.pmsg.service.atom.IMintService;
import com.dfocus.pmsg.service.compose.XXXComposeService;
import com.dfocus.pmsg.service.dto.mint.SearchMint;
import com.dfocus.pmsg.service.dto.mint.MintDto;
import com.dfocus.pmsg.service.kafka.XXXSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/26 18:35
 * @Description:
 */
@Service
public class XXXComposeServiceImpl implements XXXComposeService {

	@Autowired
	IMintService mintService;

	@Autowired
	XXXSender xxxSender;

	@Override
	public List<MintDto> testComposeSearchMint(SearchMint searchMint) {
		return mintService.getMints(searchMint);
	}

	@Override
	public List<MintDto> testComposeSearchMintAndSendEvent(SearchMint searchMint) {
		xxxSender.send(searchMint.getAccount());
		return mintService.getMints(searchMint);
	}

}
