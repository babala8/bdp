package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import com.zjft.microservice.treasurybrain.common.domain.SysUserDO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
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
public interface SysUserConverter {

	SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

	@Mappings({
			@Mapping(target = "password", ignore = true),
			@Mapping(target = "passwordError", ignore = true),
			@Mapping(target = "passwordExpiration", ignore = true),
			@Mapping(target = "newPassword", ignore = true),
			@Mapping(target = "loginTerm", ignore = true),
			@Mapping(target = "loginTime", ignore = true),
			@Mapping(target = "loginIp", ignore = true),
	})
	UserDTO domain2dto(SysUserDO domain);


	@Mappings({
			@Mapping(target = "passwordError", ignore = true),
			@Mapping(target = "passwordExpiration", ignore = true),
			@Mapping(target = "status", ignore = true),
			@Mapping(target = "loginTerm", ignore = true),
			@Mapping(target = "loginTime", ignore = true),
			@Mapping(target = "loginIp", ignore = true),
	})
	SysUserDO dto2do(UserDTO domain);

	@Mappings({
			@Mapping(source = "menuIcon", target = "icon"),
			@Mapping(source = "menuSize", target = "size"),
			@Mapping(source = "menuBg", target = "backgroundColor"),
	})
	MenuDTO domain2dto(SysMenuDO domain);

	List<UserDTO> domain2dto(List<SysUserDO> domain);

	List<SysUserDO> dto2do(List<UserDTO> domain);

	@Mappings({})
	SysOrgDTO domain2dto(SysOrg domain);
}

