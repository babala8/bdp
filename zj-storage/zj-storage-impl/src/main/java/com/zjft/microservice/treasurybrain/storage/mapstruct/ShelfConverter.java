package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.dto.ShelfDTO;
import com.zjft.microservice.treasurybrain.storage.po.StorageShelfTablePO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author 常 健
 * @since 2020/1/2
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShelfConverter {

	ShelfConverter INSTANCE = Mappers.getMapper(ShelfConverter.class);

	StorageShelfTablePO dto2po(ShelfDTO shelfDTO);
}

