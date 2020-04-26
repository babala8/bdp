package com.zjft.microservice.treasurybrain.linecenter.mapstruct;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineWorkDO;
import com.zjft.microservice.treasurybrain.linecenter.domain.NetworkLineRunInfo;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetworkLineRunInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NetworkLineRunConverter {

	NetworkLineRunConverter INSTANCE = Mappers.getMapper(NetworkLineRunConverter.class);
	@Mappings({
	})
	LineWorkTableDTO domain2dto(LineWorkDO lineRunInfo);

	LineWorkDO  domain2dto(LineWorkTableDTO lineRunInfoDTO);

}
