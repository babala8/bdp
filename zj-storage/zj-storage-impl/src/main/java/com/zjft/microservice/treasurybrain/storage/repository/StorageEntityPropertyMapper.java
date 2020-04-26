package com.zjft.microservice.treasurybrain.storage.repository;

import com.zjft.microservice.treasurybrain.storage.po.StorageEntityPropertyPO;

import java.util.List;

public interface StorageEntityPropertyMapper {

	/**
	 *
	 * 插入属性表
	 *
	 * @param storageEntityPropertyPO
	 * @return
	 */
	int insert(StorageEntityPropertyPO storageEntityPropertyPO);

	/**
	 *
	 * 查询库房中箱子的明细
	 *
	 * @param id
	 * @return
	 */
	List<StorageEntityPropertyPO> qryEntityDetailById(String id);

	int deleteById(String id);

}
