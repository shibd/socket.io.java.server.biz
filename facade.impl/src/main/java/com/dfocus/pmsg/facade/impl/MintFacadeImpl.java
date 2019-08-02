package com.dfocus.pmsg.facade.impl;

import com.dfocus.pmsg.facade.api.MintFacade;
import com.dfocus.pmsg.facade.model.MintFaReq;
import com.dfocus.pmsg.facade.transfer.MintFaTransfer;
import com.dfocus.pmsg.service.atom.IMintService;
import com.dfocus.pmsg.service.dto.mint.MintDto;
import com.dfocus.pmsg.service.common.exception.BizException;
import com.dfocus.pmsg.service.common.constant.ResultCode;
import com.dfocus.mint.web.rsp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/25 11:32
 * @Description:
 */
@RestController
public class MintFacadeImpl implements MintFacade {

	@Autowired
	IMintService mintService;

	@Override
	public Response<List<MintFaReq>> testMint(@RequestBody MintFaReq mintFaReq) {
		MintFaReq mintFaReq1 = new MintFaReq();
		mintFaReq1.setAccount("lulu1");
		mintFaReq1.setNickName("lulunickname1");

		MintFaReq mintFaReq2 = new MintFaReq();
		mintFaReq2.setAccount("lulu2");
		mintFaReq2.setNickName("lulunickname2");

		List<MintFaReq> mintFaReqs = new ArrayList<>();
		mintFaReqs.add(mintFaReq);
		mintFaReqs.add(mintFaReq1);
		mintFaReqs.add(mintFaReq2);

		// 有问题可以直接抛出异常
		// throw new BizException(ResultCode.SYS_ERROR);
		// 可以直接返回
		return Response.success(mintFaReqs);
	}

}
