package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerInfo;
import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerTypeDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTypeDTO;
import com.zjft.microservice.treasurybrain.common.domain.CallCustomerTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zhangjs
 * @since 2019-09-21
 */
@Mapper
public interface CallCustomerInfoConverter {

	CallCustomerInfoConverter INSTANCE = Mappers.getMapper(CallCustomerInfoConverter.class);

	@Mappings({
	})
	CallCustomerDTO domain2dto(CallCustomerInfo domain);

	CallCustomerInfo dto2domain(CallCustomerDTO dto);

	List<CallCustomerDTO> domain2dto(List<CallCustomerInfo> callCustomerInfos);

	CallCustomerTypeDTO domain2dto2(CallCustomerTypeDO callCustomerTypeDO);

	List<CallCustomerTypeDTO> domain2dto2(List<CallCustomerTypeDO> callCustomerInfos);
}
