package com.zjft.microservice.treasurybrain.storage.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.exception.TradeException;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.storage.dto.*;
import com.zjft.microservice.treasurybrain.storage.service.GoodManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/8/13 17:03
 */
@Slf4j
@RestController
public class GoodManageResourceImpl implements GoodManageResource{

	@Resource
	private GoodManageService goodManageService;

	@Override
	public PageDTO<StorageEntityDTO> qryStorageEntityByPage(Map<String, Object> paramMap) {
		try {
			return goodManageService.qryStorageEntityByPage(paramMap);
		} catch (Exception e) {
			log.error("库存查询失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public StorageEntityDetailDTO qryStorageEntityDetail(String taskNo) {
		try {
			//参数校验：任务单编号不为空
			if (StringUtil.isNullorEmpty(taskNo)) {
				return new StorageEntityDetailDTO(RetCodeEnum.PARAM_LACK);
			}
			return goodManageService.qryStorageEntityDetail(taskNo);
		} catch (Exception e) {
			log.error("查看库存详情失败", e);
			return new StorageEntityDetailDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public PageDTO<StorageSortedEntityDTO> qryStorageInnerEntityByPage(Map<String, Object> paramMap) {
		try{
			return goodManageService.qryStorageInnerEntityByPage(paramMap);
		}catch (TradeException e){
			PageDTO<StorageSortedEntityDTO> retList = new PageDTO<>(RetCodeEnum.EXCEPTION);
			retList.setRetMsg(e.getMessage());
			return retList;
		}catch (Exception e){
			log.error("现金库存查询发生异常",e);
			return new  PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public StorageEntityTransferDTO qryStorageEntityByShelfNo(String shelfNo) {
		StorageEntityTransferDTO dto = new StorageEntityTransferDTO(RetCodeEnum.FAIL);
		try{
			//参数校验：笼车编号不为空
			if (StringUtil.isNullorEmpty(shelfNo)) {
				dto.setResult(RetCodeEnum.PARAM_LACK);
			}
			//获取笼车对应的任务单信息和钞袋列表
			dto = goodManageService.qryStorageEntityByShelfNo(shelfNo);
		}catch (Exception e){
			log.error("获取仓储实物信息失败:", e);
			dto.setRetException("获取仓储实物信息失败!");
		}
		return dto;
	}

	/**
	 * 根据线路编号查询笼车明细（出库）
	 */
	@Override
	public ListDTO qryShelfDetailByLineNo(Map<String, Object> paramMap) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			Map<String, Object> shelfDetailList = goodManageService.qryShelfDetailByLineNo(paramMap);
			List retList = (List) shelfDetailList.get("retList");
			dto.setRetList(retList);
			dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto.setRetMsg(RetCodeEnum.SUCCEED.getTip());
		} catch (Exception e) {
			log.error("查询线路失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}

	@Override
	public PageDTO<StorageCheckDTO> qryCheckInfoByPage(Map<String, Object> paramMap) {
		try {
			return goodManageService.qryCheckInfoByPage(paramMap);
		} catch (Exception e) {
			log.error("分页查询核库记录异常！", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO check(CheckInventoryDTO checkInventoryDTO) {
		try {
			if (StringUtil.isNullorEmpty(checkInventoryDTO.getClrCenterNo())){
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return goodManageService.check(checkInventoryDTO);
		} catch (Exception e) {
			log.error("核库异常", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 钞处领现金库确认
	 */
	@Override
	public DTO affirmOutNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO){
		log.info("------------[affirmOutNotesReceipt]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "金库确认失败");
		try {
			return goodManageService.affirmOutNotesReceipt(transferTaskInfoDTO);
		}catch (Exception e) {
			log.error("金库确认失败:", e);
			dto.setRetException("金库确认失败");
			return dto;
		}
	}
}
