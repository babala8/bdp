package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.OutsourcingLineMapDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.OutSourcingLineMapDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/25 14:43
 */
@Mapper
public interface OutsourcingLineMapConverter {


	OutsourcingLineMapConverter INSTANCE = Mappers.getMapper(OutsourcingLineMapConverter.class);


	@Mappings({

	})
	OutSourcingLineMapDTO do2Dto(OutsourcingLineMapDO outsourcingLineMapDO);
	List<OutSourcingLineMapDTO> do2Dto(List<OutsourcingLineMapDO> outsourcingLineMapDOS);

	OutsourcingLineMapDO dto2do(OutSourcingLineMapDTO outSourcingLineMapDTO);
	List<OutsourcingLineMapDO> dto2do(List<OutSourcingLineMapDTO> outSourcingLineMapDTOS);

}
