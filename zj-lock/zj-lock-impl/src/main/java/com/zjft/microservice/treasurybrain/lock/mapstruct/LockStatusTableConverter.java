package com.zjft.microservice.treasurybrain.lock.mapstruct;

import com.zjft.microservice.treasurybrain.lock.dto.LockStatusTableDTO;
import com.zjft.microservice.treasurybrain.lock.po.LockStatusTablePO;
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
public interface LockStatusTableConverter {

	LockStatusTableConverter INSTANCE = Mappers.getMapper(LockStatusTableConverter.class);

	@Mappings({
	})
	LockStatusTablePO dto2domain(LockStatusTableDTO lockStatusTableDTO);

	@Mappings({
	})
	LockStatusTableDTO domain2dto(LockStatusTablePO lockStatusTablePO);

	List<LockStatusTablePO> dto2domain(List<LockStatusTableDTO> lockStatusTableDTOList);

	List<LockStatusTableDTO> domain2dto(List<LockStatusTablePO> lockStatusTablePOList);
}
