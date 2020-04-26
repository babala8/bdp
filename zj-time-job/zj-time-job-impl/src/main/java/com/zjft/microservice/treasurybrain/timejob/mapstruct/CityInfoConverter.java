package com.zjft.microservice.treasurybrain.timejob.mapstruct;

import com.zjft.microservice.treasurybrain.timejob.dto.CityInfoDTO;
import com.zjft.microservice.treasurybrain.timejob.po.CityInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/07/16
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityInfoConverter {

	CityInfoConverter INSTANCE = Mappers.getMapper(CityInfoConverter.class);

	@Mappings({

	})
	CityInfoDTO domain2dto(CityInfoPO cityInfoPO);

	List<CityInfoDTO> domain2dto(List<CityInfoPO> cityInfoPOList);
}
