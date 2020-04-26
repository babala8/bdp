package com.zjft.microservice.treasurybrain.productcenter.mapstruct;

import com.zjft.microservice.treasurybrain.productcenter.domain.ServiceDO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceConverter {

	ServiceConverter INSTANCE = Mappers.getMapper(ServiceConverter.class);

	@Mappings({
//			@Mapping(source = "serviceNo", target = "productNo"),
//			@Mapping(source = "serviceName", target = "productName")
	})
	ServiceDTO do2dto(ServiceDO serviceDO);

	List<ServiceDTO> do2dto(List<ServiceDO> serviceDOList);

}
