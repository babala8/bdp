package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/11/19
 */
@Data
@ApiModel(value = "排班生成", description = "排班生成")
public class CreateScheduleDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "模板编号", example = "0000000001")
	private String mouldNo;

	@ApiModelProperty(value = "所属机构", example = "1001")
	private String orgNo;

	@ApiModelProperty(value = "岗位编号", example = "000001")
	private String postNo;

	@ApiModelProperty(value = "月份", example = "2019-01")
	private String scheduleMonth;

}
