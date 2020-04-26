package com.zjft.microservice.treasurybrain.storage.domain;

import com.zjft.microservice.treasurybrain.storage.po.StorageTransferEntityPO;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/8/30 10:40
 */
@Data
public class StorageSortedTransferDetailDO extends StorageTransferEntityPO {

	private String containerName;
}
