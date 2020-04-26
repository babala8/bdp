package com.zjft.microservice.treasurybrain.usercenter.service;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.RoleDTO;

import java.util.Map;

/**
 * @author 杨光
 * @since 2019-02-26
 */
public interface SysRoleService {

	ListDTO<RoleDTO> getRoleListByOrgGradeNo(String orgGradeNo);

	PageDTO<RoleDTO> getRoleListByPage(Map<String, Object> paramMap);

	RoleDTO getRoleDetailByNo(String no);

	DTO addRole(RoleDTO p);

	DTO updateRole(RoleDTO p);

	DTO deleteRoleByNo(int no);
}
