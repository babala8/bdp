package com.zjft.microservice.treasurybrain.timejob.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TimeJobTaskEntityDetailPO {
	private String id;

	private String taskNo;

	private String key;

	private String value;

	private String name;
}
