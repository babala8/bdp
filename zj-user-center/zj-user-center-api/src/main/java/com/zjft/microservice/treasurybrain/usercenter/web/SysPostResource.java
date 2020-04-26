package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.PostUserInfoDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPostDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPostDetailDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * @author liuyuan
 * @author 常健
 * @since 2019/9/21 09:39
 */
@Api(value = "用户中心：岗位管理", tags = "用户中心：岗位管理")
public interface SysPostResource {

	String PREFIX = "${user-center:}/v2/post";

	@PostMapping(PREFIX)
	@ApiOperation(value = "新增岗位", notes = "新增岗位")
	@ApiOperationSupport(ignoreParameters = {"sysPostDTO.postNo", "sysPostDTO.postLimitList.postNo"})
	@ZjWorkFlow("addPost")
	DTO addPost(@RequestBody SysPostDTO sysPostDTO);

	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询岗位信息", notes = "分页查询岗位信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "postName", value = "岗位名称（模糊查询）"),
			@ApiImplicitParam(name = "postType", value = "岗位类型 1：总行 2：分行 3：支行 4：网点 5：金库"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	@ZjWorkFlow("qryPostInfoBypage")
	PageDTO<SysPostDTO> qryPostInfoBypage(@RequestParam @ApiIgnore HashMap<String, Object> param);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改岗位信息", notes = "修改岗位信息")
	@ApiOperationSupport(ignoreParameters = {"sysPostDTO.postLimitList.postNo"})
	@ZjWorkFlow("modPost")
	DTO modPost(@RequestBody SysPostDTO sysPostDTO);

	@DeleteMapping(PREFIX + "/{postNo}")
	@ApiOperation(value = "删除岗位信息", notes = "删除岗位信息")
	@ZjWorkFlow("deletePost")
	DTO deletePost(@PathVariable("postNo") String postNo);

	@GetMapping(PREFIX + "/all")
	@ApiOperation(value = "查询所有岗位信息", notes = "查询所有岗位信息")
	@ZjWorkFlow("qryAllPostInfo")
	ListDTO<SysPostDTO> qryAllPostInfo(String postNo);

	@GetMapping(PREFIX + "/detail")
	@ApiOperation(value = "查询当前岗位详情", notes = "查询当前岗位详情")
	@ApiOperationSupport(ignoreParameters = {"postLimitList.postNo"})
	@ZjWorkFlow("qryPostDetail")
	ListDTO<SysPostDetailDTO> qryPostDetail(@RequestParam("postNo") String postNo);

	@GetMapping(PREFIX + "/qryUserInfo")
	@ApiOperation(value = "根据机构、岗位查询当前人员信息", notes = "根据机构、岗位查询当前人员信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "postNo", value = "岗位编号", required = true),
			@ApiImplicitParam(name = "orgNo", value = "机构编号", required = true)
	})
	@ZjWorkFlow("qryUserByPostNoAndOrgNo")
	ListDTO<PostUserInfoDTO> qryUserByPostNoAndOrgNo(@RequestParam @ApiIgnore HashMap<String, Object> map);
}
