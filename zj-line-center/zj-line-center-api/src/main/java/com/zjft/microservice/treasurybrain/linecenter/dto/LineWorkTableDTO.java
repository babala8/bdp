package com.zjft.microservice.treasurybrain.linecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "线路运行图信息", description = "线路运行图信息")
public class LineWorkTableDTO extends DTO {
	@ApiModelProperty(value = "清机中心编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "线路运行编号")
	private String lineWorkId;

	@ApiModelProperty(value = "线路编号")
	private String lineNo;

	@ApiModelProperty(value = "线路名称")
	private String lineName;

	@ApiModelProperty(value = "年月[yyyy-mm]")
	private String theYearMonth;

	@ApiModelProperty(value = "日")
	private String theDay;

	@ApiModelProperty(value = "运行日期[yyyy-mm-dd]")
	private String runDate;

	@ApiModelProperty(value = "任务次数", example = "0")
	private Integer taskCount;

	@ApiModelProperty(value = "设备数量", example = "0")
	private Integer customerCount;

	@ApiModelProperty(value = "上午开始时间[hh:mm:ss]")
	private String startTimeAm;

	@ApiModelProperty(value = "下午结束时间[hh:mm:ss]")
	private String endTimeAm;

	@ApiModelProperty(value = "下午开始时间[hh:mm:ss]")
	private String startTimePm;

	@ApiModelProperty(value = "下午结束时间[hh:mm:ss]")
	private String endTimePm;

	@ApiModelProperty(value = "上午返回地点")
	private String returnUnitAm;

	@ApiModelProperty(value = "下午返回地址")
	private String returnUnitPm;

	@ApiModelProperty(value = "设备列表")
	private List<LineScheduleDTO> detailList;

}
