package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.domain.StorageEntityTableDO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedEntityDTO;
import com.zjft.microservice.treasurybrain.storage.po.StorageEntityTablePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/29 10:34
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorageSortedEntityConverter {

	StorageSortedEntityConverter INSTANCE = Mappers.getMapper(StorageSortedEntityConverter.class);
	@Mappings({

	})
	StorageEntityTablePO dto2po (StorageSortedEntityDTO storageSortedEntityDTO);

	List<StorageSortedEntityDTO> po2dto(List<StorageEntityTablePO> storageEntityTablePOS);

	StorageSortedEntityDTO po2dto(StorageEntityTablePO storageEntityTablePO);

	StorageSortedEntityDTO domain2dto(StorageEntityTableDO storageEntityTableDO);

}
