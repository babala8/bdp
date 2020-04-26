package com.zjft.microservice.treasurybrain.tauro.mapstruct;

import com.zjft.microservice.treasurybrain.tauro.domain.TagReaderUseInfoDO;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderUseDTO;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderUseInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagReaderUseConverter {

	TagReaderUseConverter INSTANCE = Mappers.getMapper(TagReaderUseConverter.class);

	@Mappings({
	})
	TagReaderUseDTO domain2dto(TagReaderUseInfoDO tagReaderUseInfoDO);

	@Mappings({
	})
	List<TagReaderUseDTO> domain2dto(List<TagReaderUseInfoDO> list);

	TagReaderUseInfoPO dto2domain(TagReaderUseDTO tagReaderUseDTO);
}
