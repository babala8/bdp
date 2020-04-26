package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevTypeDTO;
import com.zjft.microservice.treasurybrain.common.domain.DevTypeTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 张笑
 * @since 2019-04-08
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DevTypeConverter {

	DevTypeConverter INSTANCE = Mappers.getMapper(DevTypeConverter.class);

	@Mappings({
	})
	DevTypeDTO domain2dto(DevTypeTable domain);

	DevTypeTable dto2domain(DevTypeDTO dto);

	List<DevTypeDTO> domain2dto(List<DevTypeTable> devTypeTables);
}
