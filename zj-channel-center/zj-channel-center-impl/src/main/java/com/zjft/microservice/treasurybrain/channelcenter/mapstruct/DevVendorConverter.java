package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevVendorDTO;
import com.zjft.microservice.treasurybrain.common.domain.DevVendorTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DevVendorConverter {

	DevVendorConverter INSTANCE = Mappers.getMapper(DevVendorConverter.class);


	@Mappings({
	})
	DevVendorDTO domain2dto(DevVendorTable domain);

	DevVendorTable dto2domain(DevVendorDTO dto);

	List<DevVendorDTO> domain2dto(List<DevVendorTable> clrCenterTableList);
}
