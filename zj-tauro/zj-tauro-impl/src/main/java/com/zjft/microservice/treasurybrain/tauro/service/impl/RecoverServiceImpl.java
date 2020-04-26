package com.zjft.microservice.treasurybrain.tauro.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityTableDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskTableDTO;
import com.zjft.microservice.treasurybrain.task.web.TaskResource;
import com.zjft.microservice.treasurybrain.task.web_inner.*;
import com.zjft.microservice.treasurybrain.tauro.po.*;
import com.zjft.microservice.treasurybrain.tauro.repository.*;
import com.zjft.microservice.treasurybrain.tauro.service.RecoverService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/08/14
 */
@Slf4j
@Service
public class RecoverServiceImpl implements RecoverService {

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskPerRecorderInnerResource taskPerRecorderInnerResource;

	@Resource
	private TaskDetailInfoInnerResource taskDetailInfoInnerResource;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private TaskResource taskResource;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO recover(TaskTransferDTO taskTransferDTO) {
		//传过来的容器编号包含所有回收的容器（钞袋 钞箱）
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		List<String> entityNoList = taskTransferDTO.getEntityNos();
		List<String> cassetteBagList = new ArrayList<>();
		List<String> cassetteList = new ArrayList<>();
		String taskNo = "";
		String customerNo = "";
		for (String entityNo : entityNoList) {
//			TauroTaskEntityTablePO tauroTaskEntityTablePO = tauroTaskEntityTableMapper.qryByContainerNo(containerNo);

			TaskEntityDTO taskEntityDTO = taskEntityInnerResource.qryByContainerNo(entityNo);

			if (taskEntityDTO == null) {
				log.error("该容器[" + entityNo + "]编号输入错误");
				throw new RuntimeException("该容器[" + entityNo + "]编号输入错误");
			}
			if (StringUtil.isNullorEmpty(taskEntityDTO.getParentEntity())) {
				cassetteBagList.add(entityNo);
				//判断当前任务是否出库以及是否是当日任务单
				taskNo = taskEntityDTO.getTaskNo();
				TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
				//找出加钞钞袋对应的服务对象编号
				customerNo = taskEntityInnerResource.qryNewCustomerNoBycontainerNo(entityNo, taskNo);
				int taskType = taskTableDTO.getTaskType();
				int taskStatus = taskTableDTO.getStatus();
				String planFinishDate = taskTableDTO.getPlanFinishDate();
				if (taskType == StatusEnum.TaskTypeTauro.TYPE_ADDNOTES.getType()) {
					//判断当前状态是否为308-出库交接
					if (taskStatus != 308 || !planFinishDate.equals(CalendarUtil.getSysTimeYMD())) {
						throw new RuntimeException("当前任务单[" + taskNo + "]未出库交接或不是今日任务单");
					}
				}
			} else {
				cassetteList.add(entityNo);
				//判断该设备下有没有尾箱没有回收，有则回收失败
				ListDTO<String> oldContainerNoList= taskResource.qryPreviousContainByCustomerNo(customerNo);
				List<String> oldContainerNos = oldContainerNoList.getRetList();
				for (String oldContainerNo : oldContainerNos) {
					if (!entityNoList.contains(oldContainerNo)) {
						throw new RuntimeException("该设备号[" + customerNo + "]下存在尾箱没有回收，无法进行回收操作");
					}
				}
			}
		}
		if (StringUtil.isNullorEmpty(taskNo)) {
			log.error("未获取到钞袋编号");
			throw new RuntimeException("未获取到钞袋编号");
		}
		//第一次回收钞箱为空，不进行回收操作
		if (cassetteList.size() == 0) {
			log.error("没有回收的钞箱则该设备不进行回收操作");
			throw new RuntimeException("没有回收的钞箱则该设备不进行回收操作");
		}

		for (String containerNo : entityNoList) {
//			TauroTaskEntityTablePO tauroTaskEntityTablePO = tauroTaskEntityTableMapper.qryByContainerNo(containerNo);
			TaskEntityDTO taskEntityDTO = taskEntityInnerResource.qryByContainerNo(containerNo);
			if (StringUtil.isNullorEmpty(taskEntityDTO.getParentEntity())) {
				//最上级容器（钞袋）
				//新增任务执行记录表
//				TauroTaskPerRecorderPO tauroTaskPerRecorderPO = new TauroTaskPerRecorderPO();
//				tauroTaskPerRecorderPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
//				tauroTaskPerRecorderPO.setTaskNo(taskNo);
//				tauroTaskPerRecorderPO.setContainerNo(containerNo);
//				tauroTaskPerRecorderPO.setPerformTime(CalendarUtil.getSysTimeYMDHMS());
//				tauroTaskPerRecorderPO.setPerformType(StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
//				tauroTaskPerRecorderPO.setOpNo1(sysUserResource.getAuthUserInfo().getUsername());
//				tauroTaskPerRecorderPO.setContainerType(StatusEnum.ContainerType.TYPE_CASSETTE_BAG.getType());

				Map<String,Object> taskPerRecorderMap = new HashMap<>();
				taskPerRecorderMap.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
				taskPerRecorderMap.put("taskNo",taskNo);
				taskPerRecorderMap.put("containerNo",containerNo);
				taskPerRecorderMap.put("performTime",CalendarUtil.getSysTimeYMDHMS());
				taskPerRecorderMap.put("performType",StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
				taskPerRecorderMap.put("opNo1",sysUserResource.getAuthUserInfo().getUsername());
				taskPerRecorderMap.put("containerType",StatusEnum.ContainerType.TYPE_CASSETTE_BAG.getType());

				int x = taskPerRecorderInnerResource.insertByMap(taskPerRecorderMap);

				if (x != 1) {
					log.error("生成钞袋尾箱回收任务执行记录表失败");
					throw new RuntimeException("生成钞袋尾箱回收任务执行记录表失败");
				}
			} else {
				//钞箱
				//通过回收的钞箱编号查出上次加钞任务所对应的服务对象编号
//				String oldCustomerNo = tauroTaskEntityTableMapper.qryOldCustomerNoBycontainerNo(containerNo);
//				if (!customerNo.equals(oldCustomerNo)) {
//					log.error("不同设备的尾箱不允许放到同一个钞袋中");
//					throw new RuntimeException("不同设备的尾箱不允许放到同一个钞袋中");
//				}
				//新增任务执行记录表
//				TauroTaskPerRecorderPO tauroTaskPerRecorderPO = new TauroTaskPerRecorderPO();
//				tauroTaskPerRecorderPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
//				tauroTaskPerRecorderPO.setTaskNo(taskNo);
//				tauroTaskPerRecorderPO.setContainerNo(containerNo);
//				tauroTaskPerRecorderPO.setPerformTime(CalendarUtil.getSysTimeYMDHMS());
//				tauroTaskPerRecorderPO.setPerformType(StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
//				tauroTaskPerRecorderPO.setOpNo1(sysUserResource.getAuthUserInfo().getUsername());
//				tauroTaskPerRecorderPO.setContainerType(StatusEnum.ContainerType.TYPE_CASSETTE.getType());
//				int y = tauroTaskPerRecorderMapper.insert(tauroTaskPerRecorderPO);

				Map<String,Object> taskPerRecorderMap = new HashMap<>();
				taskPerRecorderMap.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
				taskPerRecorderMap.put("taskNo",taskNo);
				taskPerRecorderMap.put("containerNo",containerNo);
				taskPerRecorderMap.put("performTime",CalendarUtil.getSysTimeYMDHMS());
				taskPerRecorderMap.put("performType",StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
				taskPerRecorderMap.put("opNo1",sysUserResource.getAuthUserInfo().getUsername());
				taskPerRecorderMap.put("containerType",StatusEnum.ContainerType.TYPE_CASSETTE_BAG.getType());

				int y = taskPerRecorderInnerResource.insertByMap(taskPerRecorderMap);

				if (y != 1) {
					log.error("生成钞箱尾箱[" + containerNo + "]回收任务执行记录表失败");
					throw new RuntimeException("生成钞箱尾箱[" + containerNo + "]回收任务执行记录表失败");
				}
			}
		}

		//将钞箱放入对应的钞袋并生成记录
		for (String cassetteBagNo : cassetteBagList) {
			for (String cassetteNo : cassetteList) {
//				TauroTaskEntityTablePO tauroTaskEntityTablePO = new TauroTaskEntityTablePO();
//				tauroTaskEntityTablePO.setTaskNo(taskNo);
//				tauroTaskEntityTablePO.setContainerNo(cassetteNo);
//				tauroTaskEntityTablePO.setCustomerNo(customerNo);
//				tauroTaskEntityTablePO.setEntityType(StatusEnum.TaskEntityTableEntityTypeTauro.ENTITY_TYPE_CASH.getType());
//				tauroTaskEntityTablePO.setDirection(StatusEnum.TaskEntityTableDirectionTauro.DIRECTION_OUT.getDirection());
//				tauroTaskEntityTablePO.setUpperNo(cassetteBagNo);
//				tauroTaskEntityTablePO.setLeafFlag(StatusEnum.ContainerLeafFlag.FLAG_TRUE.getFlag());

				Map<String,Object> taskEntityMap = new HashMap<>();
				taskEntityMap.put("taskNo",taskNo);
				taskEntityMap.put("containerNo",cassetteNo);
				taskEntityMap.put("customerNo",customerNo);
				taskEntityMap.put("entityType",StatusEnum.TaskEntityTableEntityTypeTauro.ENTITY_TYPE_CASH.getType());
				taskEntityMap.put("direction",StatusEnum.TaskEntityTableDirectionTauro.DIRECTION_OUT.getDirection());
				taskEntityMap.put("upperNo",cassetteBagNo);
				taskEntityMap.put("leafFlag",StatusEnum.ContainerLeafFlag.FLAG_TRUE.getFlag());

				try {
//					int x = tauroTaskEntityTableMapper.insert(tauroTaskEntityTablePO);
					int x = taskEntityInnerResource.insertSelectiveByMap(taskEntityMap);
					if (x != 1) {
						log.error("生成任务物品关系表失败");
						throw new RuntimeException("生成任务物品关系表失败");
					}
				} catch (Exception e) {
					log.error("此钞箱[" + cassetteNo + "]不需要回收，不允许进行回收操作");
					throw new RuntimeException("此钞箱[" + cassetteNo + "]不需要回收，不允许进行回收操作");
				}
			}
		}

		//回收完成更新
//		TauroTaskDetailTablePO tauroTaskDetailTablePO=new TauroTaskDetailTablePO();
//		tauroTaskDetailTablePO.setTaskNo(taskNo);
//		tauroTaskDetailTablePO.setCustomerNo(customerNo);
//		tauroTaskDetailTablePO.setStatus(StatusEnum.DevStatus.STATUS_RECOVERY.getStatus());

//		Map<String,Object> taskDetailMap = new HashMap<>();
//		taskDetailMap.put("taskNo",taskNo);
//		taskDetailMap.put("customerNo",customerNo);
//		taskDetailMap.put("status",StatusEnum.DevStatus.STATUS_RECOVERY.getStatus());
//
////		int updateFlag = tauroTaskDetailTableMapper.updateStatusByNo(tauroTaskDetailTablePO);
//		int updateFlag = taskDetailInfoInnerResource.updateByPrimaryKeyMap(taskDetailMap);
//		if (updateFlag != 1) {
//			log.error("服务对象状态更新失败");
//			throw new RuntimeException("服务对象状态更新失败");
//		}
		return dto;
	}
}
