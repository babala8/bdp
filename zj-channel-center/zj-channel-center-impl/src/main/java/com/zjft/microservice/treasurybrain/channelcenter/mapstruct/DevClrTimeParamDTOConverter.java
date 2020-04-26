package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.DevClrTimeParamDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevClrTimeParamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/17 14:40
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DevClrTimeParamDTOConverter {

	DevClrTimeParamDTOConverter INSTANCE = Mappers.getMapper(DevClrTimeParamDTOConverter.class);

	@Mappings({

	})

	List<DevClrTimeParamDTO> do2dto(List<DevClrTimeParamDO> devClrTimeParamDOS);

}
