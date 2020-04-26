package com.zjft.microservice.treasurybrain.channelcenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CountTaskInfoDTO {

	@ApiModelProperty(value = "任务单编号",example = "028001021911260001")
	private String taskNo;

	@ApiModelProperty(value = "设备编号",example = "10001")
	private String devNo;

	@ApiModelProperty(value = "清分批次",example = "1")
	private Short batch;

	@ApiModelProperty(value = "清分状态：1-等待执行，2-失败，3-完成，4-正在执行",example = "1")
	private Short countStatus;

	@ApiModelProperty(value = "清分开始时间")
	private String countStartDate;

	@ApiModelProperty(value = "清分结束时间")
	private String countEndDate;

	@ApiModelProperty(value = "批次数量")
	private double countAmount;

	//用于websocket推送，区分推送类型
	private String type;

	@ApiModelProperty(value = "任务单编号列表")
	private List<String> taskNoList;
}
