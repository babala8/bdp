package com.zjft.microservice.treasurybrain.timejob.mapstruct;

import com.zjft.microservice.treasurybrain.timejob.dto.CityWeatherDTO;
import com.zjft.microservice.treasurybrain.timejob.po.CityWeatherPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author 常 健
 * @since 2019/07/16
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityWeatherConverter {

	CityWeatherConverter INSTANCE = Mappers.getMapper(CityWeatherConverter.class);

	@Mappings({

	})
	CityWeatherDTO domain2dto(CityWeatherPO cityWeatherPO);

	//List<CityWeatherDTO> domain2dto(List<CityWeatherPO> cityWeatherPO);

	CityWeatherPO dto2domain(CityWeatherDTO cityWeatherDTO);

	//List<CityWeatherPO> doList2dtoList(List<CityWeatherDTO> cityWeatherDTO);
}
