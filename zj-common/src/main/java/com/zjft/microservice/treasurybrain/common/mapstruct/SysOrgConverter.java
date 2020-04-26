package com.zjft.microservice.treasurybrain.common.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
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
public interface SysOrgConverter {

	SysOrgConverter INSTANCE = Mappers.getMapper(SysOrgConverter.class);


	@Mappings({
	})
	SysOrgDTO domain2dto(SysOrg domain);

	@Mappings({
			@Mapping(source = "deliveryTime", target = "deliveryTime"),
			@Mapping(source = "backTime", target = "backTime")
	})
	SysOrg dto2domain(SysOrgDTO dto);

	List<SysOrgDTO> domain2dto(List<SysOrg> addnoteLineList);

}

