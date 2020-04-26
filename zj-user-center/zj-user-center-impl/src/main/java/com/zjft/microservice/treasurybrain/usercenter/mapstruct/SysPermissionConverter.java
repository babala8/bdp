package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysPermissionPO;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysMenu;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysPermissionDO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysPermissionConverter {

	SysPermissionConverter INSTANCE = Mappers.getMapper(SysPermissionConverter.class);


	@Mappings({
	})
	SysPermissionDTO po2dto(SysPermissionPO domain);

	@Mappings({
	})
	SysPermissionPO dto2po(SysPermissionDTO domain);


	List<SysPermissionDTO> po2dto(List<SysPermissionPO> domain);

	List<SysPermissionPO> dto2do(List<SysPermissionDTO> domain);

	List<SysPermissionDTO> domain2dto(List<SysPermissionDO> po);

}

