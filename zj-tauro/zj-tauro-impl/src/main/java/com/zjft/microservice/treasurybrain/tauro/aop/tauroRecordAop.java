package com.zjft.microservice.treasurybrain.tauro.aop;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
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
 * 物流信息记录
 *
 * @author 韩通
 * @since 2019/11/27
 */
@Slf4j
@Aspect
@Component
public class tauroRecordAop {


	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskNodeInnerResource taskNodeInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskNodeVariateInnerResource taskNodeVariateInnerResource;

	/**
	 * AOP切点位置：经警接库
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.tauro.component.ConvoyOutComponent.recover(..)) ")
	public void tauroRecordPointcut(){}

	/**
	 * AOP切点位置：入库交接
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.tauro.component.InComponent.recover(..))")
	public void inPointcut(){}

	/**
	 * AOP切点位置：钞袋出库、钞袋入库、网点接库、出库交接
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.tauro.service.impl.OutStorageServiceImpl.outStorage(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.tauro.service.impl.InServiceImpl.in(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.tauro.service.impl.OutStorageServiceImpl.outStorage(..))")
	public void tauroLineRecordPointcut(){}

	/**
	 * AOP切点位置：网点接库
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.tauro.component.SignComponent.sign(..)) ")
	public void signRecordPointcut(){}


	@Around("tauroRecordPointcut()")
	public String tauroRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - tauroRecordAroundAll() - @Around()");
		TaskTransferDTO transferDTO = (TaskTransferDTO) joinPoint.getArgs()[0];

		//查询当前任务单状态
		String taskNo = transferDTO.getTaskNo();
		String operateType = transferDTO.getOperateType();
		Integer curStatus = taskInnerResource.getByPrimaryKey(taskNo).getStatus();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		String description = serviceInnerResource.qryNameByOperateType(operateType);
		String curTime = CalendarUtil.getSysTimeYMDHMS();
		String OpNo = ServletRequestUtil.getUsername();
		String taskNodeNo = taskNodeInnerResource.getNextTaskNodeNo(taskNo);

		//设置操作节点信息
		TaskNodeDTO taskNodeDTO = new TaskNodeDTO();
		taskNodeDTO.setTaskNodeNo(taskNodeNo);
		taskNodeDTO.setTaskNo(taskNo);
		taskNodeDTO.setOpNo(OpNo);
		taskNodeDTO.setFinishTime(curTime);
		taskNodeDTO.setBeforeNode(curStatus);
		taskNodeDTO.setCurNode(nextStatus);
		taskNodeDTO.setOperateType(operateType);
		taskNodeDTO.setDescription(description);

		String result = (String) joinPoint.proceed();

		//记录任务节点信息
		if("ok".equals(result)) {
			if (taskNodeInnerResource.insert(taskNodeDTO) <= 0) {
				log.info("====== 记录任务节点信息失败 ======");
			} else {
				log.info("====== 记录任务节点信息成功 ======");
			}
			//记录创建申请单过程中的变量
			int taskType = taskInnerResource.qryTaskType(taskNo);
			taskNodeVariateInnerResource.getJSONToInsert(taskNodeNo,taskType,operateType,transferDTO);
		}
		return result;
	}

	@Around("inPointcut()")
	public String inRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - tauroRecordAroundAll() - @Around()");
		TaskTransferDTO taskTransferDTO = (TaskTransferDTO) joinPoint.getArgs()[0];

		String lineNo = taskTransferDTO.getLineNo();
		String date = CalendarUtil.getSysTimeYMD();
		Integer taskType = taskTransferDTO.getTaskType();
		String operateType = taskTransferDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		String description = serviceInnerResource.qryNameByOperateType(operateType);
		String curTime = CalendarUtil.getSysTimeYMDHMS();
		String OpNo = ServletRequestUtil.getUsername();

		//查找符合本次状态更新的上一个任务单状态列表
		List<String> curStatusList = serviceInnerResource.selectCurStatus(taskType, operateType);
		if (curStatusList.size() == 0) {
			throw new RuntimeException("无可执行此任务状态的任务单，执行失败");
		}

		Map<String, Object> map = new HashMap<>();
		map.put("lineNo", lineNo);
		map.put("planFinishDate", date);
		map.put("status", curStatusList);
		List<String> taskList = taskInnerResource.selectTaskNoByLineNo(map);

		if (taskList.size() == 0) {
			throw new RuntimeException("该线路[" + lineNo + "]下不存在当日可执行任务单，无法进行操作");
		}

		List<TaskNodeDTO> taskNodeDTOList = new ArrayList<TaskNodeDTO>();
		List<Map<String,Object>> taskNodeVariatList = new ArrayList<Map<String,Object>>();
		Map<String,Object> teempMap = null ;
		for (String taskNo : taskList) {
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
//			Integer taskType = taskInnerResource.qryTaskType(taskNo);
			teempMap = new HashMap<String ,Object>();
			teempMap.put("taskNodeNo",taskNodeNo);
			teempMap.put("taskType",taskType);
			teempMap.put("operateType",operateType);
			teempMap.put("object",taskTransferDTO);
			taskNodeVariatList.add(teempMap);
		}

		String result = (String) joinPoint.proceed();

		//记录任务节点信息
		if("ok".equals(result)) {
			if ( taskNodeInnerResource.insertBatch(taskNodeDTOList) <= 0 ) {
				log.info("====== 记录任务节点信息失败 ======");
			} else {
				log.info("====== 记录任务节点信息成功 ======");
			}
			//记录创建申请单过程中的变量
			taskNodeVariateInnerResource.getJSONToInsertBatch(taskNodeVariatList);
		}
		return result;
	}

	@Around("tauroLineRecordPointcut()")
	public DTO tauroLineRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - tauroLineRecordAroundAll() - @Around()");
		TaskTransferDTO taskTransferDTO = (TaskTransferDTO) joinPoint.getArgs()[0];

		String lineNo = taskTransferDTO.getLineNo();
		int taskType = taskTransferDTO.getTaskType();
		String operateType = taskTransferDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		String description = serviceInnerResource.qryNameByOperateType(operateType);
		String curTime = CalendarUtil.getSysTimeYMDHMS();
		String OpNo = ServletRequestUtil.getUsername();

		List<TaskNodeDTO> taskNodeDTOList = new ArrayList<TaskNodeDTO>();
		List<String> statusString = serviceInnerResource.mayStatusConverts(operateType, taskType);

		List<Map<String,Object>> taskNodeVariatList = new ArrayList<Map<String,Object>>();
		Map<String,Object> teempMap = null ;

		for (String mayStatus : statusString) {
			Map<String, Object> lineMap = new HashMap<String, Object>();
			lineMap.put("lineNo", lineNo);
			lineMap.put("status", mayStatus);
			lineMap.put("planFinishDate", CalendarUtil.getSysTimeYMD());
			List<String> list = taskInnerResource.qryTaskNoByLineNo(lineMap);
			for (String taskNo : list) {
				//查询当前任务单状态
				int curStatus = taskInnerResource.getByPrimaryKey(taskNo).getStatus();
				String taskNodeNo = taskNodeInnerResource.getNextTaskNodeNo(taskNo);

				//设置操作节点信息
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
				teempMap = new HashMap<String ,Object>();
				teempMap.put("taskNodeNo",taskNodeNo);
				teempMap.put("taskType",taskType);
				teempMap.put("operateType",operateType);
				teempMap.put("object",taskTransferDTO);
				taskNodeVariatList.add(teempMap);
			}
		}

		DTO dto = (DTO) joinPoint.proceed();

		//记录任务节点信息
		if(RetCodeEnum.SUCCEED.getCode() == dto.getRetCode()) {
			if (taskNodeInnerResource.insertBatch(taskNodeDTOList) <= 0) {
				log.info("====== 记录任务节点信息失败 ======");
			} else {
				log.info("====== 记录任务节点信息成功 ======");
			}
			//记录创建申请单过程中的变量
//			taskNodeVariateInnerResource.getJSONToInsertBatch(taskNodeVariatList);
		}
		return dto;
	}

	@Around("signRecordPointcut()")
	public String signRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - tauroLineRecordAroundAll() - @Around()");
		TaskTransferDTO taskTransferDTO = (TaskTransferDTO) joinPoint.getArgs()[0];

		String lineNo = taskTransferDTO.getLineNo();
		int taskType = taskTransferDTO.getTaskType();
		String operateType = taskTransferDTO.getOperateType();
		if (StringUtil.isNullorEmpty(operateType)){
			String result = (String) joinPoint.proceed();
			return result;
		}
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		String description = serviceInnerResource.qryNameByOperateType(operateType);
		String curTime = CalendarUtil.getSysTimeYMDHMS();
		String OpNo = ServletRequestUtil.getUsername();

		List<TaskNodeDTO> taskNodeDTOList = new ArrayList<TaskNodeDTO>();
		List<String> statusString = serviceInnerResource.mayStatusConverts(operateType, taskType);

		List<Map<String,Object>> taskNodeVariatList = new ArrayList<Map<String,Object>>();
		Map<String,Object> teempMap = null ;

		for (String mayStatus : statusString) {
			Map<String, Object> lineMap = new HashMap<String, Object>();
			lineMap.put("lineNo", lineNo);
			lineMap.put("status", mayStatus);
			lineMap.put("planFinishDate", CalendarUtil.getSysTimeYMD());
			List<String> list = taskInnerResource.qryTaskNoByLineNo(lineMap);
			for (String taskNo : list) {
				//查询当前任务单状态
				int curStatus = taskInnerResource.getByPrimaryKey(taskNo).getStatus();
				String taskNodeNo = taskNodeInnerResource.getNextTaskNodeNo(taskNo);

				//设置操作节点信息
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
				teempMap = new HashMap<String ,Object>();
				teempMap.put("taskNodeNo",taskNodeNo);
				teempMap.put("taskType",taskType);
				teempMap.put("operateType",operateType);
				teempMap.put("object",taskTransferDTO);
				taskNodeVariatList.add(teempMap);
			}
		}

		String result = (String) joinPoint.proceed();

		//记录任务节点信息
		if("ok".equals(result)) {
			if (taskNodeInnerResource.insertBatch(taskNodeDTOList) <= 0) {
				log.info("====== 记录任务节点信息失败 ======");
			} else {
				log.info("====== 记录任务节点信息成功 ======");
			}
			//记录创建申请单过程中的变量
//			taskNodeVariateInnerResource.getJSONToInsertBatch(taskNodeVariatList);
		}
		return result;
	}

}
