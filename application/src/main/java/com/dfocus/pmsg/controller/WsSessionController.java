package com.dfocus.pmsg.controller;

import com.dfocus.mint.web.rsp.Response;
import com.dfocus.pmsg.service.atom.ISessionService;
import com.dfocus.pmsg.service.dto.WsSessionDto;
import com.dfocus.pmsg.vo.WsProjectSessionVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@Api("接口: 会话管理")
@RestController
@RequestMapping("/ws_session")
public class WsSessionController {

	@Autowired
	SimpUserRegistry userRegistry;

	@Autowired
	ISessionService iSessionService;

	@ApiOperation("接口: 获取会话列表")
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	Response<List<WsProjectSessionVo>> getSessions() {

		// 预处理 <projectId, <user, userSession>>
		Map<String, Map<String, WsProjectSessionVo.WsUserSessionVo>> wsProjectSessionMaps = new HashMap<>();
		for (SimpUser user : userRegistry.getUsers()) {

			for (SimpSession simpSession : user.getSessions()) {

				// session预处理
				WsSessionDto session = iSessionService.getSessionById(simpSession.getId());
				WsProjectSessionVo.WsSessionVo wsSessionVo = new WsProjectSessionVo.WsSessionVo();
				wsSessionVo.setSessionId(simpSession.getId());
				wsSessionVo.setRemoteUrl(session.getRemoteUrl());
				for (SimpSubscription simpSubscription : simpSession.getSubscriptions()) {
					wsSessionVo.addSubscription(simpSubscription.getDestination());
				}

				// 用户的session预处理
				Map<String, WsProjectSessionVo.WsUserSessionVo> wsUserSessionVoMap = wsProjectSessionMaps
						.get(session.getProjectId());
				if (wsUserSessionVoMap == null) {
					wsUserSessionVoMap = new HashMap<>();
					wsProjectSessionMaps.put(session.getProjectId(), wsUserSessionVoMap);
				}
				WsProjectSessionVo.WsUserSessionVo wsUserSessionVo = wsUserSessionVoMap.get(user.getName());
				if (wsUserSessionVo == null) {
					wsUserSessionVo = new WsProjectSessionVo.WsUserSessionVo();
					wsUserSessionVoMap.put(user.getName(), wsUserSessionVo);
				}
				wsUserSessionVo.setUserId(user.getName());
				wsUserSessionVo.addWsSession(wsSessionVo);
			}
		}

		// 组装结果
		List<WsProjectSessionVo> wsProjectSessions = new ArrayList<>();
		wsProjectSessionMaps.forEach((key, wsUserSession) -> {
			WsProjectSessionVo wsProjectSessionVo = new WsProjectSessionVo();
			wsProjectSessionVo.setProjectId(key);
			wsProjectSessionVo.setWsUserSessionVos(new ArrayList<>(wsUserSession.values()));
			wsProjectSessions.add(wsProjectSessionVo);
		});

		return Response.success(wsProjectSessions);
	}

	@ApiOperation("接口: 获取自己定义的属性")
	@RequestMapping(method = RequestMethod.GET, value = "/my_define/list")
	Response<List<WsSessionDto>> getMyDefineSessions() {
		return Response.success(iSessionService.getSessions());
	}

	@ApiOperation("接口: 获取所有会话数目")
	@RequestMapping(method = RequestMethod.GET, value = "/my_define/size")
	Response<Integer> getSessionNumber() {
		return Response.success(iSessionService.getSessions().size());
	}

}
