package com.dfocus.pmsg.service.atom.impl;

import com.dfocus.pmsg.service.atom.IProjectKeyService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther: baozi
 * @date: 2019/8/5 17:34
 * @description:
 */
@Service
public class ProjectKeyServiceImpl implements IProjectKeyService {

	/**
	 * key: projectId, value: publicKey
	 */
	private static Map<String, String> publicKeys = new ConcurrentHashMap<>();

	static {
		publicKeys.put("dm",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ60Tn13Gw3jAeNAGvl/yZEarko9oV33jAG5IgK86GzBJKsA23Ru+v2LUkcejXpu/cqsIVDajYPpSnzETKaxEQ5v/v3l9U792Axc6V2WstJneYWcVECjalg6Gwne1PLcCkQ3yMNQXyAbT4nS90Spjl5SbnaGvink4SEUJU9gx6xQIDAQAB");
		publicKeys.put("fm",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVpgJ8BKV0ZDTHcNNH0bDkUDRqxW3wdHFezJ6SO6YukA0iEHrGAGWbuDUFUCWVW4dAnTFcfvjJiavzqhHIBA3uNDEwe33yxXiFdrFG5t3MpdBoqkKeVPokgrVwahhyY96bVaXRc3RYOizSxJ62LmBnSnmkbgQk9D2O+yMkljjkqwIDAQAB");
	}

	@Override
	public String getPublicKey(String projectId) {
		return publicKeys.get(projectId);
	}

	@Override
	public Map<String, String> getPublicKeys() {
		return publicKeys;
	}

}
