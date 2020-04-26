package com.zjft.microservice.treasurybrain.pushserver.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * websocket 在线测试工具：http://easyswoole.com/wstool.html
 * 服务地址示例：ws://localhost:8080/push-server/v2/websocket?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjEwMTk3OTIsInVzZXJfbmFtZSI6InFpYW5nc3VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV8xMDAwMSJdLCJqdGkiOiI2YWZkNWY0MC02ZmVhLTRiNDktODVjYy05YjU5Y2I4ZmJmNmIiLCJjbGllbnRfaWQiOiJteS1jbGllbnQtMSIsInNjb3BlIjpbImFsbCJdfQ.BLYC1HBrrRvK5RzYY5jWhUyY7FGML3-_XfAQfYM3BAw
 *
 * @author 韩通
 */
@Api(value = "推送中心：推送消息", tags = "推送中心：推送消息")
public interface SendInfoFlowResource {

	String PREFIX = "/push-server/v2/info";

	@GetMapping(PREFIX + "/user")
	@ApiOperation(value = "推送消息给某用户", notes = "推送消息给某用户")
	@ZjWorkFlow("sendUser")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户编号" , required = true, paramType = "query"),
			@ApiImplicitParam(name = "message", value = "推送信息", required = true , paramType = "query")
	})
	DTO sendInfo2User(@ApiIgnore @RequestParam HashMap hashMap);

	@GetMapping(PREFIX + "/role")
	@ApiOperation(value = "推送消息给某角色", notes = "推送消息给某角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roles", value = "角色编号" , required = true, paramType = "query"),
			@ApiImplicitParam(name = "message", value = "推送信息", required = true , paramType = "query")
	})
	@ZjWorkFlow("sendRoles")
	DTO sendInfo2Roles(@ApiIgnore @RequestParam HashMap hashMap);

	@GetMapping(PREFIX + "/all")
	@ApiOperation(value = "推送消息给所有人", notes = "推送消息给所有人")
	@ZjWorkFlow("sendAll")
	DTO sendInfo2All(@RequestParam("message") String message);
}
