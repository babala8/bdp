package com.zjft.microservice.treasurybrain.usercenter.web;


import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 张弛
 * @since 2020-01-22
 */
@Api(value = "用户中心：权限管理", tags = {"用户中心：权限管理"})
public interface SysPermissionResource {
	String PREFIX="${user-center:}/v2/permission";
	@GetMapping(PREFIX)
	@ApiOperation(value = "查询所有权限列表", notes = "查询所有权限列表")
	ListDTO getAllPermissionList();
}
