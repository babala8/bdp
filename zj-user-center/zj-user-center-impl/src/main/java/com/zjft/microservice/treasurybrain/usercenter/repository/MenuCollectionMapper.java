package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/8/14 16:32
 */

public interface MenuCollectionMapper {

	/**
	 * 查询用户收藏的菜单
	 *
	 * @return 查询结果
	 */
	List<SysMenuDO> qryCollectedMenus(String userName);

	/**
	 * 删除用户收藏菜的单
	 *
	 * @param userName 用户名
	 * @return 删除结果
	 */
	int deleteByUserNo(String userName);

	/**
	 *
	 *
	 * @param paramMap 参数列表
	 * @return 插入结果
	 */
	int insert(Map<String,Object> paramMap);
}
