package com.zjft.microservice.treasurybrain.business.mapstruct;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanDetail;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddnotesPlanDetailDTOConverter {
	AddnotesPlanDetailDTOConverter INSTANCE = Mappers.getMapper(AddnotesPlanDetailDTOConverter.class);

	@Mappings({
//			@Mapping(source = "devStatusTable.devNo", target = "devNo"),
//			@Mapping(source = "devBaseInfo.devTypeTable.devCatalogTable.name", target = "devCatalogName"),
//			@Mapping(source = "devBaseInfo.address", target = "address"),
//			@Mapping(source = "devBaseInfo.addnotesLine", target = "devLineName"),
//			@Mapping(source = "addnoteLine.lineName", target = "lineName"),
//			@Mapping(source = "devStatusTable.availableAmt",target = "availableAmt"),
//			@Mapping(source = "devStatusTable.lastAddnoteDate",target = "lastAddnoteDate"),
//			@Mapping(source = "devBaseInfo.sysOrg.name",target = "orgName")
	})
	AddnotesPlanDetailDTO domain2dto(AddnotesPlanDetail addnotesPlanDetail);

	List<AddnotesPlanDetailDTO> domain2dto(List<AddnotesPlanDetail> addnotesPlanDetailList);
}
