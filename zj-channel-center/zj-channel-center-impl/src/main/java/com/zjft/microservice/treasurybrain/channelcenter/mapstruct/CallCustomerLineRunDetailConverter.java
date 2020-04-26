package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerLineRunDetailDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerLineRunDetailDTO;
import com.zjft.microservice.treasurybrain.channelcenter.po.CallCustomerLineRunDetailPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/24 00:53
 */
@Mapper
public interface CallCustomerLineRunDetailConverter {

	CallCustomerLineRunDetailConverter INSTANCE = Mappers.getMapper(CallCustomerLineRunDetailConverter.class);
	@Mappings({

	})
	List<CallCustomerLineRunDetailDTO> do2dto(List<CallCustomerLineRunDetailDO> callCustomerLineRunDetailDOS);
	List<CallCustomerLineRunDetailDO> dto2do(List<CallCustomerLineRunDetailDTO> callCustomerLineRunDetailDTOS);
	@Mappings({

	})
	List<CallCustomerLineRunDetailPO> dto2po(List<CallCustomerLineRunDetailDTO> callCustomerLineRunDetailDTOS);
	List<CallCustomerLineRunDetailDTO> po2dto(List<CallCustomerLineRunDetailPO> callCustomerLineRunDetailPOS);
}
