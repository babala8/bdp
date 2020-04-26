package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.domain.OrgbusinessTimeDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.OrgBusinessTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.po.OrgBusinessTimePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 崔耀中
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrgBusinessTimeConverter {
	OrgBusinessTimeConverter INSTANCE = Mappers.getMapper(OrgBusinessTimeConverter.class);


	@Mappings({
	})
	OrgBusinessTimePO dto2domain(OrgBusinessTimeDTO dto);

	List<OrgBusinessTimeDTO> domain2dto(List<OrgbusinessTimeDO> list);
}
