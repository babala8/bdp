package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StorageCheckPO {

	private String no;

	private BigDecimal storageCheckMoney;

	private BigDecimal databaseRecordMoney;

	private String time;

	private Integer flag;

	private String clrCenterNo;


}
