package com.zjft.microservice.treasurybrain.usercenter.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 杨光
 * @since 2019-03-08
 */
@Api(value = "用户中心：用户管理", tags = {"用户中心：用户管理"})
public interface SysUserResource {

	String PREFIX = "${user-center:}/v2/user";

	@PostMapping(PREFIX)
	@ApiOperation(value = "添加用户", notes = "添加用户")
	DTO addUser(@RequestBody UserDTO u);


	@PutMapping(PREFIX)
	@ApiOperation(value = "修改用户", notes = "修改用户")
	DTO updateUserByNo(@RequestBody UserDTO u);


	@DeleteMapping(PREFIX + "/{username}")
	@ApiOperation(value = "删除用户", notes = "删除用户")
	@ApiImplicitParam(name = "username", value = "用户名", paramType = "path")
	DTO deleteUser(@PathVariable("username") String username);


	@GetMapping(PREFIX)
	@ApiOperation(value = "查询分页用户列表", notes = "查询分页用户列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgNo", value = "机构编号", paramType = "query"),
			@ApiImplicitParam(name = "roleNo", value = "角色编号", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "用户名", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query"),
	})
	PageDTO getUserListByPage(@ApiIgnore @RequestParam Map<String, Object> params);


	@GetMapping(PREFIX + "/detail")
	@ApiOperation(value = "查询当前登录用户详情", notes = "查询当前登录用户详情")
	UserDTO getAuthUserDetail();


	@GetMapping(PREFIX + "/info")
	@ApiOperation(value = "查询当前登录的用户信息", notes = "查询当前登录的用户信息")
	UserDTO getAuthUserInfo();


	@PostMapping(PREFIX + "/password")
	@ApiOperation(value = "用户自己修改密码", notes = "用户自己修改密码")
	DTO modPassword(@RequestBody UserDTO u);


	@PutMapping(PREFIX + "/password")
	@ApiOperation(value = "管理员重置密码", notes = "username、newPassword")
	DTO resetPassword(@RequestBody UserDTO u);


	@GetMapping(PREFIX + "/addnotes")
	@ApiOperation(value = "查询加钞人员用户列表", notes = "查询加钞人员用户列表")
	ListDTO<UserDTO> getUserListForAddnotes();

}
