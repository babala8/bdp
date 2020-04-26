package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysMenu;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMenuConverter {

	SysMenuConverter INSTANCE = Mappers.getMapper(SysMenuConverter.class);


	@Mappings({
			@Mapping(source = "menuIcon", target = "icon"),
			@Mapping(source = "menuSize", target = "size"),
			@Mapping(source = "menuBg", target = "backgroundColor"),
	})
	MenuDTO domain2dto(SysMenuDO domain);

	@Mappings({
			@Mapping(source = "icon", target = "menuIcon"),
			@Mapping(source = "size", target = "menuSize"),
			@Mapping(source = "backgroundColor", target = "menuBg"),
	})
	SysMenuDO dto2do(MenuDTO domain);


	List<MenuDTO> domain2dto(List<SysMenuDO> domain);
	
	List<SysMenuDO> dto2do(List<MenuDTO> domain);


}

