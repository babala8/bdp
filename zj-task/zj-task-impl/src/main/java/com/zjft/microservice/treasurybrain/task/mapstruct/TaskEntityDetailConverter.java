package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityDetailPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskEntityDetailConverter {
	TaskEntityDetailConverter INSTANCE = Mappers.getMapper(TaskEntityDetailConverter.class);

	@Mappings({
	})
	TaskEntityDetailDTO domain2dto(TaskEntityDetailPO taskEntityDetailPO);

	List<TaskEntityDetailDTO> domain2dto(List<TaskEntityDetailPO> taskEntityDetailPOList);
}
