package com.zjft.microservice.treasurybrain.datainsight.mapstruct;

import com.zjft.microservice.treasurybrain.datainsight.domain.InsightUserConfigDO;
import com.zjft.microservice.treasurybrain.datainsight.dto.InsightUserConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author 杨光
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InsightUserConfigConverter {

	InsightUserConfigConverter INSTANCE = Mappers.getMapper(InsightUserConfigConverter.class);

	@Mappings({
			@Mapping(source = "username", target = "username"),
			@Mapping(source = "userDefaultView", target = "userDefaultView"),
			@Mapping(source = "subjectId", target = "subjectId"),
			@Mapping(target = "retCode", ignore = true),
			@Mapping(target = "retMsg", ignore = true),
			@Mapping(target = "result", ignore = true)
	})
	InsightUserConfigDTO configToDto(InsightUserConfigDO configDo);

}

