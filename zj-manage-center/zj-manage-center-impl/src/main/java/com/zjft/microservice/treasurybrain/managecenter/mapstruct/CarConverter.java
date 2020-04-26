package com.zjft.microservice.treasurybrain.managecenter.mapstruct;


import com.zjft.microservice.treasurybrain.managecenter.domain.CarInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.dto.CarDTO;
import com.zjft.microservice.treasurybrain.managecenter.po.CarInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 崔耀中
 * @since 2019/9/21
 */
@Mapper
public interface CarConverter {
	CarConverter INSTANCE = Mappers.getMapper(CarConverter.class);

	@Mappings({
			//@Mapping(source = "companyName", target = "companyName")

	})

	//CarDTO domain2dto(CarInfoDO carDTO);

	List<CarDTO> domain2dto(List<CarInfoDO> carList);

	CarInfoPO dto2domain(CarDTO carDTO);

	List<CarDTO> po2dto(List<CarInfoPO> carInfoPOS);
}
