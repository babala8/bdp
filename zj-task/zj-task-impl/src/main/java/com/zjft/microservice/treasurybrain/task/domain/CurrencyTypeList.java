package com.zjft.microservice.treasurybrain.task.domain;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CurrencyTypeList {
	private String id;
	private String customerNo;
	private BigDecimal amount;
	private Integer currencyType;
	private String  currencyCode;
	private Integer denomination;
}
