package com.zjft.microservice.treasurybrain.param.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysParam;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysParamConverter {
	SysParamConverter INSTANCE = Mappers.getMapper(SysParamConverter.class);

	@Mappings({
	})
	SysParamDTO domain2dto(SysParam sysParam);

	SysParam dto2do(SysParamDTO sysParamDTO);

	List<SysParamDTO> domain2dto(List<SysParam> sysParamList);
}
