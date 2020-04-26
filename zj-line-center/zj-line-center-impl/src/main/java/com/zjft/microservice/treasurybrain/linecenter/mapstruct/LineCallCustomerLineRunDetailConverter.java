package com.zjft.microservice.treasurybrain.linecenter.mapstruct;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineScheduleDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.po.LineSchedulePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/24 00:53
 */
@Mapper
public interface LineCallCustomerLineRunDetailConverter {

	LineCallCustomerLineRunDetailConverter INSTANCE = Mappers.getMapper(LineCallCustomerLineRunDetailConverter.class);
	@Mappings({

	})
	List<LineScheduleDTO> do2dto(List<LineScheduleDO> callCustomerLineRunDetailDOS);
	List<LineScheduleDTO> dto2do(List<LineScheduleDO> callCustomerLineRunDetailDTOS);
	@Mappings({

	})
	List<LineScheduleDO> dto2po(List<LineScheduleDTO> lineScheduleDTOS);
	LineScheduleDTO po2dto(LineScheduleDO lineScheduleDO);
}
