package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.domain.StorageSortedTransferDetailDO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferEntityDTO;
import com.zjft.microservice.treasurybrain.storage.po.StorageEntityTablePO;
import com.zjft.microservice.treasurybrain.storage.po.StorageTransferEntityPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/28 15:49
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorageSortedTransferEntityConverter {

	StorageSortedTransferEntityConverter INSTANCE = Mappers.getMapper(StorageSortedTransferEntityConverter.class);
	@Mappings({

	})
	StorageTransferEntityPO dto2po(StorageSortedTransferEntityDTO storageSortedTransferEntityDTO);

	List<StorageSortedTransferEntityDTO> do2dto(List<StorageSortedTransferDetailDO> storageSortedTransferDetailDOS);
	StorageSortedTransferEntityDTO do2dto(StorageSortedTransferDetailDO storageSortedTransferDetailDO);
	StorageEntityTablePO transferEntityPo2EntityPo(StorageTransferEntityPO storageTransferEntityPO);
 }
