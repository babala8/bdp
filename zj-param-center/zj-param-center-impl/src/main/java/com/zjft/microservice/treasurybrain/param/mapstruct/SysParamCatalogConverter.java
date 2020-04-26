package com.zjft.microservice.treasurybrain.param.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysParamCatalogDO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamCatalogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常健
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysParamCatalogConverter {
	SysParamCatalogConverter INSTANCE = Mappers.getMapper(SysParamCatalogConverter.class);

	@Mappings({
			@Mapping(source = "catalog", target = "catalog"),
			@Mapping(source = "catalogName", target = "catalogName"),
			@Mapping(source = "description", target = "description")
	})
	SysParamCatalogDTO domain2dto(SysParamCatalogDO sysParamCatalogDO);

	List<SysParamCatalogDTO> domain2dto(List<SysParamCatalogDO> sysParamCatalogDOList);
}
