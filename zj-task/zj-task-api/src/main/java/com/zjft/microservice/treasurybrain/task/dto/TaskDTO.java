package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WUPENG
 * @since 2019/11/28
 */

@Data
@ApiModel(value = "清点详情",description = "清点详情")
public class TaskDTO extends DTO {
	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "加钞线路编号")
	private String lineNo;

	@ApiModelProperty(value = "加钞线路名称")
	private String lineName;

	@ApiModelProperty(value = "状态")
	private String status;

	List<TaskCheckInfoDTO> taskCheckList;

	public TaskDTO() {
	}

	public TaskDTO(RetCodeEnum re) {
		super(re);
	}

}
