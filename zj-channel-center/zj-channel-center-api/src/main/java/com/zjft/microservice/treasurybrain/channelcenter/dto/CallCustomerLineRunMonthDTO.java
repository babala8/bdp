package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 14:45
 */
@Data
@ApiModel(value = "上门收款线路安排月信息",description = "上门收款线路安排月信息")
public class CallCustomerLineRunMonthDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "线路编号",value = "线路编号")
	private String lineNo;

	@ApiModelProperty(name = "线路名称",value = "线路名称")
	private String lineName;

	@ApiModelProperty(name = "年月",value = "年月")
	private String theYearMonth;

	@ApiModelProperty(name = "用户数",value = "用户数")
	private Integer callCustomerNum;

}
