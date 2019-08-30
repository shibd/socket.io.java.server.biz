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
public class WsMessage {

	/**
	 * 项目ID
	 */
	@ApiModelProperty(value = "项目ID", example = "/fm")
	private String projectId;

	/**
	 * 主题
	 */
	@ApiModelProperty(value = "订阅主题", example = "group_1")
	private String topic;

	/**
	 * 订阅事件
	 */
	@ApiModelProperty(value = "订阅事件", example = "event_1")
	private String event;

	/**
	 * 内容 <br>
	 * 和客户端约定好的json数据
	 */
	@ApiModelProperty(value = "内容", example = "{\"content\":\"解析我,做你想做的事情\"}")
	private String playLoad;

}
