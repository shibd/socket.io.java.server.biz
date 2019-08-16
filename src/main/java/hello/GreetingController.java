package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

/**
 * @author baozi
 */
@Controller
public class GreetingController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	public void greeting(@RequestBody HelloMessage message) throws Exception {
		// simulated delay
		Thread.sleep(1000);

		// 发送消息到所有订阅topic/greetings的客户端
		messagingTemplate.convertAndSend("/topic/greetings",
				new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!"));

	}

}
