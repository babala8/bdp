package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerLineRunDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerLineRunDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/23 19:54
 */
@Mapper
public interface CallCustomerLineRunConverter {
	CallCustomerLineRunConverter INSTANCE = Mappers.getMapper(CallCustomerLineRunConverter.class);
	@Mappings({
	})
	CallCustomerLineRunDTO do2dto(CallCustomerLineRunDO callCustomerLineRunDO);
	List<CallCustomerLineRunDTO> do2dto(List<CallCustomerLineRunDO> callCustomerLineRunDOS);

}
