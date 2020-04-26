package com.zjft.microservice.treasurybrain.common.dto;

import lombok.Data;

@Data
public class DispatchDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String dispatchNo;

	private String addnotesPlanNo;

	private String clrCenterName;
	
	private String addnotesGroupNo;

	private String addnotesOpNo1;

	private String addnotesOpNo2;

	private String addnotesOpName1;

	private String addnotesOpName2;

	private String addnotesDate;

	private String addDate;

	private int status;
	
	private String addMode;
	
	private String modOpNo;
	
	private String modDate;
	
	private String modTime;
	
	private String modOpName;

	private String lineName;

	private Integer clrTimeInterval;

}
