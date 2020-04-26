package com.zjft.microservice.treasurybrain.usercenter.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPermissionDTO;
import com.zjft.microservice.treasurybrain.usercenter.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author 杨光
 * @since 2019-03-08
 *
 * @author 张弛
 * @since 2020-01-15
 */
@Slf4j
@RestController
public class SysMenuResourceImpl implements SysMenuResource {

	private final SysMenuService sysMenuService;


	@Autowired
	public SysMenuResourceImpl(SysMenuService sysMenuService) {
		this.sysMenuService = sysMenuService;
	}

	/**
	 * 获取所有的菜单列表
	 *
	 * @return ListDTO
	 */
	@Override
	public ListDTO getAllMenuList() {
		try {
			return sysMenuService.getAllMenuList();
		} catch (Exception e) {
			return new ListDTO(RetCodeEnum.FAIL);
		}

	}

	/**
	 * 更新某个叶子菜单下的接口
	 *
	 * @return DTO
	 */
	@Override
	public DTO updateMenuPermission(String menuNo, List<SysPermissionDTO> list) {
		if (StringUtil.isNullorEmpty(menuNo)) {
			return new DTO(RetCodeEnum.PARAM_ERROR);
		}
		try {
			return sysMenuService.updateMenuPermission(menuNo, list);

		} catch (Exception e) {
			return new ListDTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 获取某个叶子菜单下的接口
	 *
	 * @return ListDTO
	 */
	@Override
	public ListDTO<SysPermissionDTO> getPermissionListByMenuNo(String menuNo) {
		if (StringUtil.isNullorEmpty(menuNo)) {
			return new ListDTO<>(RetCodeEnum.PARAM_ERROR);
		}
		try {
			return sysMenuService.getPermissionListByMenuNo(menuNo);

		} catch (Exception e) {
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 获取所有的接口列表
	 *
	 * @return ListDTO
	 */
	@Override
	public PageDTO<SysPermissionDTO> getPermissionList(Map<String, Object> map){
		try {
			return sysMenuService.getPermissionList(map);
		} catch (Exception e) {
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 分页查询菜单
	 *
	 * @return pageDTO
	 */
	@Override
	public PageDTO<SysMenuDTO> qryMenuList(Map<String, Object> map) {
		try {
			return sysMenuService.qryMenuList(map);
		} catch (Exception e) {
			log.error("分页查询失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}


	}

	/**
	 * 查询详情
	 *
	 * @return SysMenuDTO
	 */
	@Override
	public SysMenuDTO qryMenuDetailByMenuNo(String no) {
		SysMenuDTO sysMenuDTO = new SysMenuDTO(RetCodeEnum.FAIL);
		try {
			if (StringUtil.isNullorEmpty(no)) {
				return sysMenuDTO;
			}
			sysMenuDTO = sysMenuService.qryMenuDetailByMenuNo(no);
		} catch (Exception e) {
			log.error("查询菜单详情失败", e);
			sysMenuDTO = new SysMenuDTO(RetCodeEnum.FAIL);
		}
		return sysMenuDTO;
	}

	/**
	 * 添加菜单
	 *
	 * @return SysMenuDTO
	 */
	@Override
	public DTO addSysMenu(SysMenuDTO sysMenuDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			dto= sysMenuService.addSysMenu(sysMenuDTO);
		}catch (Exception e){
			log.error("添加菜单失败",e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}
	/**
	 * 修改菜单
	 *
	 * @return SysMenuDTO
	 */
	@Override
	public DTO modSysMenu(SysMenuDTO sysMenuDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			dto= sysMenuService.modSysMenu(sysMenuDTO);
		}catch (Exception e){
			log.error("添加菜单失败",e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	/**
	 * 删除菜单信息
	 *
	 * @return SysMenuDTO
	 */

	@Override
	public DTO delSysMenuByNo(String no) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			if (StringUtil.isNullorEmpty(no)) {
				return dto;
			}
			dto = sysMenuService.delSysMenuByNo(no);
		} catch (Exception e) {
			log.error("查询菜单详情失败", e);
			dto = new SysMenuDTO(RetCodeEnum.FAIL);
		}
		return dto;
	}


}
