package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/24 17:16
 */
@Data
@ApiModel
public class OutSourcingLineMapDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "编号",name = "编号")
	private String lineRunNo;

	@ApiModelProperty(value = "押运日期",name = "押运日期")
	private String dutyDate;

	@ApiModelProperty(value = "押运线路编号",name = "押运线路编号")
	private String lineNo;

	@ApiModelProperty(value = "押运线路名称",name ="押运线路名称")
	private String lineName;

	@ApiModelProperty(value = "押运车辆编号",name = "押运车辆编号")
	private Integer carNo;

	@ApiModelProperty(value = "押运车辆车牌号",name = "押运车辆车牌号")
	private String carNum;

	@ApiModelProperty(value = "押运人员编号",name = "押运人员编号")
	private List<OutsourcingDTO> outsourcingList;
}
