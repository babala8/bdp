package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.DevCatalogTable;
import com.zjft.microservice.treasurybrain.common.dto.DevCatalogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DevCatalogConverter {

	DevCatalogConverter INSTANCE = Mappers.getMapper(DevCatalogConverter.class);

	@Mappings({
	})
	DevCatalogDTO domain2dto(DevCatalogTable domain);

	List<DevCatalogDTO> domain2dto(List<DevCatalogTable> domain);
}
