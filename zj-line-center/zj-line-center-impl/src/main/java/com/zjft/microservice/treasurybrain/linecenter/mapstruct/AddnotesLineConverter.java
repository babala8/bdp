package com.zjft.microservice.treasurybrain.linecenter.mapstruct;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineTableDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddnotesLineConverter {
	AddnotesLineConverter INSTANCE = Mappers.getMapper(AddnotesLineConverter.class);
	@Mappings({
	})
	LineTableDTO domain2dto(LineTableDO addnoteLine);

	List<LineTableDTO> domain2dto(List<LineTableDO> addnoteLineList);
}
