package com.baozi.pmsg.controller;

import com.baozi.mint.web.rsp.Response;
import com.baozi.pmsg.service.atom.ISecretService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
	SocketIOServer server;

	@Autowired
	ISecretService secretService;

	@ApiOperation("接口: 获取会话列表")
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	Response<List<Map<String, Object>>> getSessions() {

		List<Map<String, Object>> list = new ArrayList<>();

		Map<String, String> projectMaps = secretService.selectPublicKeys();
		for (String projectId : projectMaps.keySet()) {
			SocketIONamespace namespace = server.getNamespace("/" + projectId);
			Collection<SocketIOClient> clients = namespace.getAllClients();
			for (SocketIOClient client : clients) {
				Map<String, Object> map = new HashMap<>();
				map.put("allRomms", client.getAllRooms());
				map.put("namespace", client.getNamespace().getName());
				map.put("sessionId", client.getSessionId());
				map.put("remoteAddress", client.getRemoteAddress());
				list.add(map);
			}

		}
		return Response.success(list);
	}

	@ApiOperation("接口: 获取所有会话数目")
	@RequestMapping(method = RequestMethod.GET, value = "/size")
	Response<Integer> getSessionNumber() {
		return Response.success(server.getAllClients().size());
	}

}
