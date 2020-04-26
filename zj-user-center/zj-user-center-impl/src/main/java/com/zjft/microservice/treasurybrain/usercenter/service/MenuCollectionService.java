package com.zjft.microservice.treasurybrain.usercenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/14 15:46
 */

public interface MenuCollectionService {

	/**
	 * 查询当前用户收藏的菜单列表
	 * 注意:排除用户权限外的菜单，比如先前用户已经收藏，后来失去该菜单权限
	 * @return 收藏的菜单列表
	 */
	ListDTO<MenuDTO> qryCollectedMenus();

	/**
	 * 覆盖更新当前用户收藏的菜单列表
	 *
	 * @param menuNos 收藏的菜单编号列表
	 * @return 结果
	 */
	DTO updateCollectedMenus(List<String> menuNos);
}
