package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liuyuan
 * @since 2019/8/29 10:04
 */
@Data
public class StorageEntityTablePO {

	private String id;

	private String clrCenterNo;

	private String entityNo;

	private String productNo;

	private int entityType;

	private BigDecimal amount;

	private String parentEntity;

	private String shelfNo;

	private String time;

	private int isLeaf;

	private int inOutFlag;
}
