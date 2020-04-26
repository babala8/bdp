package com.zjft.microservice.treasurybrain.storage.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.exception.TradeException;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityCheckDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferEntityDTO;
import com.zjft.microservice.treasurybrain.storage.service.GoodFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/8/13 17:03
 */
@Slf4j
@RestController
public class GoodFlowResourceImpl implements GoodFlowResource {

	@Resource
	private GoodFlowService goodFlowService;


	@Override
	public DTO StorageInnerOut(StorageSortedTransferDTO storageSortedTransferDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if(StringUtil.isNullorEmpty(storageSortedTransferDTO.getClrCenterNo())){
			dto.setResult(RetCodeEnum.PARAM_ERROR);
			dto.setRetMsg("金库编号为空");
			log.error(dto.getRetMsg());
			return dto;
		}
		if(null==storageSortedTransferDTO.getStorageEntities() || 0== storageSortedTransferDTO.getStorageEntities().size()){
			dto.setResult(RetCodeEnum.PARAM_ERROR);
			dto.setRetMsg("物品信息为空为空");
			log.error(dto.getRetMsg());
			return dto;
		}

		try{
			dto = goodFlowService.StorageInnerOut(storageSortedTransferDTO);
		}catch (TradeException e){
			log.error("库房物品调出到清分区发生异常",e);
			dto.setResult(RetCodeEnum.EXCEPTION);
			dto.setRetMsg(e.getMessage());
		}
		catch (Exception e){
			log.error("库房物品调出到清分区发生异常",e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public DTO StorageInnerIn(StorageSortedTransferDTO storageSortedTransferDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if(StringUtil.isNullorEmpty(storageSortedTransferDTO.getClrCenterNo())){
			dto.setResult(RetCodeEnum.PARAM_ERROR);
			dto.setRetMsg("金库编号为空");
			log.error(dto.getRetMsg());
			return dto;
		}
		if(0== storageSortedTransferDTO.getStorageEntities().size()){
			dto.setResult(RetCodeEnum.PARAM_ERROR);
			dto.setRetMsg("物品信息为空为空");
			log.error(dto.getRetMsg());
			return dto;
		}

		try{
			dto = goodFlowService.StorageInnerIn(storageSortedTransferDTO);
		}catch (TradeException e){
			log.error("物品从清分区调入库房发生异常",e);
			dto.setResult(RetCodeEnum.EXCEPTION);
			dto.setRetMsg(e.getMessage());
		}
		catch (Exception e){
			log.error("物品从清分区调入库房发生异常",e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public PageDTO<StorageSortedTransferDTO> qryInnerTransferByPage(Map<String, Object> params) {
		try {
			return goodFlowService.qryInnerTransferByPage(params);
		}catch (Exception e){
			log.error("分页查询调入调出记录发生异常：",e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO<StorageSortedTransferEntityDTO> qryInnerTransferDetail(String recordNo) {
		try {
			return goodFlowService.qryInnerTransferDetail(recordNo);
		}catch (Exception e){
			log.error("分页查询调入调出记录发生异常：",e);
			return new ListDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO accessCheck(StorageEntityCheckDTO storageEntityCheckDTO){
		try {
			return goodFlowService.accessCheck(storageEntityCheckDTO);
		}catch (Exception e){
			log.error("手持机扫描推送失败：",e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

}
