package com.zjft.microservice.treasurybrain.business.mapstruct;

import com.zjft.microservice.treasurybrain.business.domain.AddnoteLine;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesLineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddnotesLineDTOConverter {
	AddnotesLineDTOConverter INSTANCE = Mappers.getMapper(AddnotesLineDTOConverter.class);
	@Mappings({
	})
	AddnotesLineDTO domain2dto(AddnoteLine addnoteLine);

	List<AddnotesLineDTO> domain2dto(List<AddnoteLine> addnoteLineList);
}
