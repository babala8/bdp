package com.zjft.microservice.treasurybrain.lock.mapstruct;

import com.zjft.microservice.treasurybrain.lock.domain.LockTransTableDO;
import com.zjft.microservice.treasurybrain.lock.dto.LockTransTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 韩通
 * @since 2019-06-26
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LockTransTableConverter {

	LockTransTableConverter INSTANCE = Mappers.getMapper(LockTransTableConverter.class);

	@Mappings({
	})
	LockTransTableDO dto2domain(LockTransTableDTO lockTransTableDTO);

	@Mappings({
	})
	LockTransTableDTO domain2dto(LockTransTableDO lockTransTableDO);

	List<LockTransTableDO> dto2domain(List<LockTransTableDTO> lockTransTableDTOList);

	List<LockTransTableDTO> domain2dto(List<LockTransTableDO> lockTransTableDOList);
}
