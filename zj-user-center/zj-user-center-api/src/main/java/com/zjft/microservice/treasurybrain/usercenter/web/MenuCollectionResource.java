package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/14 14:49
 */
@Api(value = "用户中心：快捷菜单收藏", tags = {"用户中心：快捷菜单收藏"})
public interface MenuCollectionResource {

	String PREFIX = "${user-center:}/v2/menuCollect";

	/**
	 * 查询当前用户收藏的菜单列表
	 * 注意:排除用户权限外的菜单，比如先前用户已经收藏，后来失去该菜单权限
	 * @return 收藏的菜单列表
	 */
	@GetMapping(path = PREFIX)
	@ApiOperation(value = "查询当前用户收藏的菜单列表",notes = "查询当前用户收藏的菜单列表")
	ListDTO<MenuDTO> qryCollectedMenus();

	/**
	 * 更新当前用户收藏的菜单列表
	 *
	 * @param menuNos 收藏的菜单编号列表
	 * @return 结果
	 */
	@PostMapping(path = PREFIX)
	@ApiOperation(value = "更新当前用户收藏的菜单列表",notes = "更新当前用户收藏的菜单列表")
	DTO updateCollectedMenus(@RequestBody List<String> menuNos);


}
