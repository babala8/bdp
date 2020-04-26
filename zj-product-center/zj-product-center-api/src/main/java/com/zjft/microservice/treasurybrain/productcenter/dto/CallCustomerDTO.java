package com.zjft.microservice.treasurybrain.productcenter.dto;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CallCustomerDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String customerNo;

	private String customerShortName;

	private String address;

	private String location;

	private String x;

	private String y;

	private String isOneself;

	private String customerNumber;

	private String cnCustomerLongName;

	private String customerAuthPhone;

	private String customerManage;

	private String touchPhoneOne;

	private String touchPhoneTwo;

	private String callCustomerLine;

	private Integer callClrPeriod;

	private String callCustomerLineName;

	private String callCustomerType;

	private String callCustomerTypeName;
}
