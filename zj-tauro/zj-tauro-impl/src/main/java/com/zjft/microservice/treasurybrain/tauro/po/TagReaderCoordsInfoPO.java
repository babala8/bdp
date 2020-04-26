package com.zjft.microservice.treasurybrain.tauro.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TagReaderCoordsInfoPO {

	private String tagReaderNo;

	private String rdDate;

	private String rdTime;

	private String userNo;

	private BigDecimal x;

	private BigDecimal y;

	private Integer coordsSrc;

	private Integer taskType;

	private String taskNo;

	private String devNo;

	private String transCode;
}
