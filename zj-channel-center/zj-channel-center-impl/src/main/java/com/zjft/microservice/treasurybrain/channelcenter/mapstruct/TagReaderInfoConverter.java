package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.dto.TagReaderInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.po.TagReaderInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagReaderInfoConverter {

	TagReaderInfoConverter INSTANCE = Mappers.getMapper(TagReaderInfoConverter.class);

	@Mappings({
	})
	TagReaderInfoDTO domain2dto(TagReaderInfoPO domain);

	TagReaderInfoPO dto2domain(TagReaderInfoDTO dto);

	List<TagReaderInfoDTO> domain2dto(List<TagReaderInfoPO> listDO);
}
