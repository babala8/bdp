package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/8/28 09:02
 */

@Data
public class StorageTransferEntityPO {

	private String id;

	private String recordNo;

	private String containerNo;

	private Integer containerType;

	private Integer entityType;

	private Integer currencyType;

	private String currencyCode;

	private Integer denomination;

	private Double amount;

	private String upperNo;

	private String shelfNo;

	private Integer isLeaf;

}
