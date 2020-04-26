package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.domain.StorageSortedTransferDetailDO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/30 11:16
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorageSortedTransferDetailConverter {

	StorageSortedTransferDetailConverter INSTANCE = Mappers.getMapper(StorageSortedTransferDetailConverter.class);

	@Mappings({

	})
	List<StorageSortedTransferEntityDTO> do2dto(List<StorageSortedTransferDetailDO> storageSortedTransferDetailDOS);
}
