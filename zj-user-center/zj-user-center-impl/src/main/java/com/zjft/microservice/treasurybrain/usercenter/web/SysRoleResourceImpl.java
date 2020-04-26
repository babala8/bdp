package com.zjft.microservice.treasurybrain.usercenter.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.RoleDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author 杨光
 * @since 2019-03-08
 */
@Slf4j
@RestController
public class SysRoleResourceImpl implements SysRoleResource {

	private SysRoleService sysRoleService;

	@Autowired
	SysRoleResourceImpl(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@Override
	public DTO addRole(RoleDTO roleDTO) {
		if (StringUtil.isBlank(roleDTO.getName()) || roleDTO.getOrgGradeNo() == null) {
			return new RoleDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return sysRoleService.addRole(roleDTO);
		} catch (Exception e) {
			log.error("[添加角色信息异常]: ", e);
			return new RoleDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO updateRoleByNo(RoleDTO roleDTO) {
		if (roleDTO.getNo() == null || StringUtil.isBlank(roleDTO.getName()) || roleDTO.getOrgGradeNo() == null) {
			return new RoleDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return sysRoleService.updateRole(roleDTO);
		} catch (Exception e) {
			log.error("[修改权限信息异常]: ", e);
			return new RoleDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO deleteRole(Integer no) {
		if (no == null || no < 0) {
			return new RoleDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return sysRoleService.deleteRoleByNo(no);
		} catch (Exception e) {
			log.error("[删除角色信息异常]: ", e);
			return new RoleDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public PageDTO getRoleListByPage(Map<String, Object> param) {
		try {
			return sysRoleService.getRoleListByPage(param);
		} catch (Exception e) {
			log.error("[获取分页角色列表异常]: ", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);

		}
	}

	/**
	 * 查询该机构级别及其下属机构的角色列表
	 *
	 * @param orgGradeNo 机构等级编号
	 * @return ListDTO
	 */
	@Override
	public ListDTO getRoleListByOrgGradeNo(String orgGradeNo) {
		if (StringUtil.isBlank(orgGradeNo)) {
			return new ListDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return sysRoleService.getRoleListByOrgGradeNo(orgGradeNo);
		} catch (Exception e) {
			log.error("[获取下属机构角色列表异常]: ", e);
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}


	@Override
	public RoleDTO getDetailByRole(String no) {
		if (StringUtil.isBlank(no)) {
			return new RoleDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return sysRoleService.getRoleDetailByNo(no);
		} catch (Exception e) {
			log.error("[查询角色详情失败]:", e);
			return new RoleDTO(RetCodeEnum.EXCEPTION);
		}
	}
}
