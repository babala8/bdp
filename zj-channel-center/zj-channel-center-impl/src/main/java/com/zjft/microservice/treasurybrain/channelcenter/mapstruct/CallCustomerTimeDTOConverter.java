package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerTimeDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.po.CallCustomerTimePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/17 14:40
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CallCustomerTimeDTOConverter {

	CallCustomerTimeDTOConverter INSTANCE = Mappers.getMapper(CallCustomerTimeDTOConverter.class);

	@Mappings({

	})

	List<CallCustomerTimeDTO> do2dto(List<CallCustomerTimeDO> callCustomerTimeDOs);

	List<CallCustomerTimeDTO> po2dto(List<CallCustomerTimePO> callCustomerTimePOS);

}
