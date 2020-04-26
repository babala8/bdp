package com.zjft.microservice.treasurybrain.usercenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/21
 */
@Data
public class PostSchedulePlanDTO {

	@ApiModelProperty(value = "时间轴",example = "1")
	private String planDate;

	@ApiModelProperty(value = "班次")
	private String classesNo;

	@ApiModelProperty(value = "班次名称")
	private String classesName;

	private List<PostScheduleMouldOpDTO> opList;


}
