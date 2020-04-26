package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.TagReaderInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(tags = "综合管理：手持机管理")
public interface TagReaderInfoResource {
	String PREFIX = "${manage-center:}/v2/tagReader";

	@PostMapping(PREFIX)
	@ApiOperation(value = "新增手持机", notes = "新增手持机")
	DTO addTagReaderInfo(@RequestBody @ApiParam(name = "手持机基本信息", value = "手持机基本信息", required = true) TagReaderInfoDTO tagReaderInfoDTO);

	@GetMapping(PREFIX)
	@ApiOperation(value = "手持机分页查询", notes = "手持机分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tagReaderNo", value = "手持机编号", example = "01021101", paramType = "query"),
			@ApiImplicitParam(name = "readerType", value = "手持机类型", example = "1", paramType = "query"),
			@ApiImplicitParam(name = "location", value = "安放地址", example = "四川省成都市武侯区益州大道中段", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", example = "3", paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", example = "028001", paramType = "query"),
			@ApiImplicitParam(name = "simNumberNo", value = "SIM卡编号", example = "13288888888", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", defaultValue = "10", required = true, paramType = "query"),
	})
	PageDTO<TagReaderInfoDTO> queryTagReaderInfoByPage(@ApiIgnore @RequestParam Map<String, Object> params);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改手持机信息", notes = "修改手持机信息"
	)
	DTO modTagReaderInfo(@RequestBody @ApiParam(name = "手持机基本信息", value = "手持机基本信息", required = true) TagReaderInfoDTO tagReaderInfoDTO);

	@DeleteMapping(PREFIX + "/{tagReaderNo}")
	@ApiOperation(value = "删除手持机信息", notes = "删除手持机信息")
	@ApiImplicitParam(name = "tagReaderNo", value = "手持机编号", example = "01021101", required = true, paramType = "path"
	)
	DTO delTagReaderInfoByNo(@PathVariable("tagReaderNo") String tagReaderNo);
}
