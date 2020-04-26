package com.zjft.microservice.treasurybrain.storage.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.storage.dto.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/8/13 16:49
 */
@Api(tags = "仓储中心：实物信息管理",value = "仓储中心：实物信息管理")
public interface GoodManageResource {

	String PREFIX = "${storage:}/v2/goodManage";

	/**
	 *
	 * 分页查询库房
	 *
	 * @param paramMap 金库编号 任务单编号 笼车编号 当前页 每页大小
	 * @return
	 */
	@ApiOperation(value = "库存查询",notes = "库存查询")
	@GetMapping(path = PREFIX)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo",value = "所属金库编号", paramType = "query"),
			@ApiImplicitParam(name = "taskNo",value = "任务单编号", paramType = "query"),
			@ApiImplicitParam(name = "shelfNo",value = "笼车编号", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<StorageEntityDTO> qryStorageEntityByPage(@RequestParam @ApiIgnore Map<String,Object> paramMap);

	@GetMapping(PREFIX + "/detail")
	@ApiOperation(value = "查看库存详情", notes = "查看库存详情")
	@ApiImplicitParam(name = "taskNo", value = "任务单编号", required = true, paramType = "query")
	StorageEntityDetailDTO qryStorageEntityDetail(@RequestParam("taskNo") String taskNo);


	/**
	 * 现金库存查询
	 *
	 * @param paramMap
	 * @return
	 */
	@ApiOperation(value = "库房物品信息分页查询",notes = "库房物品信息分页查询")
	@GetMapping(path = PREFIX+"/storageEntity")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo",value = "所属金库编号", paramType = "query"),
			@ApiImplicitParam(name = "productNo",value = "容器类型", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<StorageSortedEntityDTO> qryStorageInnerEntityByPage(@RequestParam @ApiIgnore Map<String,Object> paramMap);

	/**
	 * 获取笼车对应的任务单信息和钞袋列表
	 *
	 * @param shelfNo 笼车编号
	 * @return StorageEntityTransferDTO
	 */
	@GetMapping(path = PREFIX + "/showStorageEntityByShelfNo")
	@ApiOperation(value = "获取笼车对应的仓储实物信息", notes = "获取笼车对应的任务单信息和钞袋列表")
	@ApiImplicitParam(name = "shelfNo", value = "笼车编号", required = true, paramType = "query")
	StorageEntityTransferDTO qryStorageEntityByShelfNo(@RequestParam("shelfNo") String shelfNo);

	/**
	 * 根据线路编号查询笼车明细（出库）
	 */
	@GetMapping(PREFIX + "/qryShelfDetailByLineNo")
	@ApiOperation(value = "根据线路编号查询笼车明细（出库）", notes = "根据线路编号查询笼车明细（出库）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lineNoList", value = "线路编号数组", required = true, paramType = "query"),
			@ApiImplicitParam(name = "taskType", value = "任务单类型", paramType = "query")
	})
	ListDTO qryShelfDetailByLineNo(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/qryCheckInfo")
	@ApiOperation(value = "分页查询核库信息", notes = "分页查询核库信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "startTime", value = "核库开始时间", paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "核库结束时间", paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo", value = "金库编号", paramType = "query"),
			@ApiImplicitParam(name = "flag", value = "金额是否一致", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<StorageCheckDTO> qryCheckInfoByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@ApiOperation(value = "核库", notes = "核库")
	@PostMapping(PREFIX + "/check")
	DTO check(@RequestBody CheckInventoryDTO checkInventoryDTO);

	/**
	 * 钞处领现单金库确认
	 */
	@PutMapping(PREFIX+"/affirmOut")
	@ApiOperation(value = "钞处领现单金库确认", notes = "钞处领现单金库确认")
	@ApiOperationSupport(ignoreParameters = {"transferTaskInfoDTO.planFinishDate","transferTaskInfoDTO.customerType","transferTaskInfoDTO.transferType","transferTaskInfoDTO.status","transferTaskInfoDTO.nextStatus","transferTaskInfoDTO.statusDesp","transferTaskInfoDTO.clrCenterNo","transferTaskInfoDTO.clrCenterName","transferTaskInfoDTO.carNumber","transferTaskInfoDTO.lineNo","transferTaskInfoDTO.lineName","transferTaskInfoDTO.opNo1","transferTaskInfoDTO.opName1","transferTaskInfoDTO.opNo2","transferTaskInfoDTO.opName2","transferTaskInfoDTO.createOpNo","transferTaskInfoDTO.createOpName","transferTaskInfoDTO.createTime","transferTaskInfoDTO.endTime","transferTaskInfoDTO.note", "transferTaskInfoDTO.modOpNo","transferTaskInfoDTO.modeOpName","transferTaskInfoDTO.modeTime","transferTaskInfoDTO.modeNote","transferTaskInfoDTO.auditOpNo", "transferTaskInfoDTO.auditOpName","transferTaskInfoDTO.auditTime","transferTaskInfoDTO.auditNote","transferTaskInfoDTO.addnotesPlanNo","transferTaskInfoDTO.planDistance", "transferTaskInfoDTO.planTimeCost","transferTaskInfoDTO.urgentFlag",
			"transferTaskInfoDTO.tid","transferTaskInfoDTO.retCode","transferTaskInfoDTO.retMsg", "transferTaskInfoDTO.transferCurrencyTypeDTO.id","transferTaskInfoDTO.transferCurrencyTypeDTO.amount","transferTaskInfoDTO.transferCurrencyTypeDTO.currencyType","transferTaskInfoDTO.transferCurrencyTypeDTO.currencyCode","transferTaskInfoDTO.transferCurrencyTypeDTO.denomination","transferTaskInfoDTO.transferTaskDetailDTO.id","transferTaskInfoDTO.transferTaskDetailDTO.tid","transferTaskInfoDTO.transferTaskDetailDTO.retCode","transferTaskInfoDTO.transferTaskDetailDTO.retMsg","transferTaskInfoDTO.transferTaskDetailDTO.taskNo","transferTaskInfoDTO.transferTaskDetailDTO.applyAmount","transferTaskInfoDTO.transferTaskDetailDTO.productNo","transferTaskInfoDTO.transferTaskDetailDTO.outDate","transferTaskInfoDTO.transferTaskDetailDTO.depositType","transferTaskInfoDTO.transferTaskDetailDTO.direction","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.id","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS.amount","transferTaskInfoDTO.transferTaskDetailDTO.transferCurrencyTypeDTOS"})
	DTO affirmOutNotesReceipt(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);


}
