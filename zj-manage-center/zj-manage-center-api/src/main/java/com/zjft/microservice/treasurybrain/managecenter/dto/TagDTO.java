package com.zjft.microservice.treasurybrain.managecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/6/11
 */
@Data
@ApiModel(value = "标签信息", description = "标签信息")
public class TagDTO extends DTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "标签编号", example = "B1001")
	private String tagTid;

	@ApiModelProperty(value = "EPC区信息", example = "B1001")
	private String epcInfo;

	@ApiModelProperty(value = "EPC存储容量（BIT）", example = "128")
	private Integer epcMemorySize;

	@ApiModelProperty(value = "标签类型 -- 1：钞箱标签 2：钞箱袋标签 3：设备标签", example = "1")
	private Integer tagType;

	@ApiModelProperty(value = "状态 -- 0：未启用 1：启用 2：停用", example = "0")
	private Integer status;

	@ApiModelProperty(value = "用户区存储容量（BIT）", example = "128")
	private Integer userdataMemorySize;

	@ApiModelProperty(value = "备注", example = "备注信息")
	private String note;

	@ApiModelProperty(value = "清机中心编号", example = "028001")
	private String clrCenterNo;

	@ApiModelProperty(value = "清机中心名称", example = "工商银行成都分行金库")
	private String centerName;

}
