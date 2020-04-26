package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/5 15:00
 */

@Data
@ApiModel(description = "导出加钞任务单接口",value = "导出加钞任务单接口")
public class ExportTaskExcelDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "导出任务编号列表",value = "导出任务编号列表")
	private List<String> taskNos;

	@ApiModelProperty(value = "导出文件类型",name = "导出文件类型")
	private String fileType;

//	@ApiModelProperty(value = "任务单类型",name ="任务单类型",notes = "1-加钞任务单")
//	private Integer taskType;

}
