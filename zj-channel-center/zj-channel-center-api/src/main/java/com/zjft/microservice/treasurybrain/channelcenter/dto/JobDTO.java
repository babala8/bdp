package com.zjft.microservice.treasurybrain.channelcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/21 09:42
 */
@Data
@ApiModel(description = "岗位信息",value = "岗位信息")
public class JobDTO {
	@ApiModelProperty(value = "岗位编号",name = "岗位编号",dataType = "int",example = "1")
	private Integer groupType;

	@ApiModelProperty(value = "岗位名称",name = "岗位名称",dataType = "String",example = "计划岗")
	private String jobName;

	@ApiModelProperty(value = "1-金库 2-网点",name = "岗位类型",dataType = "int",example = "1")
	private Integer jobType;

	@ApiModelProperty(value = "岗位数量",name = "岗位数量",dataType = "int",example = "100")
	private Integer jobNumber;

	@ApiModelProperty(value = "备注",name = "备注",dataType = "int")
	private String note;
}
