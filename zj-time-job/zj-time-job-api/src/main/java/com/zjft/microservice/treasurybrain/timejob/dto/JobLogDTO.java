package com.zjft.microservice.treasurybrain.timejob.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 张思雨
 * @since 2019-08-05
 */
@Data
@ApiModel(value = "定时任务日志", description = "定时任务日志")
public class JobLogDTO extends DTO {

	@ApiModelProperty(value = "定时任务ID", example = "dbd65147da304be0b66ccd8dec299580")
	private String logicId;

	@ApiModelProperty(value = "定时任务名称", example = "ETLJob")
	private String jobName;

	@ApiModelProperty(value = "定时任务类型", example = "0")
	private Integer jobType;

	@ApiModelProperty(value = "定时任务结果", example = "0")
	private Integer jobResult;

	@ApiModelProperty(value = "定时任务创建者", example = "NULL")
	private String jobCreator;

	@ApiModelProperty(value = "定时任务开始时间", example = "2019-07-11 15:07:00")
	private String startTime;

	@ApiModelProperty(value = "定时任务结束时间", example = "2019-07-11 15:07:00")
	private String endTime;

	@ApiModelProperty(value = "定时任务结果描述", example = "执行成功")
	private String resultDesc;
}
