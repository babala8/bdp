package com.zjft.microservice.treasurybrain.business.mapstruct;

import com.zjft.microservice.treasurybrain.business.domain.LineRunInfo;
import com.zjft.microservice.treasurybrain.business.dto.LineRunInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LineRunDTOConverter {

	LineRunDTOConverter INSTANCE = Mappers.getMapper(LineRunDTOConverter.class);
	@Mappings({
	})
	LineRunInfoDTO domain2dto(LineRunInfo lineRunInfo);

	LineRunInfo  domain2dto(LineRunInfoDTO lineRunInfoDTO);

}
