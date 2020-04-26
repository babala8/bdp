package com.zjft.microservice.treasurybrain.usercenter.service.impl;


import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.domain.SysRoleDO;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysMenuConverter;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysRoleConverter;
import com.zjft.microservice.treasurybrain.usercenter.repository.SysMenuMapper;
import com.zjft.microservice.treasurybrain.usercenter.repository.SysRoleMapper;
import com.zjft.microservice.treasurybrain.usercenter.service.SysRoleService;
import com.zjft.microservice.treasurybrain.usercenter.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 角色管理ServiceImpl
 *
 * @author 杨光
 * @since 2019-02-26
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {

	private final SysRoleMapper sysRoleMapper;
	private final SysMenuMapper sysMenuMapper;
	private final SysUserService sysUserService;

	@Autowired
	SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysMenuMapper sysMenuMapper, SysUserService sysUserService) {
		this.sysRoleMapper = sysRoleMapper;
		this.sysMenuMapper = sysMenuMapper;
		this.sysUserService = sysUserService;
	}

	/**
	 * 查询该机构级别及其下属机构的角色列表
	 *
	 * @param orgGradeNo 机构级别
	 * @return ListDTO<RoleDTO>
	 */
	@Override
	public ListDTO<RoleDTO> getRoleListByOrgGradeNo(String orgGradeNo) {
		ListDTO<RoleDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
		UserDTO authUser = sysUserService.getCurrentUserInfo();
		List<SysRoleDO> doList = sysRoleMapper.queryOrgGradeNo(orgGradeNo, authUser.getOrgNo());
		List<RoleDTO> dtoList = SysRoleConverter.INSTANCE.domain2dto(doList);

		dto.setRetList(dtoList);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * 查询角色分页列表
	 *
	 * @param paramMap orgGradeNo、curPage、pageSize
	 * @return PageDTO
	 */
	@Override
	public PageDTO<RoleDTO> getRoleListByPage(Map<String, Object> paramMap) {
		PageDTO<RoleDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		UserDTO authUser = sysUserService.getCurrentUserInfo();
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);


		String orgGradeNo = "%" + StringUtil.parseString(paramMap.get("orgGradeNo")) + "%";
		int totalRow = sysRoleMapper.queryTotalRow(orgGradeNo, authUser.getOrgNo());

		paramMap.put("orgGradeNo", orgGradeNo);
		paramMap.put("authOrgNo", authUser.getOrgNo());

		List<SysRoleDO> doList = sysRoleMapper.queryByPage(paramMap);
		List<RoleDTO> dtoList = SysRoleConverter.INSTANCE.domain2dto(doList);

		pageDTO.setRetList(dtoList);
		pageDTO.setPageSize(pageSize);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(PageUtil.computeTotalPage(pageSize, totalRow));
		return pageDTO;
	}

	/**
	 * 查询角色详情信息
	 * 1.先查询角色详情信息
	 * 2.查询角色拥有的 menuList，其中部分菜单 checked=1
	 * 3.组合
	 *
	 * @param no roleNo 角色编号
	 * @return RoleDTO
	 */
	@Override
	public RoleDTO getRoleDetailByNo(String no) {

		if (!checkPermissionByRoleNo(StringUtil.objectToInt(no))) {
			return new RoleDTO(RetCodeEnum.FORBIDDEN);
		}

		SysRoleDO roleDO = sysRoleMapper.queryRoleDetailByNo(no);
		List<SysMenuDO> sysMenuList = sysRoleMapper.queryMenuListByRoleNo(no);

		RoleDTO roleDTO = SysRoleConverter.INSTANCE.domain2dto(roleDO);
		List<MenuDTO> list = SysMenuConverter.INSTANCE.domain2dto(sysMenuList);

		roleDTO.setMenuList(list);
		roleDTO.setResult(RetCodeEnum.SUCCEED);
		return roleDTO;
	}

	/**
	 * 添加角色，同时添加角色的菜单列表
	 * 异常时回滚
	 *
	 * @param role RoleDTO
	 * @return DTO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO addRole(RoleDTO role) {

		if (checkPermissionByOrgGradeNo(role.getOrgGradeNo())) {
			int no = sysRoleMapper.selectMaxNo();
			SysRoleDO sysRoleDO = new SysRoleDO();
			sysRoleDO.setName(role.getName());
			sysRoleDO.setOrgGradeNo(role.getOrgGradeNo());
			sysRoleDO.setNote(role.getNote());
			sysRoleDO.setNo(no);

			if (sysRoleMapper.insert(sysRoleDO) == 1) {
				List<MenuDTO> menuList = role.getMenuList();
				if (menuList.size() > 0) {
					List<SysMenuDO> doList = SysMenuConverter.INSTANCE.dto2do(menuList);
					sysRoleMapper.insertRoleMenu(no, doList);
				}
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FORBIDDEN);
	}

	/**
	 * 更新角色
	 * 1.检查权限
	 * 2.需要删除菜单后重新插入菜单列表
	 *
	 * @param role RoleDTO
	 * @return DTO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO updateRole(RoleDTO role) {

		if (checkPermissionByOrgGradeNo(role.getOrgGradeNo())) {
			SysRoleDO rDO = new SysRoleDO();
			rDO.setNo(role.getNo());
			rDO.setName(role.getName());
			rDO.setOrgGradeNo(role.getOrgGradeNo());
			rDO.setNote(role.getNote());
			if (sysRoleMapper.updateByPrimaryKey(rDO) == 1) {
				List<MenuDTO> menuList = role.getMenuList();
				if (menuList.size() > 0) {
					List<SysMenuDO> doList = SysMenuConverter.INSTANCE.dto2do(menuList);
					sysRoleMapper.deleteRoleMenu(role.getNo());
					sysRoleMapper.insertRoleMenu(role.getNo(), doList);
				}
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FORBIDDEN);
	}

	/**
	 * 根据编号删除角色
	 * 1.检查权限
	 * 2.删除角色下的菜单
	 * 3.删除角色
	 *
	 * @param no 角色编号
	 * @return DTO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO deleteRoleByNo(int no) {
		if (checkPermissionByRoleNo(no)) {
			sysRoleMapper.deleteRoleMenu(no);
			int i = sysRoleMapper.deleteByPrimaryKey(no);
			if (i == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FORBIDDEN);
	}


	/**
	 * 检查权限, `当前用户的机构等级` 是否大于等于 `目标机构等级`
	 *
	 * @param orgGradeNo 目标机构等级
	 * @return boolean
	 */
	private boolean checkPermissionByOrgGradeNo(int orgGradeNo) {
		UserDTO authUser = sysUserService.getCurrentUserInfo();
		return orgGradeNo >= sysRoleMapper.getOrgGradeNoByAuthOrgNo(authUser.getOrgNo());
	}

	/**
	 * 检查权限, `当前用户的机构等级` 是否大于等于 `目标角色的机构等级`
	 *
	 * @param roleNo 目标角色编号
	 * @return boolean
	 */
	private boolean checkPermissionByRoleNo(int roleNo) {
		UserDTO authUser = sysUserService.getCurrentUserInfo();
		return sysRoleMapper.checkPermissionByRoleNo(roleNo, authUser.getOrgNo()) > 0;
	}
}
