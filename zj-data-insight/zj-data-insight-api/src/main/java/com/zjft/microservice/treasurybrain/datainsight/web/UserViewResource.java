package com.zjft.microservice.treasurybrain.datainsight.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.datainsight.dto.InsightUserConfigDTO;
import com.zjft.microservice.treasurybrain.datainsight.dto.SubjectDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @author 杨光
 */
@Api(value = "数据洞察：用户自定义配置", tags = {"数据洞察：用户自定义配置"})
public interface UserViewResource {

	String PREFIX = "${data-insight:}/v2/view";

	@GetMapping(value = PREFIX + "/{subjectId}")
	@ApiOperation(value = "获取用户自定义配置", notes = "获取用户自定义配置")
	@ApiImplicitParam(name = "subjectId", value = "主题编号", required = true, paramType = "path")
	InsightUserConfigDTO getUserConfig(@PathVariable(value = "subjectId") int subjectId);

	@PutMapping(PREFIX + "/default")
	@ApiOperation(value = "更新用户自定义视图", notes = "更新用户自定义视图")
	DTO modUsrDefaultView(@RequestBody InsightUserConfigDTO config);


	@GetMapping(value = PREFIX + "/subject")
	@ApiOperation(value = "获取用户主题列表", notes = "用户主题列表")
	ListDTO<SubjectDTO> getUserSubjects();

}
