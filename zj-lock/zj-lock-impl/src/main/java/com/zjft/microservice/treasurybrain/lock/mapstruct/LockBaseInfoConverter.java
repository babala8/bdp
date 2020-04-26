package com.zjft.microservice.treasurybrain.lock.mapstruct;


import com.zjft.microservice.treasurybrain.lock.domain.LockBaseInfoDO;
import com.zjft.microservice.treasurybrain.lock.dto.LockBaseInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/06/26
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LockBaseInfoConverter {

	LockBaseInfoConverter INSTANCE = Mappers.getMapper(LockBaseInfoConverter.class);

	@Mappings({

	})
	LockBaseInfoDO dto2domain(LockBaseInfoDTO dto);

	LockBaseInfoDTO domain2do(LockBaseInfoDO domain);

	List<LockBaseInfoDO> dto2domain(List<LockBaseInfoDTO> dtoList);

	List<LockBaseInfoDTO> domain2dto(List<LockBaseInfoDO> doList);
}
