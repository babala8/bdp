package com.zjft.microservice.treasurybrain.usercenter.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysPermissionDO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPermissionDTO;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysPermissionConverter;
import com.zjft.microservice.treasurybrain.usercenter.repository.SysPermissionMapper;
import com.zjft.microservice.treasurybrain.usercenter.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限
 * @author 张弛
 * @since 2020-01-22
 */
@Slf4j
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
	private final SysPermissionMapper sysPermissionMapper;

	@Autowired
	public SysPermissionServiceImpl(SysPermissionMapper sysPermissionMapper) {
		this.sysPermissionMapper = sysPermissionMapper;
	}

	/**
	 * 获取所有权限的列表
	 *
	 * @return ListDTO<MenuDTO>
	 */
	@Override
	public ListDTO getAllPermissionList() {
		ListDTO<SysPermissionDTO> dto = new ListDTO<>(RetCodeEnum.SUCCEED);
		List<SysPermissionDO> permissionList = sysPermissionMapper.queryAllPermission();
		List<SysPermissionDTO> list = SysPermissionConverter.INSTANCE.domain2dto(permissionList);
		dto.setRetList(list);
		return dto;
	}
}
