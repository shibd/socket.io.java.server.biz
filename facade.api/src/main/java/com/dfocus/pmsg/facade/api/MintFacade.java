package com.dfocus.pmsg.facade.api;

import com.dfocus.pmsg.facade.model.MintFaReq;
import com.dfocus.mint.web.rsp.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@RequestMapping("/facade/mint")
public interface MintFacade {

	@RequestMapping(method = RequestMethod.POST)
	Response<List<MintFaReq>> testMint(@RequestBody MintFaReq mintFaReq);

}
