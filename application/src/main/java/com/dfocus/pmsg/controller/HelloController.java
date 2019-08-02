package com.dfocus.pmsg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@Api("接口: 测试")
@RestController
@RequestMapping("/hello")
public class HelloController {

	// @Autowired
	// private MintFeign mintFeign;

	@ApiOperation("接口: 测试接口")
	@RequestMapping(method = RequestMethod.GET)
	public String hello() {

		// 模拟调用远程服务,建议再service层调用
		// MintFaReq mintFaReq = new MintFaReq();
		// mintFaReq.setAccount("baozi");
		// mintFaReq.setNickName("baozinick");
		// Response<List<MintFaReq>> response = mintFeign.testMint(mintFaReq);
		// if (response.success()) {
		// List<MintFaReq> data = response.getData();
		// for (MintFaReq datum : data) {
		// System.out.println(datum.getAccount());
		// }
		// } else {
		// throw new BizException(response.getStatus(), response.getMessage());
		// }

		return "hello world";
	}

}
