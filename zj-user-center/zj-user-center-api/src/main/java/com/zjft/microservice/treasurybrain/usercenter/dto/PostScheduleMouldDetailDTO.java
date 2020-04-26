package com.zjft.microservice.treasurybrain.usercenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/18
 */
@Data
public class PostScheduleMouldDetailDTO {

	@ApiModelProperty(value = "班次编号")
	private String classesNo;

	@ApiModelProperty(value = "班次名称")
	private String classesName;

	@ApiModelProperty(value = "时间轴分布")
	private Integer countNo;

	@ApiModelProperty(value = "人员信息")
	private List<PostScheduleMouldOpDTO> opList;
}
