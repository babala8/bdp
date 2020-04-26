package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.business.service.NotesReceiptService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 韩通
 * @since 2019/11/14
 */
@Slf4j
@RestController
public class NotesResourceImpl implements NotesResource {

	@Resource
	private NotesReceiptService notesReceiptService;

	@Override
	public DTO applyForNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[applyForClearCenter]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			Map<String, Object> retMap = notesReceiptService.applyForNotesReceipt(transferTaskInfoDTO);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		}catch (Exception e) {
			log.error("钞处领现任务单生成失败:", e);
			dto.setRetException("钞处领现任务单生成失败");
			return dto;
		}
		return dto;
	}


	@Override
	public DTO modNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[modClearCenter]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			return notesReceiptService.modNotesReceipt(transferTaskInfoDTO);
		}catch (Exception e) {
			log.error("钞处领现任务单修改失败:", e);
			dto.setRetException("钞处领现任务单修改失败");
			return dto;
		}
	}

	/**
	 * 钞处解现申请
	 */
	@Override
	public DTO transferForClearCenter(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[applyForTransfer]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			Map<String, Object> retMap = notesReceiptService.transferForClearCenter(transferTaskInfoDTO);
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
	 * 钞处解现修改
	 */
	@Override
	public DTO modTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		return notesReceiptService.modForTransfer(transferTaskInfoDTO);
	}

}
