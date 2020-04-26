package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 13:54
 */
@Data
@ApiModel
public class CallCustomerLineRunDetailDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name="客户编号",value = "客户编号")
	private String customerNo;

	@ApiModelProperty(name="客户简称",value = "客户简称")
	private String customerShortName;

	@ApiModelProperty(name="线路安排记录编号",value = "线路安排记录编号")
	private String lineRunNo;

	@ApiModelProperty(name = "达到时间段 1-上午 2-下午",value = "达到时间段")
	private String clrTimeInterval;

	@ApiModelProperty(name="要求到达时间",value = "要求到达时间")
	private String arrivalTime;


}
