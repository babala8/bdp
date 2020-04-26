package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author 韩通
 * @since 2019/11/14
 */
@Api(tags = "业务中心：钞处业务管理",value = "业务中心：钞处业务管理")
public interface NotesResource {
	String PREFIX = "${business:}/v2/notes";

	/**
	 * 钞处领现申请 已改
	 */
	@PostMapping(PREFIX+"/receipt")
	@ApiOperation(value = "钞处领现申请", notes = "钞处领现申请")
	@ApiOperationSupport(ignoreParameters = {"transferTaskInfoDTO.taskNo","transferTaskInfoDTO.status","transferTaskInfoDTO.nextStatus","transferTaskInfoDTO.lineNo","transferTaskInfoDTO.transferType", "transferTaskInfoDTO.operateType","transferTaskInfoDTO.statusDesp","transferTaskInfoDTO.clrCenterName","transferTaskInfoDTO.carNumber","transferTaskInfoDTO.lineName", "transferTaskInfoDTO.opNo1","transferTaskInfoDTO.opName1","transferTaskInfoDTO.opNo2","transferTaskInfoDTO.opName2","transferTaskInfoDTO.createOpNo", "transferTaskInfoDTO.createOpName","transferTaskInfoDTO.createTime","transferTaskInfoDTO.endTime","transferTaskInfoDTO.modOpNo","transferTaskInfoDTO.modeOpName","transferTaskInfoDTO.modeTime","transferTaskInfoDTO.shelfNo",
			"transferTaskInfoDTO.modeNote","transferTaskInfoDTO.auditOpNo","transferTaskInfoDTO.auditOpName","transferTaskInfoDTO.auditTime","transferTaskInfoDTO.auditNote","transferTaskInfoDTO.addnotesPlanNo","transferTaskInfoDTO.planDistance","transferTaskInfoDTO.planTimeCost","transferTaskInfoDTO.urgentFlag","transferTaskInfoDTO.tid","transferTaskInfoDTO.retCode","transferTaskInfoDTO.retMsg", "transferTaskInfoDTO.transferCurrencyTypeDTO.id","transferTaskInfoDTO.transferTaskDetailDTO.id","transferTaskInfoDTO.transferTaskDetailDTO.tid","transferTaskInfoDTO.transferTaskDetailDTO.retCode","transferTaskInfoDTO.transferTaskDetailDTO.retMsg","transferTaskInfoDTO.transferTaskDetailDTO.taskNo","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.id"})
	DTO applyForNotesReceipt(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);


	/**
	 * 钞处领现修改 已改
	 */
	@PutMapping(PREFIX+"/receipt")
	@ApiOperation(value = "钞处领现修改", notes = "钞处领现修改")
	@ApiOperationSupport(ignoreParameters = {"transferTaskInfoDTO.customerType","transferTaskInfoDTO.transferType","transferTaskInfoDTO.status","transferTaskInfoDTO.nextStatus","transferTaskInfoDTO.statusDesp","transferTaskInfoDTO.clrCenterNo","transferTaskInfoDTO.clrCenterName","transferTaskInfoDTO.carNumber","transferTaskInfoDTO.lineNo","transferTaskInfoDTO.lineName","transferTaskInfoDTO.opNo1","transferTaskInfoDTO.opName1","transferTaskInfoDTO.opNo2","transferTaskInfoDTO.opName2","transferTaskInfoDTO.createOpNo","transferTaskInfoDTO.createOpName","transferTaskInfoDTO.createTime","transferTaskInfoDTO.endTime","transferTaskInfoDTO.note", "transferTaskInfoDTO.modOpNo","transferTaskInfoDTO.modeOpName","transferTaskInfoDTO.modeTime","transferTaskInfoDTO.auditOpNo", "transferTaskInfoDTO.auditOpName","transferTaskInfoDTO.auditTime","transferTaskInfoDTO.auditNote","transferTaskInfoDTO.addnotesPlanNo","transferTaskInfoDTO.planDistance", "transferTaskInfoDTO.planTimeCost","transferTaskInfoDTO.urgentFlag","transferTaskInfoDTO.shelfNo",
			"transferTaskInfoDTO.tid","transferTaskInfoDTO.retCode","transferTaskInfoDTO.retMsg", "transferTaskInfoDTO.transferCurrencyTypeDTO.id","transferTaskInfoDTO.transferTaskDetailDTO.id","transferTaskInfoDTO.transferTaskDetailDTO.tid","transferTaskInfoDTO.transferTaskDetailDTO.retCode","transferTaskInfoDTO.transferTaskDetailDTO.retMsg","transferTaskInfoDTO.transferTaskDetailDTO.taskNo","transferTaskInfoDTO.transferTaskDetailDTO.applyAmount","transferTaskInfoDTO.transferTaskDetailDTO.containerNo","transferTaskInfoDTO.transferTaskDetailDTO.containerType","transferTaskInfoDTO.transferTaskDetailDTO.outDate","transferTaskInfoDTO.transferTaskDetailDTO.depositType","transferTaskInfoDTO.transferTaskDetailDTO.direction","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.id","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.amount","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.currencyType","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.currencyCode","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.denomination"})
	DTO modNotesReceipt(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);


	/**
	 * 钞处解现申请  已改
	 */
	@PostMapping(PREFIX+"/transfer")
	@ApiOperation(value = "钞处解现申请", notes = "钞处解现申请")
	@ApiOperationSupport(ignoreParameters = {"transferTaskInfoDTO.taskNo","transferTaskInfoDTO.status","transferTaskInfoDTO.nextStatus","transferTaskInfoDTO.lineNo","transferTaskInfoDTO.transferType", "transferTaskInfoDTO.operateType","transferTaskInfoDTO.statusDesp","transferTaskInfoDTO.clrCenterName","transferTaskInfoDTO.carNumber","transferTaskInfoDTO.lineName", "transferTaskInfoDTO.opNo1","transferTaskInfoDTO.opName1","transferTaskInfoDTO.opNo2","transferTaskInfoDTO.opName2","transferTaskInfoDTO.createOpNo", "transferTaskInfoDTO.createOpName","transferTaskInfoDTO.createTime","transferTaskInfoDTO.endTime","transferTaskInfoDTO.modOpNo","transferTaskInfoDTO.modeOpName","transferTaskInfoDTO.modeTime",
			"transferTaskInfoDTO.modeNote","transferTaskInfoDTO.auditOpNo","transferTaskInfoDTO.auditOpName","transferTaskInfoDTO.auditTime","transferTaskInfoDTO.auditNote","transferTaskInfoDTO.addnotesPlanNo","transferTaskInfoDTO.planDistance","transferTaskInfoDTO.planTimeCost","transferTaskInfoDTO.urgentFlag","transferTaskInfoDTO.tid","transferTaskInfoDTO.retCode","transferTaskInfoDTO.retMsg", "transferTaskInfoDTO.transferCurrencyTypeDTO.id","transferTaskInfoDTO.transferTaskDetailDTO.id","transferTaskInfoDTO.transferTaskDetailDTO.tid","transferTaskInfoDTO.transferTaskDetailDTO.retCode","transferTaskInfoDTO.transferTaskDetailDTO.retMsg","transferTaskInfoDTO.transferTaskDetailDTO.taskNo","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.id"})
	DTO transferForClearCenter(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

	/** 已改
	 * 钞处解现修改
	 */
	@PutMapping(PREFIX+"/transfer")
	@ApiOperation(value = "钞处解现修改", notes = "钞处解现修改")
	DTO modTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);
}
