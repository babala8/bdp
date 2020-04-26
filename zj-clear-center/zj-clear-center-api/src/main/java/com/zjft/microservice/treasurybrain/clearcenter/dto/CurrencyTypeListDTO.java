package com.zjft.microservice.treasurybrain.clearcenter.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CurrencyTypeListDTO {
	private String id;
	private BigDecimal amount;
	private Integer currencyType;
	private String  currencyCode;
	private Integer denomination;
}
