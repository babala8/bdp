package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.business.dto.TaskInOutDTO;
import com.zjft.microservice.treasurybrain.task.domain.TaskInOut;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskInOutConverter {

	TaskInOutConverter INSTANCE = Mappers.getMapper(TaskInOutConverter.class);

	@Mappings({
	})
	TaskInOut dto2domain(TaskInOutDTO taskInOutDTO);

	List<TaskInOut> dto2domain(List<TaskInOutDTO> taskInOutDTOList);
}
