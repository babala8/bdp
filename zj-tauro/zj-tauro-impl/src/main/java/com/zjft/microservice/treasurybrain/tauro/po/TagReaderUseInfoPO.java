package com.zjft.microservice.treasurybrain.tauro.po;

import lombok.Data;

@Data
public class TagReaderUseInfoPO {

	private String tagReaderUseNo;

	private String tagReaderNo;

	private String requestOpNo;

	private String requestDate;

	private String requestTime;

	private Integer reviewResult;

	private String rejectReason;

	private String grantOpNo;

	private String grantDate;

	private String grantTime;

	private String signOpNo;

	private String returnOpNo;

	private String returnDate;

	private String returnTime;

	private Integer tagReaderUseStatus;

	private String crashFlag;
}
