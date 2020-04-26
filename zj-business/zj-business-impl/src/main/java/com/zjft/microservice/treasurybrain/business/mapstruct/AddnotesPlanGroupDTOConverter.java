package com.zjft.microservice.treasurybrain.business.mapstruct;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanGroup;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanGroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddnotesPlanGroupDTOConverter {
	AddnotesPlanGroupDTOConverter INSTANCE = Mappers.getMapper(AddnotesPlanGroupDTOConverter.class);

	@Mappings({
	})
	AddnotesPlanGroupDTO domain2dto(AddnotesPlanGroup addnotesPlanGroup);
}
