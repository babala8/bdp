package com.zjft.microservice.treasurybrain.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author geruilian
 * @since 2019/9/25
 */
@Data
@ApiModel(value = "子任务箱子列表",description = "子任务箱子列表")
public class TaskSplitContainerListDTO {

	private List<TaskSplitContainerDTO> taskSplitContainers;

}
