package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/19
 */
@Data
@ApiModel(value = "排班信息", description = "排班信息")
public class PostScheduleDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "计划编号")
	private String planNo;

	@ApiModelProperty(value = "机构编号",example = "1001")
	private String orgNo;

	@ApiModelProperty(value = "机构名称",example = "总行")
	private String orgName;

	@ApiModelProperty(value = "岗位编号",example = "000001")
	private String postNo;

	@ApiModelProperty(value = "岗位名称",example = "清分岗")
	private String postName;

	@ApiModelProperty(value = "月份",example = "2019-01")
	private String scheduleMonth;

	@ApiModelProperty(value = "模板编号",example = "0000000001")
	private String mouldNo;

	@ApiModelProperty(value = "模板名称",example = "XX模板")
	private String mouldName;

	@ApiModelProperty(value = "创建时间",example = "2019-01-01")
	private String createTime;

	@ApiModelProperty(value = "排班计划人员信息")
	private List<PostSchedulePlanDTO> detailList;
}
