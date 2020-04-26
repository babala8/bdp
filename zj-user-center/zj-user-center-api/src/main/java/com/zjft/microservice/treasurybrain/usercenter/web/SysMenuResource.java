package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPermissionDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 * @since 2019-03-08
 *
 * @author 张弛
 * @since 2020-01-15
 */
@Api(value = "用户中心：菜单管理", tags = {"用户中心：菜单管理"})
public interface SysMenuResource {

	String PREFIX = "${user-center:}/v2/menu";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询所有菜单列表", notes = "查询所有菜单列表")
	ListDTO getAllMenuList();

	@PutMapping(PREFIX + "/{menuNo}")
	@ApiImplicitParam(name = "menuNo", value = "菜单编号", paramType = "path")
	@ApiOperation(value = "更新叶子菜单下的接口", notes = "更新叶子菜单下的接口")
	DTO updateMenuPermission(@PathVariable("menuNo") String menuNo, @RequestBody List<SysPermissionDTO> list);


	@GetMapping(PREFIX + "/{menuNo}")
	@ApiImplicitParam(name = "menuNo", value = "菜单编号", paramType = "path")
	@ApiOperation(value = "查询叶子菜单下的接口列表", notes = "查询叶子菜单下的接口列表")
	ListDTO<SysPermissionDTO> getPermissionListByMenuNo(@PathVariable("menuNo") String menuNo);


	@GetMapping(PREFIX + "/permission")
	@ApiOperation(value = "分页查询接口列表", notes = "分页查询接口列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "no", value = "接口编号", paramType = "query"),
			@ApiImplicitParam(name = "url", value = "url", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "接口名称", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query"),
	})
	PageDTO<SysPermissionDTO> getPermissionList(@ApiIgnore @RequestParam Map<String, Object> map);

	@GetMapping(PREFIX + "/menu")
	@ApiOperation(value = "分页查询菜单列表", notes = "分页查询菜单列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "curPage", value = "当前页面", paramType = "query", defaultValue="1", required=true),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query",defaultValue="20", required=true),
			@ApiImplicitParam(name = "no", value = "菜单编号", paramType = "query"),
			@ApiImplicitParam(name = "url", value = "路由", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query"),
			@ApiImplicitParam(name = "menuFather", value = "父菜单编号", paramType = "query"),
			@ApiImplicitParam(name = "menuFatherName", value = "父菜单名称", paramType = "query"),
	})
	PageDTO<SysMenuDTO> qryMenuList(@ApiIgnore @RequestParam Map<String, Object> map);



	@GetMapping(PREFIX + "/menu/{no}")
	@ApiOperation(value = "查询菜单详情", notes = "查询菜单详情")
	@ApiImplicitParam(name = "no", value = "菜单编号", paramType = "path")
	SysMenuDTO qryMenuDetailByMenuNo(@PathVariable("no") String no);


	@PostMapping(PREFIX)
	@ApiOperation(value = "添加菜单", notes = "添加菜单")
	@ApiOperationSupport(ignoreParameters ={"sysMenuDTO.menuFatherName"} )
	DTO addSysMenu(@RequestBody SysMenuDTO sysMenuDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value ="修改菜单", notes = "修改菜单")
	DTO modSysMenu(@RequestBody  SysMenuDTO sysMenuDTO);

	@DeleteMapping(PREFIX+ "/menu/{no}")
	@ApiOperation(value ="删除菜单", notes = "删除菜单")
	@ApiImplicitParam(name = "no", value = "菜单编号", paramType = "path")
	DTO delSysMenuByNo(@PathVariable("no") String no);


}
