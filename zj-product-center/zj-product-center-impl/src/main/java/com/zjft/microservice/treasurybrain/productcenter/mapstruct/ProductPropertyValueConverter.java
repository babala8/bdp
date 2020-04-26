package com.zjft.microservice.treasurybrain.productcenter.mapstruct;

import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyValueDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyValuePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductPropertyValueConverter {

	ProductPropertyValueConverter INSTANCE = Mappers.getMapper(ProductPropertyValueConverter.class);

	@Mappings({
	})
	ProductPropertyValueDetailDTO do2dto(ProductPropertyValuePO productPropertyValuePO);

	List<ProductPropertyValueDetailDTO> do2dto(List<ProductPropertyValuePO> productPropertyValuePO);
}
