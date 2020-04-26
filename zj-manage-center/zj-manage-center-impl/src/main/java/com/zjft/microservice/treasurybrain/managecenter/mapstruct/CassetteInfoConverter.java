package com.zjft.microservice.treasurybrain.managecenter.mapstruct;

import com.zjft.microservice.treasurybrain.managecenter.domain.CassetteInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author:liuyuan
 * @since:2019/6/12 09:13
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CassetteInfoConverter {

	CassetteInfoConverter INSTANCE = Mappers.getMapper(CassetteInfoConverter.class);

	@Mappings({
	})
	CassetteInfoDO domain2dto(CassetteInfoDTO cassetteInfoDTO);
	CassetteInfoDTO dto2domain(CassetteInfoDO cassetteInfoDo);
	List<CassetteInfoDO> domain2dto(List<CassetteInfoDTO> cassetteInfoDTOS);
	List<CassetteInfoDTO> dto2domain(List<CassetteInfoDO> cassetteInfoDOS);
}
