package com.dfocus.pmsg.service;

import com.dfocus.pmsg.MsgcenterApplicationTests;
import com.dfocus.pmsg.service.atom.ISecretService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author: baozi
 * @date: 2019/8/6 18:41
 * @description:
 */
public class IProjectKeyServiceTest extends MsgcenterApplicationTests {

	@Autowired
	ISecretService iProjectKeyService;

	@Test
	public void testSelectKeys() {
		Map<String, String> stringStringMap = iProjectKeyService.selectPublicKeys();
		Assert.assertTrue(stringStringMap.size() == 2);
	}

	@Test
	public void testSelectKey() {
		String publicKey = iProjectKeyService.selectPublicKeyByProjectId("dm");
		Assert.assertTrue(!StringUtils.isEmpty(publicKey));
	}

}
