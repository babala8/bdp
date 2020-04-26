package com.zjft.microservice.treasurybrain.task.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CustomerNoList {
	private String taskNo;
	private String customerNo;
	private String customerName;
	private String customerType;
	private String customerTypeName;
	private String sort;
	private String status;
	private String note;
	private String address;
	private String orgNo;
	private String name;
//	private String amount;
	private String orgName;
	private String orgAddress;
	private BigDecimal planAddnotesATM;
	private List<ContainerNoList> containerNoList;
	private List<CurrencyTypeList> currencyTypeList;
}
