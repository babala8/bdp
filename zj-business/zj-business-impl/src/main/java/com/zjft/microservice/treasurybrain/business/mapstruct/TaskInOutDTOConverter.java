package com.zjft.microservice.treasurybrain.business.mapstruct;

import com.zjft.microservice.treasurybrain.business.domain.TaskInOut;
import com.zjft.microservice.treasurybrain.business.dto.TaskInOutDTO;
import com.zjft.microservice.treasurybrain.business.po.TransferTaskInOutPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskInOutDTOConverter {
	TaskInOutDTOConverter INSTANCE = Mappers.getMapper(TaskInOutDTOConverter.class);
	@Mappings({
	})
	TaskInOutDTO domain2dto(TaskInOut taskInOut);

	TaskInOutDTO domain2dto(TransferTaskInOutPo taskInOutPo);

	List<TaskInOutDTO> domain2dto(List<TaskInOut> taskInOutList);

	List<TaskInOutDTO> domain2dto2(List<TransferTaskInOutPo> transferTaskInOutPoList);
}
