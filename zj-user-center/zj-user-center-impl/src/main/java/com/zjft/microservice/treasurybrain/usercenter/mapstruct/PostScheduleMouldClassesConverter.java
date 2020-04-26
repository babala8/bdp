package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleMouldOpDO;
import com.zjft.microservice.treasurybrain.usercenter.dto.PostScheduleMouldOpDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/19
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostScheduleMouldClassesConverter {

	PostScheduleMouldClassesConverter INSTANCE = Mappers.getMapper(PostScheduleMouldClassesConverter.class);

	@Mappings({})
	List<PostScheduleMouldOpDO> dto2domain(List<PostScheduleMouldOpDTO> dtoList);
}
