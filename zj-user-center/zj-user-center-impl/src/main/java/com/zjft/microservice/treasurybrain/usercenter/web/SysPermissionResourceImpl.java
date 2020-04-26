package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;

import com.zjft.microservice.treasurybrain.usercenter.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张弛
 * @since 2020-01-22
 */
@Slf4j
@RestController
public class SysPermissionResourceImpl implements SysPermissionResource {
	private final SysPermissionService sysPermissionService;

	@Autowired
	public SysPermissionResourceImpl(SysPermissionService sysPermissionService) {
		this.sysPermissionService = sysPermissionService;
	}
	/**
	 * 获取所有的权限列表
	 *
	 * @return ListDTO
	 */

	@Override
	public ListDTO getAllPermissionList() {
		try {
			return sysPermissionService.getAllPermissionList();
		} catch (Exception e) {
			return new ListDTO(RetCodeEnum.FAIL);
		}
	}
}
