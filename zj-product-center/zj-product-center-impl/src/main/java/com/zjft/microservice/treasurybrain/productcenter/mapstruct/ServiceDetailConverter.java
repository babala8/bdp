package com.zjft.microservice.treasurybrain.productcenter.mapstruct;

import com.zjft.microservice.treasurybrain.productcenter.domain.ServiceDO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceDetailConverter {

	ServiceDetailConverter INSTANCE = Mappers.getMapper(ServiceDetailConverter.class);

	@Mappings({
//			@Mapping(source = "serviceNo", target = "productNo"),
//			@Mapping(source = "serviceName", target = "productName")
	})
	ServiceDetailDTO do2detaildto(ServiceDO serviceDO);

//	SelfServiceDetailDTO do2detaildtos(ServiceDO productDO);
}
