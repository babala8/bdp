package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class VisitOrderDO {

	private String customerNumber;

	private String orderDate;

	private BigDecimal orderTimePeriod;

	private String orderTime;

	private String note;

	private String customerShortName;

	private Integer status;

	private String oldOrderDate;
}
