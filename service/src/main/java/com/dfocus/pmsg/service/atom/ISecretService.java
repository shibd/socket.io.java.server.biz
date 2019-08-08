package com.dfocus.pmsg.service.atom;

import java.util.Map;

/**
 * @auther: baozi
 * @date: 2019/8/5 17:33
 * @description:
 */
public interface ISecretService {

	/**
	 * @param projectId
	 * @return
	 *
	 */
	String selectPublicKeyByProjectId(String projectId);

	/**
	 * @return
	 *
	 */
	Map<String, String> selectPublicKeys();

	/**
	 * @param projectId
	 * @param publicKey
	 * @return
	 *
	 */
	boolean saveOrUpdatePublicKey(String projectId, String publicKey);

}
