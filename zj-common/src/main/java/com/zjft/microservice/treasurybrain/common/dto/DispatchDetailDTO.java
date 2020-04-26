package com.zjft.microservice.treasurybrain.common.dto;

import lombok.Data;

@Data
public class DispatchDetailDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String devNo;

	private String devTypeName;

	private String devCatalogName;
	
	private String devAddress;

	private int addnotesAmt;

	private int status;

	private String devVendorName;

	private int addFlag;

}
