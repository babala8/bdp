package com.zjft.microservice.treasurybrain.business.mapstruct;

import com.zjft.microservice.treasurybrain.business.domain.AddnoteLine;
import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlan;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesLineDTO;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddnotesPlanDTOConverter {
	AddnotesPlanDTOConverter INSTANCE = Mappers.getMapper(AddnotesPlanDTOConverter.class);

	@Mappings({
//			@Mapping(source = "clrCenterTable.centerName", target = "clrCenterName"),
//			@Mapping(source = "planGenUser.name", target = "planGenOpName"),
			@Mapping(source = "addnoteLineList", target = "lineList"),
//			@Mapping(source = "auditOpUser.name",target = "auditOpName")
			})
	AddnotesPlanDTO domain2dto(AddnotesPlan addnotesPlan);

	List<AddnotesPlanDTO> domain2dto(List<AddnotesPlan> addnotesPlanList);

	default List<AddnotesLineDTO> lineDOToDTO(List<AddnoteLine> addnoteLineList) {
		return AddnotesLineDTOConverter.INSTANCE.domain2dto(addnoteLineList);
	}
}
