package com.zjft.microservice.treasurybrain.tauro.mapstruct;

import com.zjft.microservice.treasurybrain.tauro.domain.TagReaderCoordsInfoDO;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderCoordsDTO;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderCoordsInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagReaderCoordsConverter {
	TagReaderCoordsConverter INSTANCE = Mappers.getMapper(TagReaderCoordsConverter.class);

	@Mappings({

	})
	TagReaderCoordsInfoPO dto2po(TagReaderCoordsDTO tagReaderCoordsDTO);

	@Mappings({

	})
	TagReaderCoordsDTO domain2dto(TagReaderCoordsInfoDO tagReaderCoordsInfoDO);

	List<TagReaderCoordsDTO> domain2dto(List<TagReaderCoordsInfoDO> tagReaderCoordsInfoDOList);
}
