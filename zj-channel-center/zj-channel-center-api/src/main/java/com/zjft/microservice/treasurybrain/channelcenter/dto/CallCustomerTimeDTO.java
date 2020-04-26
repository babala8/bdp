package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CallCustomerTimeDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String customerNo;

	private int clrTimeInterval;

	private int clrDay;

	private String arrivalTime;

	private String callCustomerLine;

	private String callCustomerLineName;

	private String customerName;

	private String address;
}
