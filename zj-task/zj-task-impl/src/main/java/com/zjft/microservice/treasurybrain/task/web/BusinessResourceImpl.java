package com.zjft.microservice.treasurybrain.task.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.task.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.task.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-03-01
 */
@Slf4j
@RestController
public class BusinessResourceImpl implements BusinessResource {

	@Resource
	private BusinessService businessService;
	/**
	 * 网点调拨申请
	 */
	@Override
	public DTO applyForTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[applyForTransfer]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			Map<String, Object> retMap = businessService.applyForTransfer(transferTaskInfoDTO);
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
			dto = businessService.modForTransfer(transferTaskInfoDTO);
		}catch (Exception e) {
			log.error("网点调拨任务单修改失败:", e);
			dto.setRetException("网点调拨任务单修改失败");
			return dto;
		}
		return dto;
	}
}
