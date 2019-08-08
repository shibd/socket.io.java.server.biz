package com.dfocus.pmsg.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: baozi
 * @date: 2019/8/6 10:30
 * @description:
 */
@Data
public class WsUserSessionVo {

	private String userId;

	private List<WsSessionVo> sessions;

	@Data
	public static class WsSessionVo {

		String sessionId;

		String remoteUrl;

		List<String> subscriptions = new ArrayList<>();

		public void addSubscription(String subscription) {
			subscriptions.add(subscription);
		}

	}

}
