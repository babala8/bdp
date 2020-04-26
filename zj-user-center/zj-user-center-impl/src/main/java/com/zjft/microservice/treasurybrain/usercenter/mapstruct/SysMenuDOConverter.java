package com.zjft.microservice.treasurybrain.usercenter.mapstruct;


import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysMenu;
import com.zjft.microservice.treasurybrain.usercenter.dto.MenusDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.po.SysMenuPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 张弛
 * @since 2020-01-14
 */
@Mapper
public interface SysMenuDOConverter {
	SysMenuDOConverter INSTANCE = Mappers.getMapper(SysMenuDOConverter.class);

	@Mappings({
	})
	SysMenuPO dto2po(SysMenuDTO sysMenuDTO);

	SysMenuDTO do2dto(SysMenu sysMenu);

	List<SysMenuDTO> po2dto(List<SysMenu> po);

	List<MenusDTO> domain2dto(List<SysMenu> domain);

	List<SysMenu> dto2do(List<SysMenuDTO> sysMenuDTO);


//	SysMenuDTO po2dto(SysMenu sysMenu);


}
