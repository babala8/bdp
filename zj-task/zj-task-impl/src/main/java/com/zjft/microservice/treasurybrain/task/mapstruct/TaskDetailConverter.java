package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.task.domain.TaskDetail;
import com.zjft.microservice.treasurybrain.task.domain.TaskDetailPropertyDO;
import com.zjft.microservice.treasurybrain.task.dto.PropertyDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskDetailTableDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskDetailConverter {
	TaskDetailConverter INSTANCE = Mappers.getMapper(TaskDetailConverter.class);

	@Mappings({
	})
	TaskDetailTableDTO domain2dto(TaskDetail taskDetail);

	List<TaskDetailTableDTO> domain2dto(List<TaskDetail> taskDetailList);

	@Mappings({
			@Mapping(source = "detailList", target = "detailList"),
	})
	TaskProductDTO domain2dto1(TaskDetail taskDetail);

	List<TaskProductDTO> domain2dto1(List<TaskDetail> taskDetailList);

	PropertyDTO domain2dto2(TaskDetailPropertyDO taskDetail);

	List<PropertyDTO> domain2dto2(List<TaskDetailPropertyDO> taskDetailList);


}
