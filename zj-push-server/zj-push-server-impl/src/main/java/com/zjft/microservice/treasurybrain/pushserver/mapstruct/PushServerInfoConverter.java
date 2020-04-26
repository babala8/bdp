package com.zjft.microservice.treasurybrain.pushserver.mapstruct;

import com.zjft.microservice.treasurybrain.pushserver.dto.PushSeverInfoDTO;
import com.zjft.microservice.treasurybrain.pushserver.po.PushServerInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/09/26
 */
@Mapper
public interface PushServerInfoConverter {

	PushServerInfoConverter INSTANCE = Mappers.getMapper(PushServerInfoConverter.class);

	@Mappings({

	})
	PushServerInfoPO dto2domain(PushSeverInfoDTO dto);

	PushSeverInfoDTO domain2dto(PushServerInfoPO domain);

	List<PushServerInfoPO> dto2domain(List<PushSeverInfoDTO> listDTO);

	List<PushSeverInfoDTO> domain2dto(List<PushServerInfoPO> listPO);
}
