package com.dfocus.pmsg.service.atom.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dfocus.pmsg.common.dao.SecretMapper;
import com.dfocus.pmsg.common.entity.Secret;
import com.dfocus.pmsg.service.atom.ISecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: baozi
 * @date: 2019/8/5 17:34
 * @description:
 */
@Service
public class SecretServiceImpl implements ISecretService {

	@Autowired
	private SecretMapper secretMapper;

	@Override
	public String selectPublicKeyByProjectId(String projectId) {
		LambdaQueryWrapper<Secret> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Secret::getProjectId, projectId);
		Secret secret = secretMapper.selectOne(wrapper);
		return secret == null ? "" : secret.getPublicKey();
	}

	@Override
	public Map<String, String> selectPublicKeys() {
		List<Secret> secrets = secretMapper.selectList(Wrappers.emptyWrapper());
		Map<String, String> map = new HashMap<>();
		for (Secret secret : secrets) {
			map.put(secret.getProjectId(), secret.getPublicKey());
		}
		return map;
	}

	@Override
	public boolean saveOrUpdatePublicKey(String projectId, String publicKey) {
		Secret secret = new Secret();
		secret.setProjectId(projectId);
		secret.setPublicKey(publicKey);
		return secret.insertOrUpdate();
	}

}
