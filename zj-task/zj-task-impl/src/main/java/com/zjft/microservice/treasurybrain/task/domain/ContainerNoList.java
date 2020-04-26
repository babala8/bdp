package com.zjft.microservice.treasurybrain.task.domain;

import lombok.Data;

import java.util.List;


@Data
public class ContainerNoList {
	private String taskNo;
	private String containerNo;
	private String entityType;
	private String direction;
	private Integer leafFlag;
	private String status;
	private Integer containerType;
	private String conAmount;
	private String opNo;
	private String opTime;
	private String upperNo;
	private String customerNo;
	private String note;
	private Integer depositType;
	private List<CurrencyTypeList> currencyTypeList;
}
