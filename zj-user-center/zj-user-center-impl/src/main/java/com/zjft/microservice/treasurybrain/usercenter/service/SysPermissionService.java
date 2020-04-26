package com.zjft.microservice.treasurybrain.usercenter.service;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
/**
 * @author 张弛
 * @since 2020-01-22
 */
public interface SysPermissionService {
	/**
	 * 获取所有的权限列表
	 *
	 * @return ListDTO
	 */
	ListDTO getAllPermissionList();
}
