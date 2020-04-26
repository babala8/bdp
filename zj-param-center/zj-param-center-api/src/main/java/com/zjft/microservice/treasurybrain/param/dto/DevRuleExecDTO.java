package com.zjft.microservice.treasurybrain.param.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Data;

@Data
public class DevRuleExecDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String devNo;
	private String startDate;
	private String endDate;
	private String addnotesRuleId;
	private Integer status;

}
