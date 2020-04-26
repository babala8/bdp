package com.zjft.microservice.treasurybrain.clearcenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.clearcenter.dto.*;
import com.zjft.microservice.treasurybrain.clearcenter.po.TaskCheckPO;
import com.zjft.microservice.treasurybrain.clearcenter.po.TaskTablePO;
import com.zjft.microservice.treasurybrain.clearcenter.repository.BanknoteTaskCheckMapper;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityTableInfoDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskDetailInfoInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityDetailInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 周 昊
 * @author 常 健
 * @since 2020/1/13
 */
@Slf4j
@ZjComponentResource(group = "cashBusiness")
public class CashBusinessComponent {


	@Resource
	BanknoteTaskCheckMapper banknoteTaskCheckMapper;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskDetailInfoInnerResource taskDetailInfoInnerResource;

	@Resource
	private TaskEntityDetailInnerResource taskEntityDetailInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;


	/**
	 * 设备配钞
	 */
	@ZjComponentMapping("loadNoteCheck")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String loadNoteCheck(LoadNoteDTO requestDTO, DTO returnDTO, String string) {
		/*
		 * 设备配钞
		 * 1.校验加钞任务状态
		 * 2.如果任务单已配钞，则回退配钞记录
		 * 3.检验钞箱钞袋是否可用
		 * 4.插入容器信息
		 * 5.插入容器详情信息
		 * 6.插入调入调出表信息
		 * 7.完成配钞后更改设备状态
		 * 8.如果设备已经全部配钞，更新加钞任务单状态为执行中*/
		String taskNo = requestDTO.getTaskNo();
		if (taskNo == null) {
			returnDTO.setRetMsg("未添加任务单编号！");
			returnDTO.setRetCode("FF");
			return "fail";
		}
		//1.校验加钞任务，没有审核相关的状态
		int taskStatus = taskInnerResource.getStatus(taskNo);
		String operateType = requestDTO.getOperateType();
		int taskType = requestDTO.getTaskType();
		if (serviceInnerResource.isStatusConvertMatch(taskType, taskStatus, operateType) < 1) {
			returnDTO.setRetMsg("当前加钞任务未审核通过或已执行,不支持当前操作！");
			returnDTO.setRetCode("FF");
			return "fail";
		}
		return "ok";
	}


	/**
	 * 如果任务单已配钞，则回退配钞记录
	 */
	@ZjComponentMapping("rollBack")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String rollBack(LoadNoteDTO requestDTO, DTO returnDTO, String string) {
		//2.如果任务单已配钞，则回退配钞记录
		String taskNo = requestDTO.getTaskNo();
		int taskStatus = taskInnerResource.getStatus(taskNo);
		String operateType = requestDTO.getOperateType();
		int nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		if (taskStatus == nextStatus) {
			taskEntityDetailInnerResource.deleteByTaskNo(taskNo);
			taskEntityInnerResource.deleteByTaskNo(taskNo);
			//taskDetailInfoInnerResource.deleteByTaskNo(taskNo);
			//回退设备状态为未执行
			/*Map<String, Object> map1 = new HashMap<>();
			map1.put("taskNo", requestDTO.getTaskNo());
			map1.put("customerNo", requestDTO.getCustomerNo());
			map1.put("status", StatusEnum.DevStatus.STATUS_UNEXECUTED.getStatus());
			taskDetailInfoInnerResource.updateByPrimaryKeyMap(map1);*/
			//回退任务单状态为已创建
			TaskTablePO taskTablePO = new TaskTablePO();
			taskTablePO.setTaskNo(taskNo);
			taskTablePO.setStatus(StatusEnum.TaskAddnotesStatus.STATUS_CREATED.getStatus());
			Map<String, Object> map2 = new HashMap<>();
			map2.put("taskNo", taskNo);
			map2.put("status", StatusEnum.TaskAddnotesStatus.STATUS_CREATED.getStatus());
			taskInnerResource.updateByPrimaryKeyMap(map2);
		}
		return "ok";
	}


	/**
	 * 检验钞箱钞袋是否可用
	 */
	@ZjComponentMapping("cassetteCheck")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String cassetteCheck(LoadNoteDTO requestDTO, DTO returnDTO, String string) {
		//3.检验钞箱钞袋是否可用
		String taskNo = requestDTO.getTaskNo();
		String customerNo = requestDTO.getCustomerNo();
		int updateContainerStatusRlt = 0;
		List<ContainerEntityDTO> containerEntityDTOs = requestDTO.getContainerEntityDTOs();
		for (ContainerEntityDTO containerEntityDTO : containerEntityDTOs) {
			String entityNo = containerEntityDTO.getEntityNo();
			Map<String, Object> map = new HashMap<>();
			map.put("taskNo", taskNo);
			map.put("entityNo", entityNo);
			int count = taskEntityInnerResource.selectByTaskNoAndContainerNo(map);
			if (count != 0) {
				Map<String, Object> map1 = new HashMap<>();
				map1.put("taskNo", taskNo);
				map1.put("entityNo", entityNo);
				TaskEntityDTO taskEntityDTO = taskEntityInnerResource.qryByTaskNoAndContainerNo(map1);
				if (taskEntityDTO != null) {
//					String customerNow = taskEntityDTO.getCustomerNo();
//					if (customerNo.equals(customerNow)) {
					//如果任务单和容器和设备都与当前相同
					returnDTO.setRetMsg("容器[" + entityNo + "]已被当前任务单使用");
					returnDTO.setRetCode("FF");
					return "fail";
//					}
				}
			} else {
				//如果任务单不同，容器与当前相同
				int containerCount = taskEntityInnerResource.selectCountByContainerNo(entityNo);
				if (containerCount != 0) {
					returnDTO.setRetMsg("容器[" + entityNo + "]正被其他任务单占用");
					returnDTO.setRetCode("FF");
					return "fail";
				}
			}
		}
		//如果任务单编号和服务编号都相同，则删除该服务的记录
		Map<String, Object> map2 = new HashMap<>();
		map2.put("taskNo", taskNo);
		map2.put("customerNo", customerNo);
		List<TaskEntityDTO> taskEntityDTOList = taskEntityInnerResource.selectByTaskNoAndCustomerNo(map2);
		for (TaskEntityDTO taskEntityDTO : taskEntityDTOList) {
			String entityNo = taskEntityDTO.getEntityNo();
			/*Map<String, Object> map = new HashMap<>();
			map.put("taskNo", taskNo);
			map.put("entityNo", entityNo);
			taskEntityDetailInnerResource.deleteByTaskNoAndContainerNo(map);*/
			//taskDetailInfoInnerResource.deleteByTaskNo(taskNo);
			taskEntityDetailInnerResource.deleteByTaskNo(taskNo);
			taskEntityInnerResource.deleteByTaskNoAndEntityNo(taskNo, entityNo);
		}
		return "ok";
	}

	/**
	 * 状态更新并推送
	 */
	@ZjComponentMapping("loadNote")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String loadNote(LoadNoteDTO requestDTO, DTO returnDTO, String string) {
		//4.插入容器信息
		List<ContainerEntityDTO> containerEntityDTOs = requestDTO.getContainerEntityDTOs();
		String customerNo = requestDTO.getCustomerNo();
		String operateType = requestDTO.getOperateType();
		int nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		String taskNo = requestDTO.getTaskNo();
		for (ContainerEntityDTO containerEntityDTO : containerEntityDTOs) {
			if (containerEntityDTO != null) {
				String entityNo = containerEntityDTO.getEntityNo();
				String productNo = containerEntityDTO.getProductNo();
				String id = java.util.UUID.randomUUID().toString().replace("-", "");
				Map<String, Object> map3 = new HashMap<>();
				map3.put("id", id);
				map3.put("taskNo", requestDTO.getTaskNo());
				map3.put("entityNo", entityNo);
				map3.put("productNo", productNo);
				map3.put("amount", containerEntityDTO.getAmount());
				map3.put("direction", containerEntityDTO.getDirection());
				map3.put("parentEntity", containerEntityDTO.getParentEntity());
				map3.put("leafFlag", containerEntityDTO.getLeafFlag());
				map3.put("depositType", containerEntityDTO.getDepositType());
				map3.putIfAbsent("leafFlag", 1);
				taskEntityInnerResource.insertOrUpdateByPrimaryKey(map3);

				//5.插入容器详情信息
				if (StringUtil.isNullorEmpty(entityNo)) {
					List<ContainerEntityDetailDTO> detailList = containerEntityDTO.getContainerEntityDetailDTOList();
					if (detailList != null ) {
						for (ContainerEntityDetailDTO containerEntityDetailDTO : detailList) {
							String key = containerEntityDetailDTO.getKey();
							String name = containerEntityDetailDTO.getName();
							String value = containerEntityDetailDTO.getValue();
							Map<String, Object> map4 = new HashMap<>();
							map4.put("id", id);
							map4.put("taskNo", taskNo);
							map4.put("key", key);
							map4.put("name", name);
							map4.put("value", value);
							int insertCassetteToBagRlt = taskEntityDetailInnerResource.insertSelectiveByMap(map4);
							if (0 == insertCassetteToBagRlt) {
								returnDTO.setRetMsg("更新配钞记录失败！");
								returnDTO.setRetCode("FF");
								return "fail";
							}
						}
					}
				}
			}
		}


		/*//7.完成配钞后更改设备状态
		Map<String, Object> map5 = new HashMap<>();
		map5.put("taskNo", requestDTO.getTaskNo());
		map5.put("customerNo", requestDTO.getCustomerNo());
		map5.put("status", StatusEnum.DevStatus.STATUS_LOADNOTE.getStatus());
		taskDetailInfoInnerResource.updateByPrimaryKeyMap(map5);*/
		/*//8.如果设备已经全部配钞，更新加钞任务单状态为执行中
		int unLoadDevNum = taskDetailInfoInnerResource.qryDevNumToLoad(taskNo);
		if (0 == unLoadDevNum) {
			Map<String, Object> map3 = new HashMap<>();
			map3.put("taskNo", taskNo);
			map3.put("status", nextStatus);
			taskInnerResource.updateByPrimaryKeyMap(map3);
		}*/
		Map<String, Object> map3 = new HashMap<>();
		map3.put("taskNo", taskNo);
		map3.put("status", nextStatus);
		taskInnerResource.updateByPrimaryKeyMap(map3);
		//推送给所有人信息
		Map<String, Object> pushMap = new HashMap<String, Object>();
		pushMap.put("type", "PeiChao");
		pushMap.put("taskNo", taskNo);
		pushMap.put("CustomerNo", customerNo);
		String jsonStorageInfo = JSONUtil.createJsonString(pushMap);
		sendInfoInnerResource.sendInfo2All(jsonStorageInfo);
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}


	/**
	 * 设备清点参数验证
	 */
	@ZjComponentMapping("devInventoryCheck")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String devInventoryCheck(TaskCheckDTO requestDTO, DTO returnDTO, String string) {
		String customerNo = requestDTO.getCustomerNo();
		String operateType = requestDTO.getOperateType();
		String taskNo = requestDTO.getTaskNo();
		Integer taskType = requestDTO.getTaskType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		List<CurrencyTypeListDTO> currencyTypeList = requestDTO.getCurrencyTypeList();
		int status = taskInnerResource.qryCurStatus(taskNo);
		Integer isStatusConvertMatch = serviceInnerResource.isStatusConvertMatch(taskType, status, operateType);
		if (isStatusConvertMatch == 0) {
			returnDTO.setRetCode(RetCodeEnum.RESULT_EMPTY.getCode());
			returnDTO.setRetMsg("当前任务单状态无法进行清点操作");
			return "fail";
		}
		//根据服务对象编号,任务单状态进行校验
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("customerNo", customerNo);
		paramsMap.put("status", status);
		List<TaskEntityTableInfoDTO> dtoList = taskEntityInnerResource.selectByCustomerNo(paramsMap);
		if (dtoList.size() == 0) {
			returnDTO.setRetCode(RetCodeEnum.RESULT_EMPTY.getCode());
			returnDTO.setRetMsg("没找到对应结果，请核实设备编号");
			return "fail";
		}
		return "ok";
	}

	/**
	 * 设备清点
	 */
	@ZjComponentMapping("devInventory")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String devInventory(TaskCheckDTO requestDTO, DTO returnDTO, String string) {
		String customerNo = requestDTO.getCustomerNo();
		String operateType = requestDTO.getOperateType();
		String taskNo = requestDTO.getTaskNo();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		List<CurrencyTypeListDTO> currencyTypeList = requestDTO.getCurrencyTypeList();
		int status = taskInnerResource.qryCurStatus(taskNo);
		//正常生产环境中，不存在可以查出来多个任务单的情况，目前测试情况下，为了能跑通，如果查出多个，暂时取第一个任务单进行清点
		//造成查出多个任务单的原因是：现在系统没有对箱子  服务对象 做状态判断
		String opNo = ServletRequestUtil.getUsername();
		String opTime = CalendarUtil.getSysTimeYMDHMS();
		//删除清点表中现有的记录
		banknoteTaskCheckMapper.deleteByTaskNoAndCustomerNo(taskNo, customerNo);
		for (CurrencyTypeListDTO currencyTypeListDTO : currencyTypeList) {
			String id = java.util.UUID.randomUUID().toString().replace("-", "");
			BigDecimal amount = currencyTypeListDTO.getAmount();
			Integer denomination = currencyTypeListDTO.getDenomination();
			Integer currencyType = currencyTypeListDTO.getCurrencyType();
			String currencyCode = currencyTypeListDTO.getCurrencyCode();
			//插入物品清点表
			TaskCheckPO newTaskCheckPO = new TaskCheckPO();
			newTaskCheckPO.setId(id);
			newTaskCheckPO.setTaskNo(taskNo);
			newTaskCheckPO.setCustomerNo(customerNo);
			newTaskCheckPO.setAmount(amount);
			newTaskCheckPO.setCurrencyType(currencyType);
			newTaskCheckPO.setCurrencyCode(currencyCode);
			newTaskCheckPO.setOpNo(opNo);
			newTaskCheckPO.setOpTime(opTime);
			newTaskCheckPO.setDenomination(denomination);
			newTaskCheckPO.setClearMachineNo(requestDTO.getClearMachineNo());
			newTaskCheckPO.setCashShortOver(requestDTO.getCashShortOver());
			int insertFlag = banknoteTaskCheckMapper.insert(newTaskCheckPO);
			if (insertFlag <= 0) {
				returnDTO.setResult(RetCodeEnum.FAIL);
				throw new RuntimeException("清点失败");
			}
		}

		/*//任务物品关系表 钞箱状态改为已启用 task_entity_table表
		Map<String, Object> paracMap = new HashMap<String, Object>();
		paracMap.put("taskNo", taskNo);
		paracMap.put("customerNo", customerNo);
		paracMap.put("status", StatusEnum.CassetteInfoStatus.CASSETTE_STATUS_STARTUP.getStatus());
		int e = taskEntityInnerResource.updateByTaskNoAndCustomerNo(paracMap);
		if (e == -1) {
			returnDTO.setResult(RetCodeEnum.FAIL);
			throw new RuntimeException("失败");
		}*/

		/*//修改当前任务单和设备的状态为已清点
		Map<String, Object> map3 = new HashMap<>();
		map3.put("taskNo", taskNo);
		map3.put("customerNo", customerNo);
		map3.put("status", StatusEnum.DevStatus.STATUS_CHECKED.getStatus());
		int updateFlag = taskDetailInfoInnerResource.updateByPrimaryKeyMap(map3);
		if (updateFlag != 1) {
			returnDTO.setResult(RetCodeEnum.FAIL);
			throw new RuntimeException("修改设备状态失败");
		}*/

		/*//当前任务单下所有设备状态为已清点时，修改任务单状态
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("taskNo", taskNo);
		paramMap.put("status", StatusEnum.DevStatus.STATUS_CHECKED.getStatus());
		Map<String, Object> map = new HashMap<>();
		map.put("taskNo", taskNo);
		int customerCount = taskDetailInfoInnerResource.selectCount(map);
		int checkCount = taskDetailInfoInnerResource.selectCount(paramMap);
		if (customerCount == checkCount) {
			Map<String, Object> map1 = new HashMap<>();
			map1.put("taskNo", taskNo);
			map1.put("status", nextStatus);
			int d = taskInnerResource.updateByPrimaryKeyMap(map1);
			if (d == -1) {
				returnDTO.setResult(RetCodeEnum.FAIL);
				throw new RuntimeException("失败");
			}
		}*/
		Map<String, Object> map1 = new HashMap<>();
		map1.put("taskNo", taskNo);
		map1.put("status", nextStatus);
		int d = taskInnerResource.updateByPrimaryKeyMap(map1);
		if (d == -1) {
			returnDTO.setResult(RetCodeEnum.FAIL);
			throw new RuntimeException("失败");
		}

		//推送给所有人信息
		Map<String, Object> pushMap = new HashMap<String, Object>();
		pushMap.put("type", "QingDian");
		pushMap.put("taskNo", taskNo);
		pushMap.put("CustomerNo", customerNo);
		String jsonStorageInfo = JSONUtil.createJsonString(pushMap);
		sendInfoInnerResource.sendInfo2All(jsonStorageInfo);

		returnDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		returnDTO.setRetMsg("清点成功");
		return "ok";
	}


	/**
	 * 现金入库
	 */
	@ZjComponentMapping("cashInStorage")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String cashInStorage(ArrayList<String> taskNos, DTO returnDTO, String string) {
		Integer status = 205;
		int x = taskInnerResource.cashInStorage(taskNos, status);
		if (x > 0) {
			returnDTO.setResult(RetCodeEnum.SUCCEED);
			return "ok";
		}
		returnDTO.setResult(RetCodeEnum.FAIL);
		return "fail";
	}


	/**
	 * 配款领现
	 */
	@ZjComponentMapping("cashDistribution")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String cashDistribution(ArrayList<String> taskNos, DTO returnDTO, String string) {
		Integer status = 209;
		int x = taskInnerResource.cashInStorage(taskNos, status);
		if (x > 0) {
			returnDTO.setResult(RetCodeEnum.SUCCEED);
			return "ok";
		}
		returnDTO.setResult(RetCodeEnum.FAIL);
		return "fail";
	}

}
