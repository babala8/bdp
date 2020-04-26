package com.zjft.microservice.treasurybrain.pushserver.web_inner;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * @author 常 健
 * @since 2020/2/25
 */
public interface SendMailInfoInnerResource {

	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "message", value = "通知信息", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeTitle", value = "通知标题", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别", required = true, paramType = "query"),
			@ApiImplicitParam(name = "time", value = "通知时间（定时任务专用，其他场景无需使用）", paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "类别描述", required = true, paramType = "query")
	})
	DTO sendInfo2User(@ApiIgnore @RequestParam HashMap hashMap);

	@ApiImplicitParams({
			@ApiImplicitParam(name = "roles", value = "角色编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "message", value = "通知信息", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeTitle", value = "通知标题", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "类别描述", required = true, paramType = "query")
	})
	DTO sendInfo2Roles(@ApiIgnore @RequestParam HashMap hashMap);

	@ApiImplicitParams({
			@ApiImplicitParam(name = "message", value = "通知信息", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeTitle", value = "通知标题", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别", required = true, paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "类别描述", required = true, paramType = "query")
	})
	DTO sendInfo2All(@ApiIgnore @RequestParam HashMap hashMap);
}
