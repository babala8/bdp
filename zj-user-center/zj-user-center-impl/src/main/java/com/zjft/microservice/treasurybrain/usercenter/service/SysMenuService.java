package com.zjft.microservice.treasurybrain.usercenter.service;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPermissionDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 * @since 2019-03-08
 *
 * @author 张弛
 * @since 2020-01-15
 */
public interface SysMenuService {

	ListDTO getAllMenuList();

	/**
	 * 更新某个叶子菜单下的接口
	 *
	 * @return DTO
	 */
	DTO updateMenuPermission(String menuNo, List<SysPermissionDTO> list);

	/**
	 * 获取某个叶子菜单下的接口
	 *
	 * @return ListDTO
	 */
	ListDTO<SysPermissionDTO> getPermissionListByMenuNo(String menuNo);

	/**
	 * 获取所有的接口列表
	 *
	 * @return ListDTO
	 */
	PageDTO<SysPermissionDTO> getPermissionList(Map<String, Object> map);


	/**
	 * 获取所有的用户菜单列表
	 *
	 * @return ListDTO
	 */
	PageDTO<SysMenuDTO> qryMenuList(Map<String, Object> map);

	/**
	 * 查询菜单详情
	 *
	 * @return ListDTO
	 */
	SysMenuDTO qryMenuDetailByMenuNo(String no);

	/**
	 * 添加菜单
	 *
	 * @return DTO
	 */

	DTO addSysMenu(SysMenuDTO sysMenuDTO);

	/**
	 * 修改菜单
	 *
	 * @return DTO
	 */

	DTO modSysMenu(SysMenuDTO sysMenuDTO);

	/**
	 * 删除菜单
	 *
	 * @return DTO
	 */

	DTO delSysMenuByNo(String no);
}
