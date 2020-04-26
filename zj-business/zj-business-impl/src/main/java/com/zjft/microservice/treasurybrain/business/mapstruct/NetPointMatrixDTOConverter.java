package com.zjft.microservice.treasurybrain.business.mapstruct;

import com.zjft.microservice.treasurybrain.business.domain.NetPointMatrix;
import com.zjft.microservice.treasurybrain.business.dto.NetPointMatrixDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NetPointMatrixDTOConverter {
	NetPointMatrixDTOConverter INSTANCE = Mappers.getMapper(NetPointMatrixDTOConverter.class);
	@Mappings({
	})
	NetPointMatrixDTO domain2dto (NetPointMatrix netPointMatrix);

	NetPointMatrix domain2dto2(NetpointMatrixDTO netpointMatrixDTO);

	List<NetPointMatrix> domain2dto2(List<NetpointMatrixDTO> netpointMatrixDTOList);
}
