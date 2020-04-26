package com.zjft.microservice.treasurybrain.pushserver.component;

import com.alibaba.fastjson.JSONArray;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.pushserver.po.PushServerInfoPO;
import com.zjft.microservice.treasurybrain.pushserver.repository.PushServerInfoMapper;
import com.zjft.microservice.treasurybrain.pushserver.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@ZjComponentResource(group = "push")
public class SendInfoComponent {

	@Resource
	private PushServerInfoMapper pushServerInfoMapper;
	/**
	 * 推送消息给用户
	 */
	@ZjComponentMapping("sendInfo2User")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String sendInfo2User(HashMap requestHashMap, DTO returnDTO, DTO temp) {
		String message = StringUtil.parseString(requestHashMap.get("message"));
		String userName = StringUtil.parseString(requestHashMap.get("userName"));
		WebSocketServer.sendInfo2User(message, userName);
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}


	/**
	 * 推送消息给角色
	 */
	@ZjComponentMapping("sendInfo2Roles")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String sendInfo2Roles(HashMap requestHashMap, DTO returnDTO, DTO temp) {
		String message = StringUtil.parseString(requestHashMap.get("message"));
		String roles = StringUtil.parseString(requestHashMap.get("roles"));
		String[] split = roles.split(",");
		List<Object> listStrings = Stream.of(split).collect(Collectors.toList());
		WebSocketServer.sendInfo2Roles(message, new JSONArray(listStrings));
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}


	/**
	 * 推送消息给所有人
	 */
	@ZjComponentMapping("sendInfo2All")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String sendInfo2All(String message, DTO returnDTO, DTO temp) {
		PushServerInfoPO pushServerInfoPO = new PushServerInfoPO();
		pushServerInfoPO.setNo(java.util.UUID.randomUUID().toString().replace("-", ""));
		pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
		pushServerInfoPO.setName("所有人");
		pushServerInfoPO.setMessage(message);
		int x = pushServerInfoMapper.insert(pushServerInfoPO);
		if (x != 1) {
			log.error("插入线路排班信息失败！");
			returnDTO.setResult(RetCodeEnum.FAIL);
		}
		WebSocketServer.sendInfo2User(message, null);
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}
}
