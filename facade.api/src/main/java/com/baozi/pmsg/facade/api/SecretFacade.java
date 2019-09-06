package com.baozi.pmsg.facade.api;

import com.baozi.mint.web.rsp.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@RequestMapping("/facade/secret")
public interface SecretFacade {

	/**
	 * 查询项目公钥
	 * @param projectId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{projectId}")
    Response<String> getPublicKey(@PathVariable String projectId);

	/**
	 * 新增或更新公钥
	 * @param projectId
	 * @param publicKey
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/save/{projectId}")
	Response<Boolean> saveOrUpdateKey(@PathVariable String projectId, @RequestParam String publicKey);

}
