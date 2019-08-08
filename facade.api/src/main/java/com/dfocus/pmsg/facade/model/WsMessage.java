package com.dfocus.pmsg.facade.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: baozi
 * @date: 2019/8/8 14:35
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class WsMessage {

	/**
	 * 项目ID
	 */
	@ApiModelProperty(value = "项目ID", example = "fm")
	private String projectId;

	/**
	 * 具体内容{json格式} 和前端约定好的json数据
	 */
	@ApiModelProperty(value = "和前端约定好的json数据", example = "{\"content\":\"解析我,做你想做的事情\"}")
	private String playLoad;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", example = "1557676800000")
	private long updateTime;

	@Data
	@NoArgsConstructor
	public static class WsUserMessage extends WsMessage {

		/**
		 * 用户
		 */
		@ApiModelProperty(value = "用户id", example = "wudixiaobaozi")
		private String user;

		public WsUserMessage(String projectId, String user, String playLoad) {
			super(projectId, playLoad, System.currentTimeMillis());
			this.user = user;
		}

	}

	@Data
	@NoArgsConstructor
	public static class WsTopicMessage extends WsMessage {

		/**
		 * 主题
		 */
		@ApiModelProperty(value = "订阅主题", example = "all/group1/")
		private String topic;

		public WsTopicMessage(String projectId, String topic, String playLoad) {
			super(projectId, playLoad, System.currentTimeMillis());
			this.topic = topic;
		}

	}

}
