package com.zjft.microservice.treasurybrain.usercenter.service.impl;

import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysMenuConverter;
import com.zjft.microservice.treasurybrain.usercenter.repository.MenuCollectionMapper;
import com.zjft.microservice.treasurybrain.usercenter.service.MenuCollectionService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单收藏服务
 *
 * @author liuyuan
 * @since 2019/8/14 15:50
 */
@Slf4j
@Service
public class MenuCollectionServiceImpl implements MenuCollectionService {

	@Resource
	SysUserResource sysUserResource;

	@Resource
	MenuCollectionMapper menuCollectionMapper;

	/**
	 * 查询当前用户收藏的菜单列表
	 *
	 * 注意:排除用户权限外的菜单，比如先前用户已经收藏，后来失去该菜单权限
	 * @return 收藏的菜单列表
	 */
	@Override
	public ListDTO<MenuDTO> qryCollectedMenus() {
		String userName = sysUserResource.getAuthUserInfo().getUsername();
		List<SysMenuDO> sysMenuDOS = menuCollectionMapper.qryCollectedMenus(userName);
		List<MenuDTO> menuDTOS = SysMenuConverter.INSTANCE.domain2dto(sysMenuDOS);
		ListDTO retList = new ListDTO(RetCodeEnum.SUCCEED);
		retList.setRetList(menuDTOS);
		return retList;
	}

	/**
	 * 覆盖更新当前用户收藏的菜单列表
	 *
	 * @param menuNos 收藏的菜单编号列表
	 * @return 结果
	 */
	@Override
	public DTO updateCollectedMenus(List<String> menuNos) {
		String userName = sysUserResource.getAuthUserInfo().getUsername();
		menuCollectionMapper.deleteByUserNo(userName);

		for(String menuNo : menuNos){
			Map<String,Object> paramsMap = new HashMap<>(2);
			paramsMap.put("userName",userName);
			paramsMap.put("menuNo",menuNo);
			menuCollectionMapper.insert(paramsMap);
		}

		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		return dto;
	}


}
