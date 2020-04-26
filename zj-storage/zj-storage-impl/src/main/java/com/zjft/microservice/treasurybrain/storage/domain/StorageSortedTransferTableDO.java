package com.zjft.microservice.treasurybrain.storage.domain;

import com.zjft.microservice.treasurybrain.storage.po.StorageTransferTablePO;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/8/27 19:08
 */

@Data
public class StorageSortedTransferTableDO extends StorageTransferTablePO {

	private String clrCenterName;

	private String delivererName1;

	private String delivererName2;

	private String receiverName1;

	private String receiverName2;

}
