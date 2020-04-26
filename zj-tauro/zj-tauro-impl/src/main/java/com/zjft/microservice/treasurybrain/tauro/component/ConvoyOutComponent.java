package com.zjft.microservice.treasurybrain.tauro.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityDetailInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskPerRecorderInnerResource;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ZjComponentResource(group = "convoyOut")
public class ConvoyOutComponent {

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskPerRecorderInnerResource taskPerRecorderInnerResource;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	/**
	 * 经警接库
	 */
	@ZjComponentMapping("convoyOut(经警接库)")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String recover(TaskTransferDTO taskTransferDTO, ObjectDTO returnDTO, List<String> taskNos) {
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
		for (String entityNo : list) {
			if (!entityNoList.contains(entityNo)) {
				throw new RuntimeException("任务单[" + taskNo + "]中存在未匹配的容器，无法进行操作");
			}
		}

		for (String entityNo : entityNoList) {
			String customerNo = taskEntityInnerResource.qryNewCustomerNoBycontainerNo(entityNo, taskNo);
			if (!customerList.contains(entityNo)) {
				customerList.add(customerNo);
			}

			TaskEntityDTO taskEntityDTO = taskEntityInnerResource.selectOneByTaskNo(taskNo,entityNo);
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
			taskPerRecorderMap.put("containerNo",entityNo);
			taskPerRecorderMap.put("performTime",CalendarUtil.getSysTimeYMDHMS());
			taskPerRecorderMap.put("performType", StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
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
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}

	/**
	 * 经警接库失败
	 */
	@ZjComponentMapping("convoyOutFail(经警接库失败)")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String convoyOutFail(TaskTransferDTO taskTransferDTO, ObjectDTO returnDTO, List<String> taskNos) {
		returnDTO.setRetCode("FF");
		return "fail";
	}
}
