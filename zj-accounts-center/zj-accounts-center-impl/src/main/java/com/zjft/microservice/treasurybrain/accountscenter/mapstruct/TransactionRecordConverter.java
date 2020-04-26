package com.zjft.microservice.treasurybrain.accountscenter.mapstruct;

import com.zjft.microservice.treasurybrain.accountscenter.domain.BiztxlogDO;
import com.zjft.microservice.treasurybrain.accountscenter.dto.BiztxlogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/10/10
 */
@Mapper
public interface TransactionRecordConverter {

	TransactionRecordConverter INSTANCE = Mappers.getMapper(TransactionRecordConverter.class);

	@Mappings({

	})
	BiztxlogDO dto2domain(BiztxlogDTO dto);

	BiztxlogDTO domain2dto(BiztxlogDO domain);

	List<BiztxlogDO> dto2domain(List<BiztxlogDTO> listDTO);

	List<BiztxlogDTO> domain2dto(List<BiztxlogDO> listPO);
}
