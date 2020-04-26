package com.zjft.microservice.treasurybrain.managecenter.mapstruct;

import com.zjft.microservice.treasurybrain.managecenter.domain.BaseEntityInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 葛瑞莲
 * @since 2019/9/11
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseEntityConverter {
	BaseEntityConverter INSTANCE = Mappers.getMapper(BaseEntityConverter.class);

	@Mappings({
	})
	List<BaseEntityInfoDTO> domain2dto(List<BaseEntityInfoDO> domain);
}
