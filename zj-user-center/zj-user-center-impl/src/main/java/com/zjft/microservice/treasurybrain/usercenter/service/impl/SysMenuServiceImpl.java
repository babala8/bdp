package com.zjft.microservice.treasurybrain.usercenter.service.impl;


import com.zjft.microservice.treasurybrain.common.domain.SysPermissionPO;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysMenu;
import com.zjft.microservice.treasurybrain.usercenter.dto.MenusDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPermissionDTO;


import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysMenuDOConverter;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysPermissionConverter;
import com.zjft.microservice.treasurybrain.usercenter.po.SysMenuPO;
import com.zjft.microservice.treasurybrain.usercenter.repository.SysMenuMapper;
import com.zjft.microservice.treasurybrain.usercenter.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.util.Length;

import java.util.List;
import java.util.Map;

/**
 * 菜单
 *
 * @author 杨光
 * @author 张弛
 * @since 2019-03-08
 * @since 2020-01-15
 */
@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {

	private final SysMenuMapper sysMenuMapper;

	@Autowired
	public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
		this.sysMenuMapper = sysMenuMapper;
	}

	/**
	 * 获取所有菜单的列表
	 *
	 * @return ListDTO<MenuDTO>
	 */
	@Override
	public ListDTO getAllMenuList() {
		ListDTO<SysMenuDTO> dto = new ListDTO<>(RetCodeEnum.SUCCEED);
		List<SysMenu> menuList = sysMenuMapper.queryAllMenu();
		List<SysMenuDTO> list = SysMenuDOConverter.INSTANCE.po2dto(menuList);
		dto.setRetList(list);
		return dto;
	}

	/**
	 * 更新叶子菜单下的接口
	 * 1.menuNo必须是存在的叶子菜单
	 * 2.list中 no 不能为空
	 * 3.将list与menuNo关联，更新到SYS_MENU_PERMISSION表
	 *
	 * @return DTO
	 */
	@Override
	public DTO updateMenuPermission(String menuNo, List<SysPermissionDTO> list) {

		int i = sysMenuMapper.deleteMenuPermission(menuNo);
		List<SysPermissionPO> sysPermission = SysPermissionConverter.INSTANCE.dto2do(list);
		int j = sysMenuMapper.insertMenuPermission(menuNo, sysPermission);
		return new DTO(RetCodeEnum.SUCCEED);
	}

	/**
	 * 根据叶子菜单编号，获取某个叶子菜单下的接口
	 *
	 * @return ListDTO
	 */
	@Override
	public ListDTO<SysPermissionDTO> getPermissionListByMenuNo(String menuNo) {
		List<SysPermissionPO> list = sysMenuMapper.getPermissionListByMenuNo(menuNo);
		List<SysPermissionDTO> dtoList = SysPermissionConverter.INSTANCE.po2dto(list);
		ListDTO<SysPermissionDTO> dto = new ListDTO<>(RetCodeEnum.SUCCEED);
		dto.setRetList(dtoList);
		return dto;
	}

	/**
	 * 模糊查询，获取所有的接口列表
	 *
	 * @return ListDTO
	 */
	@Override
	public PageDTO<SysPermissionDTO> getPermissionList(Map<String, Object> map) {
		PageDTO<SysPermissionDTO> dto = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(map, dto);

		int totalRow = sysMenuMapper.queryTotalRow(map);
		List<SysPermissionPO> list = sysMenuMapper.getPermissionList(map);
		List<SysPermissionDTO> dtoList = SysPermissionConverter.INSTANCE.po2dto(list);

		dto.setTotalPage(PageUtil.computeTotalPage(pageSize, totalRow));
		dto.setTotalRow(totalRow);
		dto.setRetList(dtoList);
		return dto;
	}

	/**
	 * 分页查询，获取所有的用户菜单列表
	 *
	 * @return ListDTO
	 */
	@Override
	public PageDTO<SysMenuDTO> qryMenuList(Map<String, Object> map) {
		PageDTO<SysMenuDTO> dto = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(map, dto);

		int totalRow = sysMenuMapper.queryAllRow(map);
		List<SysMenu> list = sysMenuMapper.qryMenuList(map);
		List<SysMenuDTO> dtoList = SysMenuDOConverter.INSTANCE.po2dto(list);

		dto.setTotalPage(PageUtil.computeTotalPage(pageSize, totalRow));
		dto.setTotalRow(totalRow);
		dto.setRetList(dtoList);
		return dto;
	}


	/**
	 * 查询菜单详情
	 *
	 * @return SysMenuDTO
	 */
	@Override
	public SysMenuDTO qryMenuDetailByMenuNo(String no) {
		log.info("----------[qryMenuDetailByMenuNo]SysMenuService----------------");
		SysMenu sysMenu = sysMenuMapper.qryMenuDetailByMenuNo(no);
		List<SysMenu> sysMenuList = sysMenuMapper.querySysMenuListByNo(no);
		if (sysMenu != null) {
			SysMenuDTO sysMenuDTO = SysMenuDOConverter.INSTANCE.do2dto(sysMenu);
			List<MenusDTO> list = SysMenuDOConverter.INSTANCE.domain2dto(sysMenuList);
			sysMenuDTO.setSysMenuList(list);
			sysMenuDTO.setResult(RetCodeEnum.SUCCEED);
			return sysMenuDTO;
		}
		return new SysMenuDTO(RetCodeEnum.FAIL);
	}

	/**
	 * 增加菜单信息
	 *
	 * @return SysMenuDTO
	 */

	@Override
	@Transactional
	public DTO addSysMenu(SysMenuDTO sysMenuDTO) {
		log.info("------------[addSysMenu]SysMenuService-------------");

		SysMenuPO sysMenuPO = SysMenuDOConverter.INSTANCE.dto2po(sysMenuDTO);
		List<String> permissionList = sysMenuDTO.getPermissionList();
		String menuFather = sysMenuPO.getMenuFather();
		String maxNo = sysMenuMapper.qryMaxNoByMenuFather(menuFather);
		String menuNo;

		if (StringUtil.isNullorEmpty(maxNo)) {
			menuNo = menuFather + "01";
		} else {
			String temp = maxNo.substring(maxNo.length() - 2, maxNo.length());
			int tempInt = Integer.parseInt(temp);
			tempInt = tempInt + 1;
			String a = String.format("%02d", tempInt);
			StringBuilder b = new StringBuilder(maxNo);
			b.replace(b.length() - 2, b.length(), a);
			menuNo = b.toString();
		}
		sysMenuPO.setNo(menuNo);
		int insert = sysMenuMapper.insert(sysMenuPO);
		if (insert != 1) {
			return new DTO(RetCodeEnum.FAIL);
		}
		if (permissionList.size() != 0) {
			for (String permissionNo : permissionList) {
				int c = sysMenuMapper.insertPermissionList(menuNo, permissionNo);
				if (c != 1) {
					return new DTO(RetCodeEnum.FAIL);
				}
			}

		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	/**
	 * 修改菜单信息
	 * 先查询判断是否存在这条菜单信息
	 *
	 * @return SysMenuDTO
	 */

	@Override
	@Transactional
	public DTO modSysMenu(SysMenuDTO sysMenuDTO) {
		log.info("------------[modSysMenu]SysMenuService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			SysMenuPO sysMenuPO = SysMenuDOConverter.INSTANCE.dto2po(sysMenuDTO);
			SysMenu sysMenu = sysMenuMapper.qryMenuDetailByMenuNo(sysMenuDTO.getNo());
			List<String> permissionList = sysMenuDTO.getPermissionList();
			String menuNo = sysMenuDTO.getNo();
			if (sysMenu == null) {
				return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}
			int i = sysMenuMapper.updateByNo(sysMenuPO);
			int x = sysMenuMapper.deletePermissionNoByMenuNo(menuNo);
			if (x == 1) {
				dto.setResult(RetCodeEnum.SUCCEED);
			}
			if (permissionList.size() != 0) {
				for (String permissionNo : permissionList) {
					int c = sysMenuMapper.insertPermissionList(menuNo, permissionNo);
					if (c != 1) {
						return new DTO(RetCodeEnum.FAIL);
					}
				}

			}
			if (i == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[modSysMenu] Fail: ", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 删除菜单信息
	 * 先判断是否存在
	 * 判断是否有子菜单 有子菜单删除失败
	 *
	 * @return SysMenuDTO
	 */

	@Override
	@Transactional
	public DTO delSysMenuByNo(String no) {
		log.info("------------[delSysMenu]SysMenuService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			//是否存在
			SysMenu sysMenu = sysMenuMapper.qryMenuDetailByMenuNo(no);
			if (sysMenu == null) {
				return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}

			//是否有子菜单
			int i = sysMenuMapper.qryMenuDetailByMenuFateher(no);
			if (i > 0) {
				dto.setResult(RetCodeEnum.FAIL);
			}

			//删除
			int y = sysMenuMapper.deletePermissionNoByMenuNo(no);
			if (y != -1) {

				if (sysMenuMapper.delMenuByNo(no) != -1) {
					dto.setResult(RetCodeEnum.SUCCEED);
				}
			}

		} catch (Exception e) {
			log.error("[delMenuByNo] Fail: ", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}


}
