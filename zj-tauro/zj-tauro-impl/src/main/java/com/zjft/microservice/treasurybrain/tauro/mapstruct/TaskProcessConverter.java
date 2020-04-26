package com.zjft.microservice.treasurybrain.tauro.mapstruct;

import com.zjft.microservice.treasurybrain.task.dto.CustomerInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskTauroDTO;
import com.zjft.microservice.treasurybrain.tauro.domain.TaskProcessDO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TauroCustomerInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskProcessConverter {

	TaskProcessConverter INSTANCE = Mappers.getMapper(TaskProcessConverter.class);

	@Mappings({
			@Mapping(target = "taskDate" ,source = "createTime"),
			@Mapping(target = "lineNo" ,source = "lineNo"),
			@Mapping(target = "lineName" ,source = "lineName")
	})
	TaskProcessDTO do2dto(TaskTauroDTO taskTauroDTO);

	List<TaskProcessDTO> do2dto(List<TaskTauroDTO> taskTauroDTOList);

	TauroCustomerInfoDTO do2dto2(CustomerInfoDTO customerInfoDTO);

	List<TauroCustomerInfoDTO> do2dto2(List<CustomerInfoDTO> customerInfoDTOList);
}
