package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.domain.StorageEntityDO;
import com.zjft.microservice.treasurybrain.storage.domain.StorageEntityDetailDO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseEntityDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoodManageConverter {
	GoodManageConverter INSTANCE = Mappers.getMapper(GoodManageConverter.class);

	List<StorageEntityDTO> domain2dto(List<StorageEntityDO> list);

	StorageEntityDetailDTO domain2dto(StorageEntityDetailDO storageEntityDetailDO);

	List<StorageEntityDTO> dto2dto(List<StorageUseEntityDTO> list);

	StorageEntityDetailDO dto2do(StorageUseEntityDetailDTO storageUseEntityDetailDTO);
}
