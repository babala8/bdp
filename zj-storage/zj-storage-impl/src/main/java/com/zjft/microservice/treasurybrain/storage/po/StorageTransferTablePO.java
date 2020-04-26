package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/8/27 16:28
 */
@Data
public class StorageTransferTablePO {

	private String recordNo;

	private String clrCenterNo;

	private Integer direction;

	private String transferDate;

	private String transferTime;

	private String delivererNo1;

	private String delivererNo2;

	private String receiverNo1;

	private String receiverNo2;
}
