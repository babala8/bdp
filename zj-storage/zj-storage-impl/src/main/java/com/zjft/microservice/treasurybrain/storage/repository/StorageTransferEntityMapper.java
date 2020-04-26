package com.zjft.microservice.treasurybrain.storage.repository;

import com.zjft.microservice.treasurybrain.storage.domain.StorageSortedTransferDetailDO;
import com.zjft.microservice.treasurybrain.storage.po.StorageTransferEntityPO;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/28 16:32
 */

public interface StorageTransferEntityMapper {

	int insert(StorageTransferEntityPO storageTransferEntityPO);

	List<StorageSortedTransferDetailDO> qryDetail(String recordNo);

	List<StorageSortedTransferDetailDO> qryContainerNosInRecord(String recordNo);

	List<StorageSortedTransferDetailDO> qryByRecordNoAndContainerNo(StorageTransferEntityPO storageTransferEntityPO);


}
