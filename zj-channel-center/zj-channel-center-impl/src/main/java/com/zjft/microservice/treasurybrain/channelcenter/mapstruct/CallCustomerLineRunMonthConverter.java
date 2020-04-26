package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerLineRunMonthDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerLineRunMonthDTO;
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

	CallCustomerLineRunMonthDTO do2dto(CallCustomerLineRunMonthDO callCustomerLineRunMonthDO);

	List<CallCustomerLineRunMonthDTO> do2dto(List<CallCustomerLineRunMonthDO> callCustomerLineRunMonthDOs);
}
