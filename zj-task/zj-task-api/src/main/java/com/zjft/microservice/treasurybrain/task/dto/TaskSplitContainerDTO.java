package com.zjft.microservice.treasurybrain.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author geruilian
 * @since 2019/9/25
 */
@Data
@ApiModel(value = "子任务箱子信息",description = "子任务箱子信息")
public class TaskSplitContainerDTO {

	@ApiModelProperty(value = "主键编号")
	private String Id;

	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "任务编号")
	private String entityNo;

	@ApiModelProperty(value = "容器类型")
	private String productNo;

	@ApiModelProperty(value = "金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "父容器")
	private String parentEntity;

	@ApiModelProperty(value = "调运方向")
	private Integer direction;

	@ApiModelProperty(value = "是否为最下级容器:0-不是 1-是")
	private Integer leafFlag;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "寄库类型")
	private String depositType;

	List<TaskEntityDetailDTO> taskEntityDetailDTOList;

}
