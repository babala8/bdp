package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.task.dto.TaskCheckInfoDTO;
import com.zjft.microservice.treasurybrain.task.po.TaskCheckPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskCheckConverter {
	TaskCheckConverter INSTANCE = Mappers.getMapper(TaskCheckConverter.class);

	@Mappings({
	})
	TaskCheckInfoDTO domain2dto(TaskCheckPO taskCheckPO);

	List<TaskCheckInfoDTO> domain2dto(List<TaskCheckPO> taskCheckPOList);
}
