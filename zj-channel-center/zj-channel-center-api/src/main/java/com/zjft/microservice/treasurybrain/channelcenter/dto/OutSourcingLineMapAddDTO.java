package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/24 18:14
 */
@ApiModel(value = "覆盖生成押运人员排班记录",description = "覆盖生成押运人员排班记录")
@Data
public class OutSourcingLineMapAddDTO extends DTO {

	private static final long SerialVersionUID = 1L;

	@ApiModelProperty(name = "金库编号",value = "金库编号")
	private String clrCenterNo;

	@ApiModelProperty(name = "线路类型",value = "线路类型")
	private Integer lineType;

	@ApiModelProperty(name = "线路编号" ,value = "线路编号")
	private List<String> lineNos;

	@ApiModelProperty(name = "开始日期" ,value = "开始日期 yyyy-MM-dd")
	private String startDate;

	@ApiModelProperty(name = "结束日期" ,value = "结束日期 yyyy-MM-dd")
	private String endDate;

}
