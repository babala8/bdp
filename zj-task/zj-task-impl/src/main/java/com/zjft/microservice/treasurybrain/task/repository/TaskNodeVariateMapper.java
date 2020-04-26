package com.zjft.microservice.treasurybrain.task.repository;

import com.zjft.microservice.treasurybrain.task.dto.TaskNodeVariateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 韩通
 * @since 2019/11/01
 */
@Mapper
public interface TaskNodeVariateMapper {

	int insertBatch(List<TaskNodeVariateDTO> list);

	int insert(TaskNodeVariateDTO taskNodeVariatePO);
}
