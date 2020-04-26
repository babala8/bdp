package com.zjft.microservice.treasurybrain.usercenter.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.RoleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(value = "用户中心：角色管理", tags = {"用户中心：角色管理"})
public interface SysRoleResource {

	String PREFIX = "${user-center:}/v2/role";

	/**
	 * 添加角色及菜单
	 *
	 * @param role 名称 描述 机构级别 按钮列表
	 * @return DTO
	 */
	@PostMapping(PREFIX)
	@ApiOperation(value = "添加角色", notes = "添加角色")
	DTO addRole(@RequestBody RoleDTO role);


	/**
	 * 修改角色及菜单
	 *
	 * @param role 名称 描述 机构级别 按钮列表
	 * @return DTO
	 */
	@PutMapping(PREFIX)
	@ApiOperation(value = "修改角色", notes = "修改角色")
	DTO updateRoleByNo(@RequestBody RoleDTO role);


	@DeleteMapping(PREFIX + "/{no}")
	@ApiOperation(value = "删除角色", notes = "删除角色")
	@ApiImplicitParam(name = "no", value = "角色编号", paramType = "path")
	DTO deleteRole(@PathVariable Integer no);


	@GetMapping(PREFIX + "/{no}")
	@ApiOperation(value = "查询角色详情", notes = "查询角色详情")
	RoleDTO getDetailByRole(@PathVariable String no);


	@GetMapping(PREFIX)
	@ApiOperation(value = "获取分页角色列表", notes = "获取分页角色列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgGradeNo", value = "机构等级", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query"),
	})
	PageDTO getRoleListByPage(@ApiIgnore @RequestParam Map<String, Object> param);


	@GetMapping(PREFIX + "/grade")
	@ApiOperation(value = "查询该机构级别及下属级别角色列表", notes = "查询该机构级别及下属级别角色列表")
	@ApiImplicitParam(name = "orgGradeNo", value = "机构等级编号", paramType = "query")
	ListDTO getRoleListByOrgGradeNo(@ApiIgnore @RequestParam String orgGradeNo);

}
