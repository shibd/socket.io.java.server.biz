package com.dfocus.pmsg.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: baozi
 * @date: 2019/8/6 10:30
 * @description: 项目 --> 用户 --> session
 */
@Data
public class WsProjectSessionVo {

	/**
	 * 项目Id
	 */
	private String projectId;

	/**
	 * 用户session集合
	 */
	private List<WsUserSessionVo> wsUserSessionVos = new ArrayList<>();

	public void addWsUserSession(WsUserSessionVo wsUserSessionVo) {
		wsUserSessionVos.add(wsUserSessionVo);
	}

	/**
	 * 用户的session
	 */
	@Data
	public static class WsUserSessionVo {

		/**
		 * 用户Id
		 */
		private String userId;

		/**
		 * session集合
		 */
		private List<WsSessionVo> sessions = new ArrayList<>();

		public void addWsSession(WsSessionVo wsSessionVo) {
			sessions.add(wsSessionVo);
		}

	}

	/**
	 * session详情
	 */
	@Data
	public static class WsSessionVo {

		/**
		 * 会话Id
		 */
		private String sessionId;

		/**
		 * 客户端地址
		 */
		private String remoteUrl;

		/**
		 * 订阅主题详情
		 */
		private List<String> subscriptions = new ArrayList<>();

		public void addSubscription(String subscription) {
			subscriptions.add(subscription);
		}

	}

}
