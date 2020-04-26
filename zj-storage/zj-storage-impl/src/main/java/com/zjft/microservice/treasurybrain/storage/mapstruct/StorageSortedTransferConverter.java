package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.domain.StorageSortedTransferTableDO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferDTO;
import com.zjft.microservice.treasurybrain.storage.po.StorageTransferTablePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/27 16:42
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorageSortedTransferConverter {

	StorageSortedTransferConverter INSTANCE = Mappers.getMapper(StorageSortedTransferConverter.class);

	@Mappings({

	})
	StorageTransferTablePO dto2po(StorageSortedTransferDTO storageSortedTransferDTO);

	List<StorageSortedTransferDTO> dos2dtos(List<StorageSortedTransferTableDO> storageSortedTransferDOS);

}
