package com.dfocus.pmsg.controller;

import com.dfocus.pmsg.facade.web.rsp.Response;
import com.dfocus.pmsg.service.atom.ISecretService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@Api("接口: 公钥管理")
@RestController
@RequestMapping("/secret")
public class SecretController {

	@Autowired
	ISecretService projectKeyService;

	@ApiOperation("接口: 查询所有公钥")
	@RequestMapping(method = RequestMethod.GET, value = "/public_keys")
	Response<Map<String, String>> getPublicKeys() {
		return Response.success(projectKeyService.selectPublicKeys());
	}

}
