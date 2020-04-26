package com.zjft.microservice.treasurybrain.tauro.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/8/9
 */
@Data
@ApiModel(value = "任务操作记录", description = "任务操作记录")
public class TaskPerRecorderDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "记录编号")
	private String id;

	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "货物编号")
	private String containerNo;

	@ApiModelProperty(value = "操作时间")
	private String performTime;

	@ApiModelProperty(value = "操作类型 1-配钞 2-备钞入库 3-出库交接 4-虚拟签收 5-尾箱回收 6-入库交接 7-尾箱清点 8-日结单")
	private Integer performType;

	@ApiModelProperty(value = "操作人员1")
	private String opNo1;

	@ApiModelProperty(value = "操作人员2")
	private String opNo2;

	@ApiModelProperty(value = "货物类型")
	private Integer containerType;
}
