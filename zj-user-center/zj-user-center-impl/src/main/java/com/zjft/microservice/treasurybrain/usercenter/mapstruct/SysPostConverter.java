package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.usercenter.domain.PostUserInfoDO;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysPostDetailDO;
import com.zjft.microservice.treasurybrain.usercenter.dto.PostUserInfoDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPostDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysPostDetailDTO;
import com.zjft.microservice.treasurybrain.usercenter.po.SysPostPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liuyuan
 * @author 常健
 * @since 2019/9/21 11:17
 */
@Mapper
public interface SysPostConverter {

	SysPostConverter INSTANCE = Mappers.getMapper(SysPostConverter.class);

	@Mappings({

	})
	List<SysPostDTO> po2dto(List<SysPostPO> sysPostPOList);
	SysPostPO dto2po(SysPostDTO sysPostDTO);

	List<SysPostDetailDTO> domain2dto(List<SysPostDetailDO> domain);

	List<PostUserInfoDTO> doList2dtoList(List<PostUserInfoDO> doList);
}
