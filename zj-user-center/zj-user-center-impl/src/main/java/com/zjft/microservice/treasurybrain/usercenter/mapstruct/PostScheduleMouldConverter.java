package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleMouldDO;
import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleMouldDetailDO;
import com.zjft.microservice.treasurybrain.usercenter.dto.PostScheduleMouldDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/18
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostScheduleMouldConverter {

	PostScheduleMouldConverter INSTANCE = Mappers.getMapper(PostScheduleMouldConverter.class);

	@Mappings({

	})

	List<PostScheduleMouldDTO> do2dto(List<PostScheduleMouldDO> doList);

	PostScheduleMouldDetailDO dto2do(PostScheduleMouldDTO postScheduleMouldDTO);
}
