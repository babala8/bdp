package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevServiceCompanyDTO;
import com.zjft.microservice.treasurybrain.common.domain.DevServiceCompany;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DevServiceCompanyConverter {
	DevServiceCompanyConverter INSTANCE = Mappers.getMapper(DevServiceCompanyConverter.class);


	@Mappings({
	})
	DevServiceCompanyDTO domain2dto(DevServiceCompany domain);

	@Mapping(source = "type", target = "type")
	DevServiceCompany dto2domain(DevServiceCompanyDTO dto);

	List<DevServiceCompanyDTO> domain2dto(List<DevServiceCompany> devServiceCompanyList);
}
