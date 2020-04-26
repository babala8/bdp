package com.zjft.microservice.treasurybrain.task.aop;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskNodeDTO;
import com.zjft.microservice.treasurybrain.task.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskNodeInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskNodeVariateInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
 * 任务操作信息记录
 *
 * @author 韩通
 * @since 2020-01-14
 */
@Slf4j
@Aspect
@Component
public class TaskRecordAop {

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskNodeInnerResource taskNodeInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskNodeVariateInnerResource taskNodeVariateInnerResource;

	/**
	 * AOP切点位置：任务单修改状态
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.task.service.impl.TaskServiceImpl.updateTaskInfo(..)) ")
	public void TaskRecordPointcut(){}

	/**
	 * AOP切点位置：网点调拨审核
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.task.service.impl.TaskDetailServiceImpl.modDevClrTime(..)) ")
	public void ModDevClrTimeRecordPointcut(){}

	/**
	 * AOP切点位置：任务单修改状态批量
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.task.service.impl.TaskServiceImpl.affirmOutBatchNotesReceipt(..))")
	public void modBatchcut() {
	}

	/**
	 * AOP切点位置：任务单申请
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.task.service.impl.BusinessServiceImpl.applyForTransfer(..)) ")
	public void applyForcut() {
	}

	/**
	 * AOP切点位置：任务单修改
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.task.service.impl.BusinessServiceImpl.modForTransfer(..)) ")
	public void modcut() {
	}


	@Around("TaskRecordPointcut()")
	public DTO TaskRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - TaskRecordAroundAll() - @Around()");
		TaskInfoDTO taskInfoDTO = (TaskInfoDTO) joinPoint.getArgs()[0];

		// 任务节点信息
		String taskNo = taskInfoDTO.getTaskNo();
		//查询当前任务单状态
		Integer curStatus = taskInnerResource.qryCurStatus(taskNo);
		String operateType = taskInfoDTO.getOperateType();
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
			int taskType = taskInnerResource.qryTaskType(taskNo);
			taskNodeVariateInnerResource.getJSONToInsert(taskNodeNo,taskType,operateType,taskInfoDTO);

		}

		return dto;
	}

	@Around("ModDevClrTimeRecordPointcut()")
	public DTO ModDevClrTimeRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - ModDevClrTimeRecordAroundAll() - @Around()");
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
			int taskType = taskInnerResource.qryTaskType(taskNo);
			taskNodeVariateInnerResource.getJSONToInsert(taskNodeNo,taskType,operateType,transferTaskInfoDTO);

		}

		return dto;
	}

	@Around("modBatchcut()")
	public DTO modBatcAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - modBatcAroundAll() - @Around()");
		TaskInfoDTO taskInfoDTO = (TaskInfoDTO) joinPoint.getArgs()[0];

		String operateType = taskInfoDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		String description = serviceInnerResource.qryNameByOperateType(operateType);
		String curTime = CalendarUtil.getSysTimeYMDHMS();
		String OpNo = ServletRequestUtil.getUsername();

		List<String> list = taskInfoDTO.getList();
		List<TaskNodeDTO> taskNodeDTOList = new ArrayList<TaskNodeDTO>();
		List<Map<String,Object>> taskNodeVariatList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null ;
		for (String taskNo: list) {
			Integer curStatus = taskInnerResource.qryCurStatus(taskNo);
			String taskNodeNo = taskNodeInnerResource.getNextTaskNodeNo(taskNo);

			// 任务节点信息
			TaskNodeDTO taskNodeDTO = new TaskNodeDTO();
			taskNodeDTO.setTaskNodeNo(taskNodeNo);
			taskNodeDTO.setTaskNo(taskNo);
			taskNodeDTO.setOpNo(OpNo);
			taskNodeDTO.setFinishTime(curTime);
			taskNodeDTO.setBeforeNode(curStatus);
			taskNodeDTO.setCurNode(nextStatus);
			taskNodeDTO.setOperateType(operateType);
			taskNodeDTO.setDescription(description);
			taskNodeDTOList.add(taskNodeDTO);

			//记录节点变量信息
			Integer taskType = taskInnerResource.qryTaskType(taskNo);
			map = new HashMap<String ,Object>();
			map.put("taskNodeNo",taskNodeNo);
			map.put("taskType",taskType);
			map.put("operateType",operateType);
			map.put("object",taskInfoDTO);
			taskNodeVariatList.add(map);
		}

		DTO dto = (DTO) joinPoint.proceed();

		if(RetCodeEnum.SUCCEED.getCode() == dto.getRetCode()){
			if ( taskNodeInnerResource.insertBatch(taskNodeDTOList) <= 0 ){
				log.info("====== 记录任务节点信息失败 ======");
			}else {
				log.info("====== 记录任务节点信息成功 ======");
			}

			taskNodeVariateInnerResource.getJSONToInsertBatch(taskNodeVariatList);
		}
		return dto;
	}

	@AfterReturning(returning = "retMap", pointcut="applyForcut()")
	public void afterApply(JoinPoint joinPoint, Map<String, Object> retMap) {
		log.debug("@AfterReturning() - afterApply() - @AfterReturning()");
		TransferTaskInfoDTO transferTaskInfoDTO = (TransferTaskInfoDTO) joinPoint.getArgs()[0];

		// 任务节点信息
		String retCode = StringUtil.parseString(retMap.get("retCode"));
		if(RetCodeEnum.SUCCEED.getCode() == retCode){
			String taskNo = StringUtil.parseString(retMap.get("taskNo"));
			String taskNodeNo = taskNodeInnerResource.getNextTaskNodeNo(taskNo);

			TaskNodeDTO taskNodeDTO = new TaskNodeDTO();
			taskNodeDTO.setTaskNodeNo(taskNodeNo);
			taskNodeDTO.setTaskNo(taskNo);
			taskNodeDTO.setOpNo(ServletRequestUtil.getUsername());
			taskNodeDTO.setFinishTime(CalendarUtil.getSysTimeYMDHMS());
			taskNodeDTO.setBeforeNode(0);
			taskNodeDTO.setCurNode(201);
			taskNodeDTO.setOperateType("Create");
			taskNodeDTO.setDescription("创建");

			if ( taskNodeInnerResource.insert(taskNodeDTO) <= 0 ){
				log.info("====== 记录任务节点信息失败 ======");
			}else {
				log.info("====== 记录任务节点信息成功 ======");
			}

			//记录创建申请单过程中的变量
			int serviceNo = transferTaskInfoDTO.getTaskType();
			taskNodeVariateInnerResource.getJSONToInsert(taskNodeNo,serviceNo,"Create",transferTaskInfoDTO);
		}
	}

	@Around("modcut()")
	public DTO aroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - aroundAll() - @Around()");
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
