package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.domain.StorageCassetteBagDO;
import com.zjft.microservice.treasurybrain.storage.domain.StorageCassetteBagDetailDO;
import com.zjft.microservice.treasurybrain.storage.domain.StorageCassetteDO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/7
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorageCassetteBagConverter {

	StorageCassetteBagConverter INSTANCE = Mappers.getMapper(StorageCassetteBagConverter.class);

	List<StorageCassetteBagDO> dto2do(List<StorageUseCassetteBagDTO> list);

	List<StorageCassetteDO> dto2todo(List<StorageUseCassetteDTO> list);

	List<StorageCassetteBagDetailDO> detailDTO2DO(List<StorageUseCassetteBagDetailDTO> list);
}
