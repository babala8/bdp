package com.zjft.microservice.treasurybrain.tauro.mapstruct;

import com.zjft.microservice.treasurybrain.tauro.dto.TaskPerRecorderDTO;
import com.zjft.microservice.treasurybrain.tauro.po.TauroTaskPerRecorderPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskPerRecorderConverter {

	TaskPerRecorderConverter INSTANCE = Mappers.getMapper(TaskPerRecorderConverter.class);

	@Mappings({})
	List<TaskPerRecorderDTO> po2dto(List<TauroTaskPerRecorderPO> poList);
}
