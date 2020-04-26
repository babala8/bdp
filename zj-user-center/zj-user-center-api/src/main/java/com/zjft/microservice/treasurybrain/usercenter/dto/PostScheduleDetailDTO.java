package com.zjft.microservice.treasurybrain.usercenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/11/20
 */
@Data
@ApiModel(value = "排班详情信息（暂未用）", description = "排班详情信息（暂未用）")
public class PostScheduleDetailDTO extends PostScheduleDTO{

	@ApiModelProperty(value = "机构名称",example = "总行")
	private String orgName;

	@ApiModelProperty(value = "岗位名称",example = "清分岗")
	private String postName;

	@ApiModelProperty(value = "人员姓名",example = "张三")
	private String opName;
}
