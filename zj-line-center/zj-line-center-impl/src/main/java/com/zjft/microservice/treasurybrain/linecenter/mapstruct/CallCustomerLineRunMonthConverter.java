package com.zjft.microservice.treasurybrain.linecenter.mapstruct;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineWorkDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/23 17:30
 */

@Mapper
public interface CallCustomerLineRunMonthConverter {

	CallCustomerLineRunMonthConverter INSTANCE = Mappers.getMapper(CallCustomerLineRunMonthConverter.class);

	@Mappings({

	})
	LineWorkTableDTO do2dto(LineWorkDO callCustomerLineRunMonthDO);

	List<LineWorkTableDTO> do2dto(List<LineWorkDO> callCustomerLineRunMonthDOs);
}
