package com.zjft.microservice.treasurybrain.channelcenter.mapstruct;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevBaseInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevTypeDTO;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.domain.DevCatalogTable;
import com.zjft.microservice.treasurybrain.common.domain.DevTypeTable;
import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import com.zjft.microservice.treasurybrain.common.dto.DevCatalogDTO;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import com.zjft.microservice.treasurybrain.common.mapstruct.SysOrgConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DevBaseInfoConverter {
	DevBaseInfoConverter INSTANCE = Mappers.getMapper(DevBaseInfoConverter.class);

	@Mappings({

	})
	DevBaseInfoDTO domain2dto(DevBaseInfo devBaseInfo);

	DevBaseInfo dto2do(DevBaseInfoDTO dto);

	List<DevBaseInfoDTO> domain2dto(List<DevBaseInfo> devBaseInfoList);

	default DevTypeDTO domain2dto(DevTypeTable domain) {
		return DevTypeConverter.INSTANCE.domain2dto(domain);
	}

	default DevCatalogDTO domain2dto(DevCatalogTable domain) {
		return DevCatalogConverter.INSTANCE.domain2dto(domain);
	}

	default SysOrgDTO domain2dto(SysOrg domain) {
		return SysOrgConverter.INSTANCE.domain2dto(domain);
	}
}
