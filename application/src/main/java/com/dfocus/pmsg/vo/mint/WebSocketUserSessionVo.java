package com.dfocus.pmsg.vo.mint;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: baozi
 * @date: 2019/8/6 10:30
 * @description:
 */
@Data
public class WebSocketUserSessionVo {

	private String userId;

	private List<WebSocketUserSessionVo.WebSocketSessionVo> sessions;

	@Data
	public static class WebSocketSessionVo {

		String sessionId;

		List<String> subscriptions = new ArrayList<>();

		public void addSubscription(String subscription) {
			subscriptions.add(subscription);
		}

	}

}
