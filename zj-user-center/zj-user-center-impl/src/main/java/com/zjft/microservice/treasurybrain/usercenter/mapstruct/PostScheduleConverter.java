package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleDO;
import com.zjft.microservice.treasurybrain.usercenter.dto.PostScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/20
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostScheduleConverter {

	PostScheduleConverter INSTANCE = Mappers.getMapper(PostScheduleConverter.class);

	@Mappings({})
	PostScheduleDO dto2domain(PostScheduleDTO postScheduleDTO);

	List<PostScheduleDTO> domain2dto(List<PostScheduleDO> postScheduleDOList);
}
