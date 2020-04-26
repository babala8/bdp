package com.zjft.microservice.treasurybrain.storage.repository;


import com.zjft.microservice.treasurybrain.storage.domain.StorageEntityTableDO;
import com.zjft.microservice.treasurybrain.storage.po.StorageEntityTablePO;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/8/29 10:01
 */

public interface StorageEntityTableMapper {

	/**
	 * 新增物品
	 *
	 * @param storageEntityTablePO
	 * @return
	 */
	int insert(StorageEntityTablePO storageEntityTablePO);

	/**
	 * 删除物品
	 *
	 * @param storageEntityTablePO
	 * @return
	 */
	int delete(StorageEntityTablePO storageEntityTablePO);


	/**
	 *
	 * 分页查询总行数
	 *
	 * @param param
	 * @return
	 */
	int qryTotalRowForPage(Map<String,Object> param);

	/**
	 *
	 * 分页查询现金物品信息
	 *
	 * @param param
	 * @return
	 */
	List<StorageEntityTableDO> qryByPage(Map<String,Object> param);

	/**
	 *
	 * 更新或新增库房物品信息表
	 *
	 * @param storageEntityTablePO
	 * @return
	 */
	int insertOrUpdate(StorageEntityTablePO storageEntityTablePO);

	/**
	 *
	 * 物品入库前更新物品状态和清空金额信息
	 *
	 * @param storageEntityTablePO
	 * @return
	 */
	int inClear(StorageEntityTablePO storageEntityTablePO);

	/**
	 *
	 *
	 *
	 * @param storageEntityTablePO
	 * @return
	 */
	int outClear(StorageEntityTablePO storageEntityTablePO);


	/**
	 *
	 * 根据entityNo查询
	 *
	 * @param entityNo
	 * @return
	 */
	List<StorageEntityTableDO> qryByEntityNo(String entityNo);

	int deleteById(String id);
}
