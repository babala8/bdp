package com.zjft.microservice.treasurybrain.task.mapstruct;


import com.zjft.microservice.treasurybrain.task.domain.TaskCountInfo;
import com.zjft.microservice.treasurybrain.task.dto.TaskCountInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 *
 * @author zhangjs
 * @since 2019/10/10 16:36
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskCountInfoConverter {

	TaskCountInfoConverter INSTANCE = Mappers.getMapper(TaskCountInfoConverter.class);

	List<TaskCountInfoDTO> do2dto(List<TaskCountInfo> taskCountInfos);
}
