package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.clearcenter.dto.TaskCheckDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskCheckInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskCheckDTOConverter {
	TaskCheckDTOConverter INSTANCE = Mappers.getMapper(TaskCheckDTOConverter.class);

	@Mappings({
	})
	TaskCheckInfoDTO domain2dto(TaskCheckDTO taskCheckPO);

	List<TaskCheckInfoDTO> domain2dto(List<TaskCheckDTO> taskCheckDTOList);
}
