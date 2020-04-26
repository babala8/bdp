package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.OutsourcingDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/21
 */
@Api(tags = "用户中心：外包人员管理", value = "用户中心：外包人员管理")
public interface OutsourcingResource {
	String PREFIX = "${user-center:}/v2/outSourcing";

	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询外包人员信息列表", notes = "分页查询外包人员信息列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "no", value = "人员编号", example = "A1001", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "人员姓名", example = "张非", paramType = "query"),
			@ApiImplicitParam(name = "post", value = "岗位", example = "1", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<OutsourcingDTO> queryOutsourcingList(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX)
	@ApiOperation(value = "增加外包人员信息", notes = "增加外包人员信息")
	DTO addOutsourcingInfo(@RequestBody @ApiParam(name = "外包人员信息", value = "外包人员信息", required = true) OutsourcingDTO outsourcingDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改外包人员信息", notes = "修改外包人员信息")
	DTO modOutsourcingInfo(@RequestBody @ApiParam(name = "外包人员信息", value = "外包人员信息", required = true) OutsourcingDTO outsourcingDTO);

	@DeleteMapping(PREFIX + "/{no}")
	@ApiOperation(value = "删除外包人员信息", notes = "删除外包人员信息")
	@ApiImplicitParam(name = "no", value = "人员编号", example = "A1001", required = true, paramType = "path")
	DTO delOutsourcingInfoByNo(@PathVariable("no") String no);

	@PostMapping(PREFIX+ "/import")
	@ApiOperation(value = "批量导入外包人员信息", notes = "批量导入外包人员信息")
	DTO importOutsourcing(@RequestParam(value = "filename") MultipartFile file);

	/**
	 *
	 * 查询某岗位的外包人员
	 * @param post 岗位
	 * @return
	 */
	List<String> qryByPost(int post);
}
