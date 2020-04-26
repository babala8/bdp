package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class CallCustomerTimeListDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String customerNo;

	private Integer callClrPeriod;

	private List<CallCustomerTimeDTO> timeList;

	public CallCustomerTimeListDTO(RetCodeEnum retCodeEnum){
		super(retCodeEnum);
	}

	public CallCustomerTimeListDTO(){
		super();
	}

}
