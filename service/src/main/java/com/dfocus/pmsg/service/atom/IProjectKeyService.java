package com.dfocus.pmsg.service.atom;

import java.util.Map;

/**
 * @auther: baozi
 * @date: 2019/8/5 17:33
 * @description:
 */
public interface IProjectKeyService {

	String getPublicKey(String projectId);

	Map<String, String> getPublicKeys();

}
