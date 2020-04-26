package com.zjft.microservice.treasurybrain.task.mapstruct;

import com.zjft.microservice.treasurybrain.task.dto.ContainerEntityDTO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityDetailPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskEntityDetailTableConverter {
	TaskEntityDetailTableConverter INSTANCE = Mappers.getMapper(TaskEntityDetailTableConverter.class);

	@Mappings({
	})
	ContainerEntityDTO domain2dto(TaskEntityDetailPO taskEntityDetailPO);

	List<ContainerEntityDTO> domain2dto(List<TaskEntityDetailPO> taskEntityDetailPOList);
}
