package com.zjft.microservice.treasurybrain.linecenter.mapstruct;

import com.zjft.microservice.treasurybrain.linecenter.domain.NetPointMatrix;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/8
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NetPointMatrixConverter {

	NetPointMatrixConverter INSTANCE = Mappers.getMapper(NetPointMatrixConverter.class);

	NetpointMatrixDTO domain2dto(NetPointMatrix netPointMatrix);

	List<NetpointMatrixDTO> domain2dto(List<NetPointMatrix> doList);
}
