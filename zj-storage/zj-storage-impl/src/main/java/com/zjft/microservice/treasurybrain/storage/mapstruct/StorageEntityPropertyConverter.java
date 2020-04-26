package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityPropertyDTO;
import com.zjft.microservice.treasurybrain.storage.po.StorageEntityPropertyPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 崔耀中
 * @since 2020/3/2
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorageEntityPropertyConverter {

	StorageEntityPropertyConverter INSTANCE = Mappers.getMapper(StorageEntityPropertyConverter.class);
	@Mappings({

	})
	StorageEntityPropertyPO dto2po (StorageEntityPropertyDTO storageEntityPropertyDTO);

	List<StorageEntityPropertyDTO> po2dto(List<StorageEntityPropertyPO> storageEntityPropertyPOS);

	StorageEntityPropertyDTO po2dto(StorageEntityPropertyPO storageEntityPropertyPO);

}
