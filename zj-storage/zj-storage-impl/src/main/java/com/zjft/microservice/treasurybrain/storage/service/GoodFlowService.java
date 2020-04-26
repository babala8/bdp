package com.zjft.microservice.treasurybrain.storage.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityCheckDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityTransferDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferEntityDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/8/14
 */
public interface GoodFlowService {


	/**
	 *
	 * @param taskNo 任务单编号
	 * @return 实物编号列表
	 */
	List<String> getGoodsByTaskNo(String taskNo);


	/**
	 * 现金库房调入
	 * 1.校验参数和笼车信息
	 * 2.插入库房调拨记录
	 * 3.删除库房物品信息
	 * @param storageSortedTransferDTO
	 * @return
	 */
	DTO StorageInnerIn(StorageSortedTransferDTO storageSortedTransferDTO) throws Exception;

	/**
	 * 现金库房调出到清分区或其他库房
	 *
	 * 1.校验参数和笼车信息
	 * 2.插入库房调拨记录
	 * 3.增加库房物品信息
	 * @param
	 * @return
	 */
	DTO StorageInnerOut( StorageSortedTransferDTO storageSortedTransferDTO) throws Exception;


	/**
	 *
	 * 分页查询调入调出记录
	 *
	 * @param params
	 * @return
	 */
	PageDTO<StorageSortedTransferDTO> qryInnerTransferByPage(Map<String, Object> params) ;

	/**
	 *
	 * 查询调入调出详情
	 *
	 * @param recordNo
	 * @return
	 */
	ListDTO<StorageSortedTransferEntityDTO> qryInnerTransferDetail(String recordNo);

	/**
	 *
	 * 手持机推送消息
	 *
	 * @param storageEntityCheckDTO
	 * @return
	 */
	DTO accessCheck(StorageEntityCheckDTO storageEntityCheckDTO);

	/**
	 * 获取笼车对应的任务单信息和钞袋列表
	 *
	 * @param shelfNo 笼车编号
	 * @return StorageEntityTransferDTO
	 */
	StorageEntityTransferDTO getStorageEntityByShelfNo(String shelfNo);
}
