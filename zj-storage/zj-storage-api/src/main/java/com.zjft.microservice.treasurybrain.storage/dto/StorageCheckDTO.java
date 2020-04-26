package com.zjft.microservice.treasurybrain.storage.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 常 健
 * @since 2019/10/11
 */
@Data
public class StorageCheckDTO {

	private String no;

	private BigDecimal storageCheckMoney;

	private BigDecimal databaseRecordMoney;

	private String time;

	private Integer flag;

	private String clrCenterNo;

	private String centerName;

}
