package com.zjft.microservice.treasurybrain.timejob.mapstruct;

import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.timejob.po.TimeJobTaskEntityPO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/3
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TimeJobTaskEntityConverter {

	TimeJobTaskEntityConverter INSTANCE = Mappers.getMapper(TimeJobTaskEntityConverter.class);

	List<TimeJobTaskEntityPO> dto2po(List<TaskEntityDTO> dtoList);
}
