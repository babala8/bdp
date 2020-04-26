package com.zjft.microservice.treasurybrain.clearcenter.mapstruct;

import com.zjft.microservice.treasurybrain.clearcenter.dto.TaskCheckDTO;
import com.zjft.microservice.treasurybrain.clearcenter.po.TaskCheckPO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/2
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskCheckInnerConverter {

	TaskCheckInnerConverter INSTANCE = Mappers.getMapper(TaskCheckInnerConverter.class);

	List<TaskCheckDTO> po2dto(List<TaskCheckPO> poList);
}
