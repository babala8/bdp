package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;


import com.zjft.microservice.treasurybrain.channelcenter.domain.TagInfoDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.TagDTO;
import com.zjft.microservice.treasurybrain.channelcenter.po.TagInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 葛瑞莲
 * @since 2019/6/11
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagConverter {
	TagConverter INSTANCE = Mappers.getMapper(TagConverter.class);

	@Mappings({
	})
	List<TagDTO> domain2dto(List<TagInfoDO> tagList);

	TagInfoPO dto2domain(TagDTO tagDTO);
}
