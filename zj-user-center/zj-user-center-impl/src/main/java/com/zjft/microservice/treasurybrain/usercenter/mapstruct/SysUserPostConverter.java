package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysUserPostDO;
import com.zjft.microservice.treasurybrain.common.dto.SysUserPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/12
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserPostConverter {

	SysUserPostConverter INSTANCE = Mappers.getMapper(SysUserPostConverter.class);

	@Mappings({})
	SysUserPostDO dto2do(SysUserPostDTO sysUserPostDTO);

	SysUserPostDTO do2dto(SysUserPostDO sysUserPostDO);

	List<SysUserPostDO> dto2do(List<SysUserPostDTO> sysUserPostDTOList);

	List<SysUserPostDTO> do2dto(List<SysUserPostDO> sysUserPostDOList);
}
