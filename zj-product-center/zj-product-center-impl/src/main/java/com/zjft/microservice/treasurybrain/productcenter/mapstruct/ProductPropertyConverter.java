package com.zjft.microservice.treasurybrain.productcenter.mapstruct;

import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyKeyValueDTO;
import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyKeyPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductPropertyConverter {
	ProductPropertyConverter INSTANCE = Mappers.getMapper(ProductPropertyConverter.class);

	@Mappings({
	})
	ProductPropertyKeyPO do2dto(ProductPropertyKeyValueDTO productPropertyKeyValueDTO);

}
