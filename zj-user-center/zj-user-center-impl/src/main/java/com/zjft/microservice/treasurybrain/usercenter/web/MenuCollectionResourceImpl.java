package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.usercenter.service.MenuCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/14 15:32
 */
@Slf4j
@RestController
public class MenuCollectionResourceImpl implements MenuCollectionResource {

	@Resource
	MenuCollectionService menuCollectionService;

	@Override
	public ListDTO<MenuDTO> qryCollectedMenus() {

		try{
			return menuCollectionService.qryCollectedMenus();
		}catch (Exception e){
			log.error("查询用户收藏的菜单发生异常",e);
			ListDTO<MenuDTO> dtoListDTO = new ListDTO<>(RetCodeEnum.EXCEPTION);
			return dtoListDTO;
		}
	}

	/**
	 * 更新当前用户收藏的菜单列表
	 *
	 * @param menuNos 收藏的菜单编号列表
	 * @return 结果
	 */
	@Override
	public DTO updateCollectedMenus(List<String> menuNos) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try{
			dto = menuCollectionService.updateCollectedMenus(menuNos);
		}catch (Exception e){
			log.error("更新用户收藏的菜单时发生异常",e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}
}
