package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.usercenter.domain.OutsourcingLineMapDO;
import com.zjft.microservice.treasurybrain.usercenter.dto.OutSourcingLineMapDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.OutSourcingLineMapInnerDTO;
import com.zjft.microservice.treasurybrain.usercenter.po.OutsourcingLineMapPO;
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

	List<OutSourcingLineMapInnerDTO> po2dto(List<OutsourcingLineMapPO> outSourcingLineMapPO);

}
