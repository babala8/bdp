package com.zjft.microservice.treasurybrain.pushserver.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushSeverInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/26
 */
@Api(value = "推送中心：推送消息查询", tags = "推送中心：推送消息查询")
public interface PushServerInfoResource {

	String PREFIX = "/push-server/v2/pushServer";

	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询推送信息列表", notes = "分页查询推送信息列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "推送时间", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "推送人员", paramType = "query"),
			@ApiImplicitParam(name = "noticeWay", value = "通知方式", paramType = "query"),
			@ApiImplicitParam(name = "noticeCategory", value = "通知类别", paramType = "query"),
			@ApiImplicitParam(name = "noticeCategoryDescription", value = "通知类别描述", paramType = "query"),
			@ApiImplicitParam(name = "noticeFlag", value = "是否接收到消息：1、已接收到，2、未接收到", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<PushSeverInfoDTO> qryByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PutMapping(PREFIX)
	@ApiOperation(value = "更新推送信息为已读", notes = "更新推送信息为已读")
	DTO updateStatus(String no);

	@GetMapping(PREFIX + "/no")
	@ApiOperation(value = "根据NO查询消息主体", notes = "根据No查询消息主体")
	PushSeverInfoDTO qryMessageByNo(String no);

}


