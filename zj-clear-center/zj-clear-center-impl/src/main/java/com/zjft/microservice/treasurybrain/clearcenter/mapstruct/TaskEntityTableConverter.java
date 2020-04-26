package com.zjft.microservice.treasurybrain.clearcenter.mapstruct;

import com.zjft.microservice.treasurybrain.clearcenter.dto.ContainerEntityDTO;
import com.zjft.microservice.treasurybrain.clearcenter.po.TaskEntityDetailTablePO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 *
 * @author liuyuan
 * @since 2019/6/18 08:43
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskEntityTableConverter {

	TaskEntityTableConverter INSTANCE = Mappers.getMapper(TaskEntityTableConverter.class);

	List<TaskEntityDetailTablePO> dto2domain(List<ContainerEntityDTO> containerEntityDTOS);
}
