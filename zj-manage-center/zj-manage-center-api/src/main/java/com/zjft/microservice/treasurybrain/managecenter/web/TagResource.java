package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.TagDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


/**
 * @author 葛瑞莲
 * @since 2019/6/11
 */
@Api(tags = {"综合管理：标签管理"})
public interface TagResource {

	String PREFIX = "${manage-center:}/v2/tag";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询标签信息列表", notes = "查询标签信息列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tagTid", value = "标签编号", example = "B1001", paramType = "query"),
			@ApiImplicitParam(name = "tagType", value = "标签类型", example = "2", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", example = "1", paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", example = "028001", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<TagDTO> queryTagList(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX)
	@ApiOperation(value = "增加标签信息", notes = "增加标签信息")
	DTO addTagInfo(@RequestBody @ApiParam(name = "标签信息基本信息", value = "标签信息基本信息", required = true) TagDTO tagDTO);

	/**
	 *
	 * 需求内要求标签状态不能修改，只能通过修改实物关联关系（比如新增修改钞箱标签）来更新标签状态
	 *
	 * @param tagDTO
	 * @return
	 */
	@PutMapping(PREFIX)
	@ApiOperation(value = "修改标签信息", notes = "修改标签信息")
	DTO modTagInfo(@RequestBody @ApiParam(name = "标签信息基本信息", value = "标签信息基本信息", required = true) TagDTO tagDTO);


	/**
	 * 需求要求标签未停用时不能删除
	 *
	 * @param tagTid
	 * @return
	 */
	@DeleteMapping(PREFIX + "/{tagTid}")
	@ApiOperation(value = "删除标签信息", notes = "删除标签信息")
	@ApiImplicitParam(name = "tagTid", value = "标签编号", example = "B1001", required = true, paramType = "path")
	DTO delTagInfoByTagTid(@PathVariable("tagTid") String tagTid);

	/**
	 * 批量导入标签
	 */
	@PostMapping(PREFIX+ "/import")
	@ApiOperation(value = "批量导入标签信息", notes = "批量导入标签信息")
	DTO importTag(@RequestParam(value = "filename") MultipartFile file);
}
