package com.zjft.microservice.treasurybrain.business.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴朋
 * @since 2019/9/21
 */
@Data
@ApiModel(value = "上门预约信息",description = "上门预约信息")
public class VisitOrderDTO extends DTO {
	@ApiModelProperty(value = "上门客户号")
	private String customerNumber;

	@ApiModelProperty(value = "预约日期")
	private String orderDate;

	@ApiModelProperty(value = "预约时间段 ：1-上午 2-下午")
	private Integer orderTimePeriod;

	@ApiModelProperty(value = "预约时间 ：8-18点的时间范围，粒度为小时")
	private String orderTime;

	@ApiModelProperty(value = "备注：描述押运的物品类型及数量")
	private String note;

	@ApiModelProperty(value = "备注：上门客户简称")
	private String customerShortName;

	@ApiModelProperty(value = "状态 ：0-待审核 1-金库人员已审核 2-审核不通过")
	private Integer status;

	@ApiModelProperty(value = "旧预约日期",notes = "修改预约时启用，无其他含义")
	private String oldOrderDate;

}
