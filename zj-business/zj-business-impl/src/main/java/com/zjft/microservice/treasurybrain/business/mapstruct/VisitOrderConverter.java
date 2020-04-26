package com.zjft.microservice.treasurybrain.business.mapstruct;

import com.zjft.microservice.treasurybrain.business.domain.VisitOrderDO;
import com.zjft.microservice.treasurybrain.business.dto.VisitOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitOrderConverter {
	VisitOrderConverter INSTANCE = Mappers.getMapper(VisitOrderConverter.class);
	@Mappings({
	})
	VisitOrderDTO do2dto(VisitOrderDO visitOrderDO);

	@Mappings({
	})
	VisitOrderDO do2dto(VisitOrderDTO visitOrderDTO);

	List<VisitOrderDTO> do2dto(List<VisitOrderDO> productDOList);
}
