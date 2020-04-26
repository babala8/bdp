package com.zjft.microservice.treasurybrain.storage.mapstruct;

import com.zjft.microservice.treasurybrain.storage.domain.StorageCheckDO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageCheckDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/10/11
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorageCheckConverter {

	StorageCheckConverter INSTANCE = Mappers.getMapper(StorageCheckConverter.class);

	@Mappings({

	})
	StorageCheckDO dto2domain(StorageCheckDTO dto);

	StorageCheckDTO domain2dto(StorageCheckDO domain);

	List<StorageCheckDO> dto2domain(List<StorageCheckDTO> listDTO);

	List<StorageCheckDTO> domain2dto(List<StorageCheckDO> listPO);
}
