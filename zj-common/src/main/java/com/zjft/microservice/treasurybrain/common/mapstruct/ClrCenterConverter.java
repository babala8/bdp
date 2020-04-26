package com.zjft.microservice.treasurybrain.common.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import com.zjft.microservice.treasurybrain.common.domain.SysUserDO;
import com.zjft.microservice.treasurybrain.common.dto.ClrCenterDTO;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClrCenterConverter {

	ClrCenterConverter INSTANCE = Mappers.getMapper(ClrCenterConverter.class);


	@Mappings({
	})
	ClrCenterDTO domain2dto(ClrCenterTable domain);

	ClrCenterTable dto2do(ClrCenterDTO clrCenterDTO);

	List<ClrCenterDTO> domain2dto(List<ClrCenterTable> clrCenterTableList);

	List<UserDTO> domain2dto1(List<SysUserDO> sysUserDoList);

	default SysOrgDTO domain2dto(SysOrg domain) {
		return SysOrgConverter.INSTANCE.domain2dto(domain);
	}

}

