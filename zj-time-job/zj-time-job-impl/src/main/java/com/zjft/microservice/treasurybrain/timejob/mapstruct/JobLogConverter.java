package com.zjft.microservice.treasurybrain.timejob.mapstruct;

import com.zjft.microservice.treasurybrain.timejob.dto.JobLogDTO;
import com.zjft.microservice.treasurybrain.timejob.po.JobLogPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 张思雨
 * @since 2019-08-05
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobLogConverter {

	JobLogConverter INSTANCE = Mappers.getMapper(JobLogConverter.class);

	@Mappings({})
	List<JobLogDTO> domain2dto(List<JobLogPO> jobLogPO);

}
