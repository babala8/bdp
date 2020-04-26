package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityTableDTO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskEntityConverter {

	TaskEntityConverter INSTANCE = Mappers.getMapper(TaskEntityConverter.class);

	@Mappings({
	})
	TaskEntityDTO domain2dto(TaskEntityPO taskEntityPO);

	List<TaskEntityDTO> domain2dto(List<TaskEntityPO> taskEntityPOList);
}
