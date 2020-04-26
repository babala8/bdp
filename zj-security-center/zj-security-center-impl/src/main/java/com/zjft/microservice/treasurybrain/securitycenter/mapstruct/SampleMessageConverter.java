package com.zjft.microservice.treasurybrain.securitycenter.mapstruct;

import com.zjft.microservice.treasurybrain.securitycenter.domain.SampleMessageInfo;
import com.zjft.microservice.treasurybrain.securitycenter.dto.SecurityMessageResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SampleMessageConverter {

	SampleMessageConverter INSTANCE = Mappers.getMapper(SampleMessageConverter.class);
	@Mappings({
	})
	SecurityMessageResponseDTO domain2dto(SampleMessageInfo sampleMessageInfo);

	List<SecurityMessageResponseDTO> domain2dto(List<SampleMessageInfo> sampleMessageInfoList);

	SampleMessageInfo  domain2dto(SecurityMessageResponseDTO securityMessageResponseDTO);

}
