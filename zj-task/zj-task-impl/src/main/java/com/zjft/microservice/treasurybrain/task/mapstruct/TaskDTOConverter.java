package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.task.domain.TaskInfo;
import com.zjft.microservice.treasurybrain.task.domain.TaskTableForName;
import com.zjft.microservice.treasurybrain.task.dto.TaskDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskDTOConverter {
	TaskDTOConverter INSTANCE = Mappers.getMapper(TaskDTOConverter.class);
	@Mappings({
			@Mapping(source = "clrCenterName", target = "clrCenterName"),
			@Mapping(source = "opNo1", target = "opNo1"),
			@Mapping(source = "opNo2", target = "opNo2"),
//			@Mapping(source = "modeOp", target = "modeOp"),
//			@Mapping(source = "addnoteLine.lineName",target = "lineName")
	})
	TaskInfoDTO domain2dto(TaskTableForName taskTableForOp);

	TaskInfo domain2dto(TaskInfoDTO taskInfoDTO);

	TaskDTO domain2dto(TaskInfo taskInfo);

}
