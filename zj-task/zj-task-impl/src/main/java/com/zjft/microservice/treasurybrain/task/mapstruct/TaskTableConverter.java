package com.zjft.microservice.treasurybrain.task.mapstruct;


import com.zjft.microservice.treasurybrain.task.domain.TaskInfo;
import com.zjft.microservice.treasurybrain.task.dto.TaskTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskTableConverter {
	TaskTableConverter INSTANCE = Mappers.getMapper(TaskTableConverter.class);

	@Mappings({
	})
	TaskTableDTO do2dto(TaskInfo taskNodeDO);

	List<TaskTableDTO> do2dto(List<TaskInfo> taskNodeDOList);
}
