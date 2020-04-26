package com.zjft.microservice.treasurybrain.clearcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/7 14:54
 */

@Data
@ApiModel(value = "任务配钞信息",description = "任务配钞信息")
public class LoadNoteDTO extends DTO {

	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	@ApiModelProperty(value = "服务对象类型")
	private Integer customerType;

	/*@ApiModelProperty(value = "服务对象状态")
	private Integer status;

	@ApiModelProperty(value = "服务对象清点金额")
	private double customerAmount;*/

	@ApiModelProperty(value = "任务单下一个状态")
	private Integer nextStatus;

	@ApiModelProperty(value = "操作")
	private String operateType;

	@ApiModelProperty(value = "任务单类型")
	private int taskType;

	@ApiModelProperty(value = "长短款标志类型")
	private BigDecimal cashShortOver;

	@ApiModelProperty(value = "容器配钞信息")
	private List<ContainerEntityDTO> containerEntityDTOs;

	@ApiModelProperty(value = "推送消息类型")
	private String Type;

	public LoadNoteDTO() {
	}

	public LoadNoteDTO(RetCodeEnum re) {
		super(re);
	}

}
