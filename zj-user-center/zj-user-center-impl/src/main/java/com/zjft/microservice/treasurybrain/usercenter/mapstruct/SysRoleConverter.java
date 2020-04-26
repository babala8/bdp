package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysRoleDO;
import com.zjft.microservice.treasurybrain.common.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleConverter {

	SysRoleConverter INSTANCE = Mappers.getMapper(SysRoleConverter.class);


	@Mappings({})
	RoleDTO domain2dto(SysRoleDO domain);

	@Mappings({})
	SysRoleDO dto2do(RoleDTO domain);

	List<SysRoleDO> dto2do(List<RoleDTO> domain);

	List<RoleDTO> domain2dto(List<SysRoleDO> domain);
}

