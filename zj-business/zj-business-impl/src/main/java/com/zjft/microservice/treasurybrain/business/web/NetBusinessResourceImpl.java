package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.business.service.NetBusinessService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/17
 */
@Slf4j
@RestController
public class NetBusinessResourceImpl implements NetBusinessResource {

	@Resource
	private NetBusinessService netBusinessService;

	/**
	 * 现金调拨申请
	 */
	@Override
	public DTO applyForCashTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------CashTransferResourceImpl[applyForCashTransfer]-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			//参数校验：金库编号，服务对象编号不为空
			if (StringUtil.isNullorEmpty(transferTaskInfoDTO.getClrCenterNo()) || StringUtil.isNullorEmpty(transferTaskInfoDTO.getCustomerNo()) ) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			Map<String, Object> retMap = netBusinessService.applyForCashTransfer(transferTaskInfoDTO);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			return dto;
		}catch (Exception e) {
			log.error("现金调拨单生成失败：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 修改现金调拨单
	 */
	@Override
	public DTO modCashTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------CashTransferResourceImpl[modCashTransfer]-------------");
		try {
			//参数校验：任务单编号不为空
			if (StringUtil.isNullorEmpty(transferTaskInfoDTO.getTaskNo())) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return netBusinessService.modCashTransfer(transferTaskInfoDTO);
		}catch (Exception e) {
			log.error("修改现金调拨单失败：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 网点调拨申请
	 */
	@Override
	public DTO applyForTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[applyForTransfer]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			Map<String, Object> retMap = netBusinessService.applyForTransfer(transferTaskInfoDTO);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		}catch (Exception e) {
			log.error("网点调拨任务单生成失败:", e);
			dto.setRetException("网点调拨任务单生成失败");
			return dto;
		}
		return dto;
	}

	/**
	 * 网点解现&寄库修改
	 */
	@Override
	public DTO modTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[modTransfer]-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			dto = netBusinessService.modForTransfer(transferTaskInfoDTO);
		}catch (Exception e) {
			log.error("网点调拨任务单修改失败:", e);
			dto.setRetException("网点调拨任务单修改失败");
			return dto;
		}
		return dto;
	}

	/**
	 * 网点领现申请
	 */
	@Override
	public DTO applyForReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[applyForReceipt]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			Map<String, Object> retMap = netBusinessService.applyForReceipt(transferTaskInfoDTO);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		}catch (Exception e) {
			log.error("网点领现任务单生成失败:", e);
			dto.setRetException("网点领现任务单生成失败");
			return dto;
		}
		return dto;
	}

	/**
	 * 网点领现修改
	 */
	@Override
	public DTO modReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[modReceipt]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			return netBusinessService.modReceipt(transferTaskInfoDTO);
		}catch (Exception e) {
			log.error("网点领现任务单修改失败:", e);
			dto.setRetException("网点领现任务单修改失败");
			return dto;
		}
	}

}
