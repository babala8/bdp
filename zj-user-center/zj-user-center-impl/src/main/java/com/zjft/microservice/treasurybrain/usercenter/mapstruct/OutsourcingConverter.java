package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.usercenter.dto.OutsourcingDTO;
import com.zjft.microservice.treasurybrain.usercenter.po.OutsourcingPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 葛瑞莲
 * @since 2019/9/21
 */
@Mapper
public interface OutsourcingConverter {
	OutsourcingConverter INSTANCE = Mappers.getMapper(OutsourcingConverter.class);

	@Mappings({
	})
	List<OutsourcingDTO> po2dto(List<OutsourcingPO> po);

	OutsourcingPO dto2po(OutsourcingDTO dto);
}
