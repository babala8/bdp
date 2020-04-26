package com.zjft.microservice.treasurybrain.storage.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.storage.dto.*;

import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/8/14
 */
public interface GoodManageService {

	/**
	 * 分页查询库存信息
	 *
	 * @param paramMap 金库编号 任务单编号 笼车编号
	 * @return 仓储库存信息
	 */
	PageDTO<StorageEntityDTO> qryStorageEntityByPage(Map<String, Object> paramMap);

	/**
	 * 查询库存信息详情
	 *
	 * @param taskNo 任务单编号
	 * @return 仓储库存信息详情
	 */
	StorageEntityDetailDTO qryStorageEntityDetail(String taskNo);

	/**
	 * 库房物品信息分页查询
	 *
	 * @param paramMap
	 * @return
	 */
	PageDTO<StorageSortedEntityDTO> qryStorageInnerEntityByPage(Map<String, Object> paramMap) throws Exception;

	/**
	 * 获取笼车对应的任务单信息和钞袋列表
	 *
	 * @param shelfNo 笼车编号
	 * @return StorageEntityTransferDTO
	 */
	StorageEntityTransferDTO qryStorageEntityByShelfNo(String shelfNo);

	/**
	 * 根据线路编号查询笼车明细（出库）
	 */
	Map<String,Object> qryShelfDetailByLineNo(Map<String, Object> paramMap);

	PageDTO<StorageCheckDTO> qryCheckInfoByPage(Map<String, Object> paramMap);

	DTO check(CheckInventoryDTO checkInventoryDTO);

	/**
	 * 钞处领现确认出库
	 */
	DTO affirmOutNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO);
}
