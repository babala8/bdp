package com.zjft.microservice.treasurybrain.tauro.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.*;
import com.zjft.microservice.treasurybrain.tauro.service.ConvoyOutService;
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
 * @since 2019/08/26
 */
@Slf4j
@Service
public class ConvoyOutServiceImpl implements ConvoyOutService {

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskEntityDetailInnerResource taskEntityDetailInnerResource;

	@Resource
	private TaskPerRecorderInnerResource taskPerRecorderInnerResource;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO convoyOut(TaskTransferDTO taskTransferDTO) {
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		String taskNo = taskTransferDTO.getTaskNo();
		List<String> entityNoList = taskTransferDTO.getEntityNos();
		Integer taskType = taskTransferDTO.getTaskType();
		//Integer nextStatus = taskTransferDTO.getStatus();
		String operateType = taskTransferDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		List<String> customerList = new ArrayList<>();

		TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
		if (StringUtil.isNullorEmpty(taskNo) || StringUtil.isNullorEmpty(StringUtil.parseString(taskType)) ||
				StringUtil.isNullorEmpty(StringUtil.parseString(nextStatus)) || entityNoList.size() == 0) {
			throw new RuntimeException("缺少必要参数");
		}

//		String planFinishDate = tauroTaskTableMapper.qryDateByNo(taskNo);
		String planFinishDate = taskTableDTO.getPlanFinishDate();
		if (StringUtil.isNullorEmpty(planFinishDate)) {
			throw new RuntimeException("任务单[" + taskNo + "]不存在或未填写日期信息");
		}
		if (!planFinishDate.equals(CalendarUtil.getSysTimeYMD())) {
			throw new RuntimeException("[" + taskNo + "]不是当日任务单，无法进行操作");
		}

//		Integer curStatus = tauroTaskTableMapper.qryStatusByNo(taskNo);
		Integer curStatus = taskTableDTO.getStatus();
//		TauroProductStatusConvertPO tauroProductStatusConvertPO = new TauroProductStatusConvertPO();
//		tauroProductStatusConvertPO.setServiceNo(taskType);
//		//tauroProductStatusConvertPO.setNextStatus(nextStatus);
//		tauroProductStatusConvertPO.setOperateType(operateType);
//		tauroProductStatusConvertPO.setCurStatus(curStatus);

		Map<String,Object> productMap = new HashMap<>();
		productMap.put("serviceNo",taskType);
		productMap.put("operateType",operateType);
		productMap.put("curStatus",curStatus);
		int x = serviceInnerResource.qryExist(productMap);
		if (x == 0) {
			throw new RuntimeException("当前任务[" + taskNo + "]的状态无法进行此操作");
		}

		List<String> list = taskEntityInnerResource.selectContainerNoByTaskNo(taskNo);
		for (String containerNo : list) {
			if (!entityNoList.contains(containerNo)) {
				throw new RuntimeException("任务单[" + taskNo + "]中存在未匹配的容器，无法进行操作");
			}
		}

		for (String containerNo : entityNoList) {
			String customerNo = taskEntityInnerResource.qryNewCustomerNoBycontainerNo(containerNo, taskNo);
			if (!customerList.contains(containerNo)) {
				customerList.add(customerNo);
			}

			TaskEntityDTO taskEntityDTO = taskEntityInnerResource.selectOneByTaskNo(taskNo,containerNo);
//			TauroTaskPerRecorderPO tauroTaskPerRecorderPO = new TauroTaskPerRecorderPO();
//			tauroTaskPerRecorderPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
//			tauroTaskPerRecorderPO.setTaskNo(taskNo);
//			tauroTaskPerRecorderPO.setContainerNo(containerNo);
//			tauroTaskPerRecorderPO.setPerformTime(CalendarUtil.getSysTimeYMDHMS());
//			tauroTaskPerRecorderPO.setPerformType(StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
//			tauroTaskPerRecorderPO.setOpNo1(sysUserResource.getAuthUserInfo().getUsername());
//			tauroTaskPerRecorderPO.setContainerType(taskEntityDetailTableDTO.getContainerType());

			Map<String,Object> taskPerRecorderMap = new HashMap<>();
			taskPerRecorderMap.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
			taskPerRecorderMap.put("taskNo",taskNo);
			taskPerRecorderMap.put("containerNo",containerNo);
			taskPerRecorderMap.put("performTime",CalendarUtil.getSysTimeYMDHMS());
			taskPerRecorderMap.put("performType",StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
			taskPerRecorderMap.put("opNo1",sysUserResource.getAuthUserInfo().getUsername());
			taskPerRecorderMap.put("containerType", taskEntityDTO.getProductNo());
			int y = taskPerRecorderInnerResource.insertByMap(taskPerRecorderMap);
			if (y != 1) {
				log.error("生成容器任务执行记录表失败");
				throw new RuntimeException("生成容器任务执行记录表失败");
			}
		}

//		TauroTaskTablePO tauroTaskTablePO = new TauroTaskTablePO();
//		tauroTaskTablePO.setTaskNo(taskNo);
//		tauroTaskTablePO.setStatus(nextStatus);

		Map<String,Object> TaskTableMap = new HashMap<>();
		TaskTableMap.put("taskNo",taskNo);
		TaskTableMap.put("status",nextStatus);
		int m = taskInnerResource.updateByPrimaryKeyMap(TaskTableMap);
		if (m != 1) {
			log.error("更新表 [TASK_TABLE] 状态失败");
			throw new RuntimeException("更新订单任务编号[" + taskNo + "]状态失败");
		}
		return dto;
	}
}
