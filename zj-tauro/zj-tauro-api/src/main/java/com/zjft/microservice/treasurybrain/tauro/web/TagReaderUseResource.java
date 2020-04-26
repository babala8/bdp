package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderUseDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(tags = {"物流中心：手持机领用管理"})
public interface TagReaderUseResource {

	String PREFIX = "${tauro:}/v2/readerInfoFlow";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询手持机领用记录", notes = "查询手持机领用记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tagReaderNo", value = "读写器编号", example = "01021101", paramType = "query"),
			@ApiImplicitParam(name = "requestOpNo", value = "领用人编号", example = "zhangsan", paramType = "query"),
			@ApiImplicitParam(name = "tagReaderUseStatus", value = "领用状态 -- 1：待审核 2：已审核（通过） 3：已审核（拒绝） 4：已归还", example = "2", paramType = "query"),
			@ApiImplicitParam(name = "requestStartDate", value = "申请开始日期[yyyy-MM-dd]",  paramType = "query"),
			@ApiImplicitParam(name = "requestEndDate", value = "申请结束日期[yyyy-MM-dd]",  paramType = "query"),
			@ApiImplicitParam(name = "crashFlag", value = "紧急领用标志 -- 0：正常 1：紧急", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<TagReaderUseDTO> queryTagReaderUseListByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX)
	@ApiOperation(value = "增加手持机领用记录", notes = "增加手持机领用记录")
	DTO addTagReaderUseInfo(@RequestBody @ApiParam(name = "手持机领用记录信息", value = "手持机领用记录信息", required = true) TagReaderUseDTO tagReaderUseDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改手持机领用记录", notes = "修改手持机领用记录")
	DTO modTagReaderUseInfo(@RequestBody @ApiParam(name = "手持机领用记录信息", value = "手持机领用记录信息", required = true) TagReaderUseDTO tagReaderUseDTO);

	@GetMapping(PREFIX + "/detail")
	@ApiOperation(value = "查询手持机领用记录详情", notes = "查询手持机领用记录详情")
	@ApiImplicitParam(name = "tagReaderUseNo", value = "编号", example = "20190809000001", required = true, paramType = "query")
	TagReaderUseDTO queryTagReaderUseDetail(@RequestParam("tagReaderUseNo") String tagReaderUseNo);

	/**
	 * 目前流程中未使用审核手持机领用功能，若要使用将TagReaderUseServiceImpl--addTagReaderUseInfo--tagReaderUseStatus设置为1
	 */
	@PostMapping(PREFIX + "/audit")
	@ApiOperation(value = "审核手持机领用记录", notes = "审核手持机领用记录")
	DTO auditTagReaderUseInfo(@RequestBody @ApiParam(name = "手持机领用记录信息", value = "手持机领用记录信息", required = true) TagReaderUseDTO tagReaderUseDTO);

	@PostMapping(PREFIX + "/return")
	@ApiOperation(value = "归还手持机", notes = "归还手持机")
	DTO returnTagReaderUseInfo(@RequestBody @ApiParam(name = "手持机领用记录信息", value = "手持机领用记录信息", required = true) TagReaderUseDTO tagReaderUseDTO);

	@DeleteMapping(PREFIX + "/{tagReaderUseNo}")
	@ApiOperation(value = "删除手持机领用记录", notes = "删除手持机领用记录")
	@ApiImplicitParam(name = "tagReaderUseNo", value = "编号", example = "20190809000001", required = true, paramType = "path")
	DTO delTagReaderUseInfoByTagReaderUseNo(@PathVariable("tagReaderUseNo") String tagReaderUseNo);


}
