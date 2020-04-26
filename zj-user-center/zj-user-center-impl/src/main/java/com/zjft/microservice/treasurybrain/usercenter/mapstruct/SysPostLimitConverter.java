package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.usercenter.dto.SysPostLimitDTO;
import com.zjft.microservice.treasurybrain.usercenter.po.SysPostLimitPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/14
 */
@Mapper
public interface SysPostLimitConverter {

	SysPostLimitConverter INSTANCE = Mappers.getMapper(SysPostLimitConverter.class);

	@Mappings({

	})
	List<SysPostLimitPO> dto2domain(List<SysPostLimitDTO> sysPostLimitDTOList);
}
