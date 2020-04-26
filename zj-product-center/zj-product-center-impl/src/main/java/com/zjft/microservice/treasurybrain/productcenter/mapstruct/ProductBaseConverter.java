package com.zjft.microservice.treasurybrain.productcenter.mapstruct;

import com.zjft.microservice.treasurybrain.productcenter.dto.ProductBaseTableDTO;
import com.zjft.microservice.treasurybrain.productcenter.po.ProductBaseTablePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/09/03
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductBaseConverter {

	ProductBaseConverter INSTANCE = Mappers.getMapper(ProductBaseConverter.class);

	@Mappings({
	})
	ProductBaseTableDTO do2dto(ProductBaseTablePO productBaseTablePO);

	ProductBaseTablePO dto2do(ProductBaseTableDTO productBaseTableDTO);

	List<ProductBaseTableDTO> do2dto(List<ProductBaseTablePO> productBaseTablePOList);

	List<ProductBaseTablePO> dto2do(List<ProductBaseTableDTO> productBaseTableDTOList);
}
