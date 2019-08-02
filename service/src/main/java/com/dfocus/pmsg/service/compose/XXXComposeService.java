package com.dfocus.pmsg.service.compose;

import com.dfocus.pmsg.service.dto.mint.SearchMint;
import com.dfocus.pmsg.service.dto.mint.MintDto;

import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/26 18:34
 * @Description:
 */
public interface XXXComposeService {

	/**
	 * 模拟调用用户原子查询服务
	 * @param searchMint
	 * @return
	 */
	List<MintDto> testComposeSearchMint(SearchMint searchMint);

	/**
	 * 模拟调用用户原子服务和发送事件
	 * @param searchMint
	 * @return
	 */
	List<MintDto> testComposeSearchMintAndSendEvent(SearchMint searchMint);

}
