package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.task.domain.TaskInfo;
import com.zjft.microservice.treasurybrain.task.dto.LoadInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoadInfoConverter {
	LoadInfoConverter INSTANCE = Mappers.getMapper(LoadInfoConverter.class);

	@Mappings({
			@Mapping(source = "clrCenterName", target = "centerName"),
	})
	LoadInfoDTO domain2dto(TaskInfo taskInfo);

	List<LoadInfoDTO> domain2dto(List<TaskInfo> taskInfoList);
}
