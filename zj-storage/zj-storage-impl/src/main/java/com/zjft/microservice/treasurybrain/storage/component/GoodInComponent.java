package com.zjft.microservice.treasurybrain.storage.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityTransferDTO;
import com.zjft.microservice.treasurybrain.storage.po.StorageShelfTablePO;
import com.zjft.microservice.treasurybrain.storage.po.StorageTaskShelfUserPO;
import com.zjft.microservice.treasurybrain.storage.repository.StorageShelfTableMapper;
import com.zjft.microservice.treasurybrain.storage.repository.StorageTaskShelfUserMapper;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityDetailInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskPerRecorderInnerResource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ZjComponentResource(group = "storage")
public class GoodInComponent {

	@Resource
	private StorageTaskShelfUserMapper storageTaskShelfUserMapper;

	@Resource
	private StorageShelfTableMapper storageShelfTableMapper;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskPerRecorderInnerResource taskPerRecorderInnerResource;


	/**
	 * 入库前参数校验
	 */
	@ZjComponentMapping("goodInCheck")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodInCheck(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> taskNos) {

		Integer taskType = requestDTO.getTaskType();
		//Integer nextStatus = requestDTO.getStatus();
		String operateType = requestDTO.getOperateType();
		String lineNo = requestDTO.getLineNo();

		//校验线路，线路对应的任务单号不为空
		if (StringUtil.isNullorEmpty(lineNo)) {
			returnDTO.setRetMsg("线路编号为空，无法进行入库操作");
			return "fail";
		}

		List<String> curStatus = serviceInnerResource.mayStatusConverts(operateType, taskType);
//		String planFiinishDate = CalendarUtil.getSysTimeYMD();
		int totalSize = 0;
		List<String> totalList = new ArrayList<>();
		for (String status : curStatus) {
			Map<String, Object> map = new HashMap<>();
			map.put("lineNo", lineNo);
			map.put("status", status);
			map.put("taskType", taskType.toString());
			List<String> taskList = taskInnerResource.qryTaskNoByLineNo(map);
			totalSize += taskList.size();
			totalList.addAll(taskList);
		}
		if (totalSize == 0) {
			returnDTO.setRetMsg("该线路下没有对应的任务编号");
			return "fail";
		}
		taskNos.addAll(totalList);
		return "ok";
	}

	/**
	 * 入库前参数校验(钞处解现入库，没有线路)
	 * 需要区分自动化库和非自动化库，非自动化库通道门审核确认后将任务单状态置为已入库，
	 * 自动化库将托盘搬运至库位后更新任务单状态为已入库。
	 */
	@ZjComponentMapping("goodInBankNoteCheck")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodInBankNoteCheck(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> taskNos) {

		Integer taskType = requestDTO.getTaskType();
		//Integer nextStatus = requestDTO.getStatus();
		String operateType = requestDTO.getOperateType();

		List<String> curStatus = serviceInnerResource.mayStatusConverts(operateType, taskType);
//		String planFiinishDate = CalendarUtil.getSysTimeYMD();
		int totalSize = 0;
		List<String> totalList = new ArrayList<>();
		String taskNo = requestDTO.getTaskNo();
		for (String status : curStatus) {
			Map<String, Object> map1 = new HashMap<>();
			map1.put("status", status);
			map1.put("taskType", taskType.toString());
			List<String> taskList = taskInnerResource.qryTaskNoByLineNo(map1);
			totalSize += taskList.size();
			totalList.addAll(taskList);
		}
		if (totalSize == 0 || !totalList.contains(taskNo)) {
			//returnDTO.setRetMsg("没有对应的任务编号");
			returnDTO.setRetMsg("此任务单无法进行入库操作！");
			return "fail";
		}
		//taskNos.addAll(totalList);
		taskNos.add(taskNo);
		return "ok";
	}


	/**
	 * 入库前参数校验（如果有车）
	 */
	@ZjComponentMapping("goodInCheckWithShelf")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodInCheckWithShelf(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> taskNos) {

		//校验笼车编号不为空
		String shelfNo = requestDTO.getShelfNo();
		if (StringUtil.isNullorEmpty(shelfNo)) {
			returnDTO.setRetMsg("笼车编号为空，无法进行入库操作");
			return "fail";
		}
		//校验任务单中是否存在未匹配的容器（钞袋），如果存在则无法进行入库操作
		//前端传来已匹配的容器（钞袋）列表
		List<String> goodsMatch = requestDTO.getEntityNos();
		//任务单对应所有容器（钞袋）列表
		for (String taskNo : taskNos) {
			List<String> goodsAll = taskEntityInnerResource.getGoodsByTaskNo(taskNo);
			for (String good : goodsAll) {
				if (!goodsMatch.contains(good)) {
					returnDTO.setRetMsg("存在未匹配的容器，无法进行入库操作");
					return "fail";
				}
			}
		}

		//查询该笼车信息是否存在
		if (storageShelfTableMapper.qryShelfNo(shelfNo) < 1) {
			returnDTO.setRetMsg("该笼车不存在或未进行登记，请确认");
			return "fail";
		}

		//查询笼车是否被占用
		StorageShelfTablePO storageShelfTablePO = new StorageShelfTablePO();
		storageShelfTablePO.setShelfNo(shelfNo);
		storageShelfTablePO.setStatus(2);
		if (storageShelfTableMapper.qryShelfUnused(storageShelfTablePO) == 1) {
			returnDTO.setRetMsg("该笼车已被占用，请确认");
			return "fail";
		}

		return "ok";
	}

	/**
	 * 入库
	 */
	@ZjComponentMapping("goodIn")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodIn(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> taskNos) {

		//Integer status = requestDTO.getStatus();
		int status = serviceInnerResource.qryNextByOperateType(requestDTO.getOperateType());
		//增加任务执行记录表 状态为2-入库
		List<String> goods = requestDTO.getEntityNos();
		for (String containerNo : goods) {
			//根据容器编号查询对应的任务单编号
			TaskEntityDTO taskEntityDTO = taskEntityInnerResource.qryByContainerNo(containerNo);
			String taskNo = taskEntityDTO.getTaskNo();
//			TaskEntityDetailDTO taskEntityDetailDTO = taskEntityDetailInnerResource.selectOneByTaskNo(taskNo, containerNo);
			Map<String, Object> map = new HashMap<>();
			map.put("id", java.util.UUID.randomUUID().toString().replace("-", ""));
			map.put("taskNo", taskNo);
			map.put("containerNo", containerNo);
			map.put("performTime", CalendarUtil.getSysTimeYMDHMS());
			map.put("performType", StatusEnum.TaskPerRecorderTypeTauro.TYPE_IN_STORAGE.getType());
			map.put("opNo1", ServletRequestUtil.getUsername());
			map.put("containerType", taskEntityDTO.getProductNo());
			int x = taskPerRecorderInnerResource.insertByMap(map);
			if (x != 1) {
				log.error("生成任务执行记录表失败");
				throw new RuntimeException("生成任务执行记录表失败");
			}
		}

		//更新任务单 状态为 入库（状态编号前端传）
		//如果是自动化库，不执行任务单状态更新操作
		String clrCenterNo = requestDTO.getClrCenterNo();
		if (clrCenterInnerResource.qryClrCenterIsAuto(clrCenterNo)) {
			returnDTO.setResult(RetCodeEnum.SUCCEED);
			return "ok";
		}
		for (String taskNo : taskNos) {
			Map<String, Object> map1 = new HashMap<>();
			map1.put("taskNo", taskNo);
			map1.put("status", status);
			int z = taskInnerResource.updateByPrimaryKeyMap(map1);
			if (z != 1) {
				log.error("更新表 [TASK_TABLE] 入库状态失败");
				throw new RuntimeException("更新订单任务编号[" + taskNo + "]入库状态失败");
			}
		}

		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}

	/**
	 * 入库(如果有车)
	 */
	@ZjComponentMapping("goodInWithShelf")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodInWithShelf(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> taskNos) {
		String shelfNo = requestDTO.getShelfNo();
		//增加一条笼车使用记录 状态为 1-装车
		for (String taskNo : taskNos) {
			StorageTaskShelfUserPO storageTaskShelfUserPO = new StorageTaskShelfUserPO();
			storageTaskShelfUserPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
			storageTaskShelfUserPO.setTaskNo(taskNo);
			storageTaskShelfUserPO.setShelfNo(shelfNo);
			storageTaskShelfUserPO.setOpType(StatusEnum.TaskShelfUserTypeTauro.TYPE_IN.getType());
			storageTaskShelfUserPO.setOpTime(CalendarUtil.getSysTimeYMDHMS());
			int y = storageTaskShelfUserMapper.insert(storageTaskShelfUserPO);
			if (y != 1) {
				log.error("生成笼车装车记录失败");
				throw new RuntimeException("生成笼车装车记录失败");
			}
		}

		//更新笼车状态为 3-占用
		StorageShelfTablePO storageShelfTablePO = new StorageShelfTablePO();
		storageShelfTablePO.setShelfNo(shelfNo);
		storageShelfTablePO.setStatus(StatusEnum.ShelfTableStatus.STATUS_USED.getStatus());
		int b = storageShelfTableMapper.updateStatusByNo(storageShelfTablePO);
		if (b != 1) {
			log.error("更新笼车状态失败");
			throw new RuntimeException("更新笼车状态失败");
		}

		//更新任务单  将笼车与任务单绑定,并更新任务单状态为已入库
		String operateType = requestDTO.getOperateType();
		Integer status = serviceInnerResource.qryNextByOperateType(operateType);
		for (String taskNo : taskNos) {
			Map<String, Object> map = new HashMap<>();
			map.put("taskNo", taskNo);
			map.put("shelfNo", shelfNo);
			map.put("status", status);
			int z = taskInnerResource.updateByPrimaryKeyMap(map);
			if (z != 1) {
				log.error("更新表 [TASK_TABLE] 入库状态失败");
				throw new RuntimeException("更新订单任务编号[" + taskNo + "]入库状态失败");
			}
		}

		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}

	/**
	 * 入库失败
	 */
	@ZjComponentMapping("goodInFail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodInFail(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> taskNos) {
		returnDTO.setRetCode("FF");
		return "fail";
	}
}
