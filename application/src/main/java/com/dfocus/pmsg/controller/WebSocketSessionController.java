package com.dfocus.pmsg.controller;

import com.dfocus.mint.web.rsp.Response;
import com.dfocus.pmsg.service.atom.ISessionService;
import com.dfocus.pmsg.vo.mint.WebSocketUserSessionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@Api("接口: 会话管理")
@RestController
public class WebSocketSessionController {

	@Autowired
	SimpUserRegistry userRegistry;

	@Autowired
	ISessionService iSessionService;

	@ApiOperation("接口: 获取会话列表")
	@RequestMapping(method = RequestMethod.GET, value = "/sessions")
	Response<Object> getSessions() {

		List<WebSocketUserSessionVo> webSocketUserSessionVos = new ArrayList<>();
		Set<SimpUser> users = userRegistry.getUsers();
		for (SimpUser user : users) {

			WebSocketUserSessionVo webSocketUserSession = new WebSocketUserSessionVo();
			List<WebSocketUserSessionVo.WebSocketSessionVo> webSocketSessionVos = new ArrayList<>();
			webSocketUserSession.setUserId(user.getName());
			webSocketUserSession.setSessions(webSocketSessionVos);

			for (SimpSession simpSession : user.getSessions()) {
				WebSocketUserSessionVo.WebSocketSessionVo webSocketSessionVo = new WebSocketUserSessionVo.WebSocketSessionVo();
				webSocketSessionVo.setSessionId(simpSession.getId());
				webSocketSessionVo.setRemoteUrl(iSessionService.getRemoteUrlBySession(simpSession.getId()));
				for (SimpSubscription simpSubscription : simpSession.getSubscriptions()) {
					webSocketSessionVo.addSubscription(simpSubscription.getDestination());
				}
				webSocketSessionVos.add(webSocketSessionVo);
			}

			webSocketUserSessionVos.add(webSocketUserSession);
		}

		return Response.success(webSocketUserSessionVos);
	}

	@ApiOperation("接口: 获取自己维护的session")
	@RequestMapping(method = RequestMethod.GET, value = "/my_sessions")
	Response<Object> getMySessions() {
		return Response.success(iSessionService.getSessions());
	}

}
