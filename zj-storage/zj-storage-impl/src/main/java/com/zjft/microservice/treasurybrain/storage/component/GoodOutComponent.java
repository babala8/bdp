package com.zjft.microservice.treasurybrain.storage.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityTransferDTO;
import com.zjft.microservice.treasurybrain.storage.po.StorageShelfTablePO;
import com.zjft.microservice.treasurybrain.storage.po.StorageTaskShelfUserPO;
import com.zjft.microservice.treasurybrain.storage.repository.StorageShelfTableMapper;
import com.zjft.microservice.treasurybrain.storage.repository.StorageTaskShelfUserMapper;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseEntityTransferDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityDetailInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskPerRecorderInnerResource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 葛瑞莲
 * @since 2019/8/27
 */
@Slf4j
@ZjComponentResource(group = "storage")
public class GoodOutComponent {

	@Resource
	private StorageTaskShelfUserMapper storageTaskShelfUserMapper;

	@Resource
	private StorageShelfTableMapper storageShelfTableMapper;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	TaskPerRecorderInnerResource taskPerRecorderInnerResource;

	/**
	 * 出库前参数校验
	 */
	@ZjComponentMapping("goodOutCheck")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodOutCheck(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> tempObj) {
		//校验笼车编号不为空
		String shelfNo = requestDTO.getShelfNo();
		if (StringUtil.isNullorEmpty(shelfNo)) {
			returnDTO.setResult(RetCodeEnum.PARAM_LACK);
			return "fail";
		}
		//检验笼车编号是否存在
		if (storageShelfTableMapper.qryShelfNo(shelfNo) < 1) {
			returnDTO.setRetMsg("该笼车不存在或未进行登记，请确认");
			return "fail";
		}

		//获取笼车对应的任务单信息和钞袋列表
		//根据笼车编号查询对应的任务单编号，线路编号
		List<StorageUseEntityTransferDTO> dtoList = taskInnerResource.getLine(shelfNo);
		if (dtoList == null || dtoList.size() < 1) {
			returnDTO.setRetMsg("未匹配到该笼车编号下的待出库任务单");
			return "fail";
		}
		if (dtoList.size() > 1) {
			returnDTO.setRetMsg("该笼车编号下对应有多条线路");
			return "fail";
		}
		//根据笼车编号找到对应的实物列表
		Integer taskType = requestDTO.getTaskType();
		//fixme  暂时改为根据任务单类型和笼车编号区分不同的任务单
		List<String> taskList = taskInnerResource.getTaskListByShelfNoAndTaskType(shelfNo,taskType);
//		List<String> taskList = taskInnerResource.getTaskListByShelfNo(shelfNo);
		List<String> goods = new ArrayList<>();
		if (taskList.size() < 1) {
			returnDTO.setRetMsg("未匹配到该笼车编号下的待出库任务单");
			return "fail";
		}
		for (String taskNo : taskList) {
			if (StringUtil.isNullorEmpty(taskNo)) {
				returnDTO.setRetMsg("该笼车编号下的待出库任务单编号为空");
				return "fail";
			}
			//查询对应的实物列表
			List<String> goodList = taskEntityInnerResource.getGoodsByTaskNo(taskNo);
			goods.addAll(goodList);

			//验证任务单状态是否能更新为指定状态
//			Integer status = requestDTO.getStatus();
			String operateType = requestDTO.getOperateType();
			Integer curStatus = Optional.ofNullable(taskInnerResource.qryCurStatus(taskNo)).orElse(-1);
			int count = serviceInnerResource.isStatusConvertMatch(taskType, curStatus, operateType);
			if (count < 1) {
				returnDTO.setRetMsg("当前任务状态无法进行[" + operateType + "]操作");
				return "fail";
			}
		}
		tempObj.addAll(taskList);
		requestDTO.setEntityNos(goods);
		requestDTO.setLineNo(dtoList.get(0).getLineNo());
		requestDTO.setLineName(dtoList.get(0).getLineName());

		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}

	/**
	 * 钞处领现出库前参数校验(不考虑线路)
	 */
	@ZjComponentMapping("goodOutTransferCheck")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodOutTransferCheck(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> tempObj) {
		//校验笼车编号不为空
		String shelfNo = requestDTO.getShelfNo();
		if (StringUtil.isNullorEmpty(shelfNo)) {
			returnDTO.setResult(RetCodeEnum.PARAM_LACK);
			return "fail";
		}
		//检验笼车编号是否存在
		if (storageShelfTableMapper.qryShelfNo(shelfNo) < 1) {
			returnDTO.setRetMsg("该笼车不存在或未进行登记，请确认");
			return "fail";
		}

		//获取笼车对应的任务单信息和钞袋列表
		//根据笼车编号查询对应的任务单编号，线路编号
//		List<StorageEntityTransfer> storageEntityTransfers = storageTaskTableMapper.getLine(shelfNo);
//		if (storageEntityTransfers == null || storageEntityTransfers.size() < 1 ) {
//			returnDTO.setRetMsg("未匹配到该笼车编号下的待出库任务单");
//			return "fail";
//		}
//		if(storageEntityTransfers.size() > 1){
//			returnDTO.setRetMsg("该笼车编号下对应有多条线路");
//			return "fail";
//		}
		//根据任务单编号找到对应的实物列表
		Integer taskType = requestDTO.getTaskType();
		//fixme  暂时改为根据任务单类型和笼车编号区分不同的任务单
		List<String> taskList = taskInnerResource.getTaskListByShelfNoAndTaskType(shelfNo,taskType);
//		List<String> taskList = taskInnerResource.getTaskListByShelfNo(shelfNo);
		List<String> goods = new ArrayList<>();
		if (taskList.size() < 1) {
			returnDTO.setRetMsg("未匹配到该笼车编号下的待出库任务单");
			return "fail";
		}
		for (String taskNo : taskList) {
			if (StringUtil.isNullorEmpty(taskNo)) {
				returnDTO.setRetMsg("该笼车编号下的待出库任务单编号为空");
				return "fail";
			}
			//查询对应的实物列表
			List<String> goodList = taskEntityInnerResource.getGoodsByTaskNo(taskNo);
			goods.addAll(goodList);

			//验证任务单状态是否能更新为指定状态
//			Integer status = requestDTO.getStatus();
			String operateType = requestDTO.getOperateType();
			Integer curStatus = Optional.ofNullable(taskInnerResource.qryCurStatus(taskNo)).orElse(-1);
			int count = serviceInnerResource.isStatusConvertMatch(taskType, curStatus, operateType);
			if (count < 1) {
				returnDTO.setRetMsg("当前任务状态无法进行[" + operateType + "]操作");
				return "fail";
			}
		}
		tempObj.addAll(taskList);
		requestDTO.setEntityNos(goods);
//		requestDTO.setLineNo(storageEntityTransfers.get(0).getLineNo());
//		requestDTO.setLineName(storageEntityTransfers.get(0).getLineName());

		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}

	/**
	 * 出库
	 */
//	@Transactional(rollbackFor = Exception.class)
	@ZjComponentMapping("goodOut")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodOut(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> tempObj) {
//		String shelfNo = requestDTO.getShelfNo();
//		Integer status = requestDTO.getStatus();
		Integer status = serviceInnerResource.qryNextByOperateType(requestDTO.getOperateType());
		for (String taskNo : tempObj) {
			//增加任务执行记录表 状态为 出库
			//查询对应的实物列表
			List<String> goods = taskEntityInnerResource.getGoodsByTaskNo(taskNo);
			for (String containerNo : goods) {
				TaskEntityDTO taskEntityDTO = taskEntityInnerResource.selectOneByTaskNo(taskNo,containerNo);
				Map<String, Object> map = new HashMap<>();
				map.put("id", java.util.UUID.randomUUID().toString().replace("-", ""));
				map.put("taskNo", taskNo);
				map.put("containerNo", containerNo);
				map.put("performTime", CalendarUtil.getSysTimeYMDHMS());
				map.put("performType", StatusEnum.TaskPerRecorderTypeTauro.TYPE_OUT_STORAGE.getType());
				map.put("opNo1", ServletRequestUtil.getUsername());
				map.put("containerType", taskEntityDTO.getProductNo());
				int x = taskPerRecorderInnerResource.insertByMap(map);
				if (x != 1) {
					log.error("生成任务执行记录表失败");
					throw new RuntimeException("生成任务执行记录表失败");
				}
			}
			//增加一条笼车使用记录 状态为 2-卸货
			/*StorageTaskShelfUserPO storageTaskShelfUserPO = new StorageTaskShelfUserPO();
			storageTaskShelfUserPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
			storageTaskShelfUserPO.setTaskNo(taskNo);
			storageTaskShelfUserPO.setShelfNo(shelfNo);
			storageTaskShelfUserPO.setOpType(StatusEnum.TaskShelfUserTypeTauro.TYPE_OUT.getType());
			storageTaskShelfUserPO.setOpTime(CalendarUtil.getSysTimeYMDHMS());
			int y = storageTaskShelfUserMapper.insert(storageTaskShelfUserPO);
			if (y != 1) {
				log.error("生成笼车装车记录失败");
				throw new RuntimeException("生成笼车装车记录失败");
			}*/
			//更新任务单状态  将笼车与任务单解绑
			Map<String, Object> map1 = new HashMap<>();
			map1.put("taskNo", taskNo);
			map1.put("status", status);
			int z = taskInnerResource.updateByPrimaryKeyMap(map1);
			if (z != 1) {
				log.error("更新表 [TASK_TABLE] 出库状态失败");
				throw new RuntimeException("更新订单任务编号[" + taskNo + "]出库状态失败");
			}
		}
		//更新笼车状态
		/*StorageShelfTablePO storageShelfTablePO = new StorageShelfTablePO();
		storageShelfTablePO.setShelfNo(shelfNo);
		storageShelfTablePO.setStatus(StatusEnum.ShelfTableStatus.STATUS_ALREADY_ENABLE.getStatus());
		int b = storageShelfTableMapper.updateStatusByNo(storageShelfTablePO);
		if (b != 1) {
			log.error("更新笼车状态失败");
			throw new RuntimeException("更新笼车状态失败");
		}*/
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}

	/**
	 * 笼车解绑
	 */
//	@Transactional(rollbackFor = Exception.class)
	@ZjComponentMapping("goodOutShelfUntied")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodOutShelfUntied(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> tempObj) {
		String shelfNo = requestDTO.getShelfNo();
		for (String taskNo : tempObj) {
			//增加一条笼车使用记录 状态为 2-卸货
			StorageTaskShelfUserPO storageTaskShelfUserPO = new StorageTaskShelfUserPO();
			storageTaskShelfUserPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
			storageTaskShelfUserPO.setTaskNo(taskNo);
			storageTaskShelfUserPO.setShelfNo(shelfNo);
			storageTaskShelfUserPO.setOpType(StatusEnum.TaskShelfUserTypeTauro.TYPE_OUT.getType());
			storageTaskShelfUserPO.setOpTime(CalendarUtil.getSysTimeYMDHMS());
			int y = storageTaskShelfUserMapper.insert(storageTaskShelfUserPO);
			if (y != 1) {
				log.error("生成笼车装车记录失败");
				throw new RuntimeException("生成笼车装车记录失败");
			}
			//更新任务单状态  将笼车与任务单解绑
			Map<String, Object> map2 = new HashMap<>();
			map2.put("taskNo", taskNo);
			map2.put("shelfNo", "");
			int z = taskInnerResource.updateByPrimaryKeyMap(map2);
			if (z != 1) {
				log.error("笼车与任务单[" + taskNo + "]解绑失败");
				throw new RuntimeException("笼车与任务单[" + taskNo + "]解绑失败");
			}

		}
		//更新笼车状态
		StorageShelfTablePO storageShelfTablePO = new StorageShelfTablePO();
		storageShelfTablePO.setShelfNo(shelfNo);
		storageShelfTablePO.setStatus(StatusEnum.ShelfTableStatus.STATUS_ALREADY_ENABLE.getStatus());
		int b = storageShelfTableMapper.updateStatusByNo(storageShelfTablePO);
		if (b != 1) {
			log.error("更新笼车状态失败");
			throw new RuntimeException("更新笼车状态失败");
		}

		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}

	/**
	 * 出库失败
	 */
	@ZjComponentMapping("goodOutFail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String goodOutFail(StorageEntityTransferDTO requestDTO, DTO returnDTO, List<String> tempObj) {
		returnDTO.setRetCode("FF");
		return "fail";
	}


}
