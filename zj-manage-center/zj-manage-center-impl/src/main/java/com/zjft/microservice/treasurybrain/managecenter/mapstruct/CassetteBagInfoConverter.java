package com.zjft.microservice.treasurybrain.managecenter.mapstruct;

import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteBagInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.po.CassetteBagInfoPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/6/13 10:05
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CassetteBagInfoConverter {

	CassetteBagInfoConverter INSTANCE = Mappers.getMapper(CassetteBagInfoConverter.class);

	@Mappings({})
	CassetteBagInfoPO domain2dto(CassetteBagInfoDTO cassetteBagInfoDTO);

	CassetteBagInfoDTO dto2domain(CassetteBagInfoPO cassetteBagInfoPO);

	List<CassetteBagInfoPO> domain2dto(List<CassetteBagInfoDTO> cassetteBagInfoDTOS);

	List<CassetteBagInfoDTO> dto2domain(List<CassetteBagInfoPO> cassetteBagInfoPOS);

}
