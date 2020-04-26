package com.zjft.microservice.treasurybrain.storage.aop;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityTransferDTO;
import com.zjft.microservice.treasurybrain.storage.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskNodeDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskNodeInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskNodeVariateInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库房出入库信息记录
 *
 * @author 韩通
 * @since 2019/9/9
 */
@Slf4j
@Aspect
@Component
public class StorageRecordAop {

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskNodeInnerResource taskNodeInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskNodeVariateInnerResource taskNodeVariateInnerResource;

	/**
	 * AOP切点位置：实物出入库
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.storage.component.GoodOutComponent.goodOut(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.storage.component.GoodInComponent.goodIn(..))")
	public void StorageRecordPointcut(){}

	/**
	 * AOP切点位置：金库确认
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.storage.service.impl.GoodManageServiceImpl.affirmOutNotesReceipt(..)) ")
	public void affirmRecordPointcut(){}


	@Around("StorageRecordPointcut()")
	public String StorageRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - StorageRecordAroundAll() - @Around()");
		StorageEntityTransferDTO requestDTO = (StorageEntityTransferDTO) joinPoint.getArgs()[0];
		DTO returnDTO = (DTO) joinPoint.getArgs()[1];
		List<String> tempObj = (List<String>) joinPoint.getArgs()[2];

		List<TaskNodeDTO> list = new ArrayList<TaskNodeDTO>();

		List<Map<String,Object>> taskNodeVariateParams = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

		for(String taskNo : tempObj){

			//查询当前任务单状态
			Integer curStatus = taskInnerResource.qryCurStatus(taskNo);
			String operateType = requestDTO.getOperateType();
			Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
			String description = serviceInnerResource.qryNameByOperateType(operateType);
			String curTime = CalendarUtil.getSysTimeYMDHMS();
			String OpNo = ServletRequestUtil.getUsername();
			String taskNodeNo = taskNodeInnerResource.getNextTaskNodeNo(taskNo);

			TaskNodeDTO taskNodeDTO = new TaskNodeDTO();
			taskNodeDTO.setTaskNodeNo(taskNodeNo);
			taskNodeDTO.setTaskNo(taskNo);
			taskNodeDTO.setOpNo(OpNo);
			taskNodeDTO.setFinishTime(curTime);
			taskNodeDTO.setBeforeNode(curStatus);
			taskNodeDTO.setCurNode(nextStatus);
			taskNodeDTO.setOperateType(operateType);
			taskNodeDTO.setDescription(description);

			list.add(taskNodeDTO);

			//任务节点变量信息保存
			Integer taskType = taskInnerResource.qryTaskType(taskNo);
			map = new HashMap<String, Object>();
			map.put("taskNodeNo",taskNodeNo);
			map.put("taskType",taskType);
			map.put("operateType",operateType);
			map.put("object",requestDTO);
			taskNodeVariateParams.add(map);

		}

		String  str = (String) joinPoint.proceed();

		if("ok".equals(str)){
			//记录创建申请单过程中的节点
			if ( taskNodeInnerResource.insertBatch(list) <= 0 ){
				log.info("====== 记录任务节点信息失败 ======");
			}else {
				log.info("====== 记录任务节点信息成功 ======");
			}
			for(String taskNo : tempObj){
				Integer taskType = taskInnerResource.qryTaskType(taskNo);
			}
			//记录创建申请单过程中的变量
			taskNodeVariateInnerResource.getJSONToInsertBatch(taskNodeVariateParams);
		}

		return str;
	}

	@Around("affirmRecordPointcut()")
	public DTO affirmAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - affirmAroundAll() - @Around()");
		TransferTaskInfoDTO transferTaskInfoDTO = (TransferTaskInfoDTO) joinPoint.getArgs()[0];

		// 任务节点信息
		String taskNo = transferTaskInfoDTO.getTaskNo();
		//查询当前任务单状态
		Integer curStatus = taskInnerResource.qryCurStatus(taskNo);
		String operateType = transferTaskInfoDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		String description = serviceInnerResource.qryNameByOperateType(operateType);
		String curTime = CalendarUtil.getSysTimeYMDHMS();
		String OpNo = ServletRequestUtil.getUsername();
		String taskNodeNo = taskNodeInnerResource.getNextTaskNodeNo(taskNo);


		TaskNodeDTO taskNodeDTO = new TaskNodeDTO();
		taskNodeDTO.setTaskNodeNo(taskNodeNo);
		taskNodeDTO.setTaskNo(taskNo);
		taskNodeDTO.setOpNo(OpNo);
		taskNodeDTO.setFinishTime(curTime);
		taskNodeDTO.setBeforeNode(curStatus);
		taskNodeDTO.setCurNode(nextStatus);
		taskNodeDTO.setOperateType(operateType);
		taskNodeDTO.setDescription(description);

		DTO dto = (DTO) joinPoint.proceed();

		if(RetCodeEnum.SUCCEED.getCode() == dto.getRetCode()){
			if ( taskNodeInnerResource.insert(taskNodeDTO) <= 0 ){
				log.error("====== 记录任务节点信息失败 ======");
			}else {
				log.info("====== 记录任务节点信息成功 ======");
			}

			//记录创建申请单过程中的变量
			int serviceNo = transferTaskInfoDTO.getTaskType();
			taskNodeVariateInnerResource.getJSONToInsert(taskNodeNo,serviceNo,operateType,transferTaskInfoDTO);

		}

		return dto;
	}

}
