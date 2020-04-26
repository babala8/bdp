package com.zjft.microservice.treasurybrain.common.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/6 16:33
 */
@Data
@ApiModel(value = "任务流转请求转发",description = "任务流转请求转发")
public class TaskTransferDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "任务单类型")
	private Integer taskType;

	@ApiModelProperty(value = "任务单下一状态")
	private Integer status;

	@ApiModelProperty(value = "操作")
	private String operateType;

	@ApiModelProperty(name = "容器编号列表",value = "容器编号列表")
	private List<String> entityNos;

	@ApiModelProperty(value = "操作人员1")
	private String opNo1;

	@ApiModelProperty(value = "出库人员2")
	private String opNo2;

	@ApiModelProperty(value = "笼车编号")
	private String shelfNo;

	@ApiModelProperty(value = "线路编号")
	private String lineNo;

	@ApiModelProperty(name = "设备编号",value = "设备编号")
	private String customerNo;

	public TaskTransferDTO(){super();}

	public TaskTransferDTO(RetCodeEnum retCodeEnum){super(retCodeEnum);}

	public TaskTransferDTO(String retCode,String retMsg){super(retCode,retMsg);}
}
