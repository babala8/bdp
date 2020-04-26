package com.zjft.microservice.treasurybrain.pushserver.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

@Api(value = "推送中心：推送邮件消息", tags = "推送中心：推送邮件消息")
public interface SendMailInfoResource {

	String PREFIX = "/push-server/v2/mailInfo";

	@GetMapping(PREFIX + "/user")
	@ApiOperation(value = "发送邮件给某用户", notes = "发送邮件给某用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "message", value = "通知信息", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeTitle", value = "通知标题", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别", required = true, paramType = "query"),
			@ApiImplicitParam(name = "time", value = "通知时间（定时任务专用，其他场景无需使用）", paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "类别描述", required = true, paramType = "query")
	})
	DTO sendInfo2User(@ApiIgnore @RequestParam HashMap hashMap);

	@GetMapping(PREFIX + "/role")
	@ApiOperation(value = "发送邮件给某角色", notes = "发送邮件给某角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roles", value = "角色编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "message", value = "通知信息", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeTitle", value = "通知标题", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "类别描述", required = true, paramType = "query")
	})
	DTO sendInfo2Roles(@ApiIgnore @RequestParam HashMap hashMap);

	@GetMapping(PREFIX + "/all")
	@ApiOperation(value = "发送邮件给所有人", notes = "发送邮件给所有人")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "message", value = "通知信息", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeTitle", value = "通知标题", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "类别描述", required = true, paramType = "query")
	})
	DTO sendInfo2All(@ApiIgnore @RequestParam HashMap hashMap);
}
