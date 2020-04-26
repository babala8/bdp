package com.zjft.microservice.treasurybrain.linecenter.mapstruct;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineScheduleDO;
import com.zjft.microservice.treasurybrain.linecenter.domain.LineWorkDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
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
	LineWorkTableDTO do2dto(LineWorkDO callCustomerLineRunDO);
	List<LineWorkTableDTO> do2dto(List<LineWorkDO> callCustomerLineRunDOS);

	@Mappings({
	})
	LineScheduleDTO do2dto2(LineScheduleDO callCustomerLineRunDO);
	List<LineScheduleDTO> do2dto2(List<LineScheduleDO> callCustomerLineRunDOS);
}
