package com.zjft.microservice.treasurybrain.linecenter.mapstruct;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineTableDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/24 20:09
 */
@Mapper
public interface LineConverter {

	LineConverter INSTANCE = Mappers.getMapper(LineConverter.class);
	@Mappings({

	})
	LineTableDTO do2dto(LineTableDO lineDO);
	List<LineTableDTO> do2dto(List<LineTableDO> lineDOS);
}
