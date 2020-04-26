package com.zjft.microservice.treasurybrain.linecenter.mapstruct;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineRunInfo;
import com.zjft.microservice.treasurybrain.linecenter.domain.LineWorkDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LineRunConverter {

	LineRunConverter INSTANCE = Mappers.getMapper(LineRunConverter.class);
	@Mappings({
	})
	LineWorkTableDTO domain2dto(LineWorkDO lineRunInfo);

	LineWorkDO  domain2dto(LineWorkTableDTO lineWorkTableDTO);

}
