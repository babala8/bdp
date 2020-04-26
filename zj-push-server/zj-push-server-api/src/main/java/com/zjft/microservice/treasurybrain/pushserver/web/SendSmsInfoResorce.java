package com.zjft.microservice.treasurybrain.pushserver.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushServerRequestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 常 健
 * @since 2020/2/24
 */
@Api(value = "推送中心：推送短信消息", tags = "推送中心：推送短信消息")
public interface SendSmsInfoResorce {

	String PREFIX = "/push-server/v2/sendSms";


	@GetMapping(PREFIX + "/user")
	@ApiOperation(value = "发送短信给某用户", notes = "发送短信给某用户")
	/*@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户编号" , required = true, paramType = "query"),
			@ApiImplicitParam(name = "message", value = "通知信息", required = true , paramType = "query"),
			@ApiImplicitParam(name = "noticeTitle", value = "通知标题" , required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别" , required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "类别描述" , required = true, paramType = "query")
	})*/
	DTO sendInfo2User(PushServerRequestDTO pushServerRequestDTO);

	@GetMapping(PREFIX + "/role")
	@ApiOperation(value = "发送短信给某角色", notes = "发送短信给某角色")
	/*@ApiImplicitParams({
			@ApiImplicitParam(name = "roles", value = "角色编号" , required = true, paramType = "query"),
			@ApiImplicitParam(name = "message", value = "通知信息", required = true , paramType = "query"),
			@ApiImplicitParam(name = "noticeTitle", value = "通知标题" , required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别" , required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "类别描述" , required = true, paramType = "query")
	})*/
	DTO sendInfo2Roles(PushServerRequestDTO pushServerRequestDTO);

	@GetMapping(PREFIX + "/all")
	@ApiOperation(value = "发送短信给所有人", notes = "发送短信给所有人")
	DTO sendInfo2All(PushServerRequestDTO pushServerRequestDTO);
}
