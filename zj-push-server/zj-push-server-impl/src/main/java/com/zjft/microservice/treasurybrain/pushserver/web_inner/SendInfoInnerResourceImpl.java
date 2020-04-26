package com.zjft.microservice.treasurybrain.pushserver.web_inner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.pushserver.po.PushServerInfoPO;
import com.zjft.microservice.treasurybrain.pushserver.repository.PushServerInfoMapper;
import com.zjft.microservice.treasurybrain.pushserver.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 消息推送
 * websocket 在线测试工具：http://easyswoole.com/wstool.html
 * 服务地址示例：ws://localhost:8080/push-server/v2/websocket?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjEwMTk3OTIsInVzZXJfbmFtZSI6InFpYW5nc3VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV8xMDAwMSJdLCJqdGkiOiI2YWZkNWY0MC02ZmVhLTRiNDktODVjYy05YjU5Y2I4ZmJmNmIiLCJjbGllbnRfaWQiOiJteS1jbGllbnQtMSIsInNjb3BlIjpbImFsbCJdfQ.BLYC1HBrrRvK5RzYY5jWhUyY7FGML3-_XfAQfYM3BAw
 *
 * @author 韩通
 * @since 2020-01-07
 */
@RestController
@Slf4j
public class SendInfoInnerResourceImpl implements SendInfoInnerResource {

	@Resource
	private PushServerInfoMapper pushServerInfoMapper;

	@Override
	public DTO sendInfo2User(String username, String message) {
		WebSocketServer.sendInfo2User(message, username);
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO sendInfo2Roles(String roles, String message) {
		String[] split = roles.split(",");
		List<Object> listStrings = Stream.of(split).collect(Collectors.toList());
		WebSocketServer.sendInfo2Roles(message, new JSONArray(listStrings));
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO sendInfo2All(String message) {
		JSONObject jsonObject = JSONUtil.parseJSONObject(message);
		String type = jsonObject.getString("type");
		//此处需在调用websocket的地方都加一个title，再释放
		//String title = jsonObject.getString("title");
		log.info("============" + type);
		PushServerInfoPO pushServerInfoPO = new PushServerInfoPO();
		pushServerInfoPO.setNo(java.util.UUID.randomUUID().toString().replace("-", ""));
		pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
		pushServerInfoPO.setName("所有人");
		pushServerInfoPO.setMessage(message);
		pushServerInfoPO.setNoticeWay(1);
		pushServerInfoPO.setNoticeCategory(type);
		pushServerInfoPO.setNoticeCategoryDescription(type);
		pushServerInfoPO.setNoticeTitle(type);//先用type代替
		pushServerInfoPO.setNoticeFlag(1);
		int x = pushServerInfoMapper.insert(pushServerInfoPO);
		if (x != 1) {
			log.error("插入线路排班信息失败！");
			return new DTO(RetCodeEnum.FAIL);
		}
		WebSocketServer.sendInfo2User(message, null);
		return new DTO(RetCodeEnum.SUCCEED);
	}
}
