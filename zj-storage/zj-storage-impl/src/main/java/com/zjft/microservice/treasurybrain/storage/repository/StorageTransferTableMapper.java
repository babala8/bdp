package com.zjft.microservice.treasurybrain.storage.repository;

import com.zjft.microservice.treasurybrain.storage.domain.StorageSortedTransferTableDO;
import com.zjft.microservice.treasurybrain.storage.po.StorageTransferTablePO;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/8/27 17:22
 */

public interface StorageTransferTableMapper {

	int insert(StorageTransferTablePO storageTransferTablePO);

	int qryTotalRowForPage(Map<String,Object> paramsMap);

    List<StorageSortedTransferTableDO> qryByPage(Map<String,Object> paramsMap);

}
