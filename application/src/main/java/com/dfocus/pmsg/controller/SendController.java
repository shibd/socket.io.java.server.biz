package com.dfocus.pmsg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

/**
 * @Auther: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@Slf4j
@Api("接口: 发送消息")
@RestController
@RequestMapping("/hello")
public class SendController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@ApiOperation("接口: 发送广播消息")
	@GetMapping("/send/{project}/{group}")
	public void sendMessage(@PathVariable String project, @PathVariable String group, @RequestParam String message)
			throws Exception {

		Thread.sleep(500);

		log.info("project:{}|group:{}|message:{}", project, group, message);

		String destination = String.format("/topic/%s/%s/", project, group);

		// 发送消息
		messagingTemplate.convertAndSend(destination, new Greeting("Hello, " + HtmlUtils.htmlEscape(message + "!")));
	}

	@ApiOperation("接口: 发送单用户消息,也可以包装成多个用户")
	@GetMapping("/sendToUser/{project}/{user}")
	public void sendMessageToUser(@PathVariable String project, @PathVariable String user, @RequestParam String message)
			throws Exception {

		Thread.sleep(500);

		log.info("project:{}|user:{}|message:{}", project, user, message);

		String destination = String.format("/queue/%s/", project);

		// 发送消息
		messagingTemplate.convertAndSendToUser(user, destination,
				new Greeting("Hello, " + HtmlUtils.htmlEscape(message + "!")));
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Greeting {

		private String content;

	}

}
