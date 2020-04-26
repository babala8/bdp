package com.zjft.microservice.treasurybrain.clearcenter.aop;

import com.zjft.microservice.treasurybrain.clearcenter.dto.LoadNoteDTO;
import com.zjft.microservice.treasurybrain.clearcenter.dto.TaskCheckDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
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

/**
 * 配款清点信息记录
 *
 * @author 韩通
 * @since 2019/11/27
 */
@Slf4j
@Aspect
@Component
public class SortRecordAop {

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskNodeInnerResource taskNodeInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskNodeVariateInnerResource taskNodeVariateInnerResource;

	/**
	 * AOP切点位置：配款清点
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.clearcenter.component.CashBusinessComponent.devInventory(..)) ")
	public void inventoryRecordPointcut(){}

	/**
	 * AOP切点位置：配钞
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.clearcenter.component.CashBusinessComponent.loadNote(..)) ")
	public void loadNoteRecordPointcut(){}

	@Around("inventoryRecordPointcut()")
	public String inventoryRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - inventoryRecordAroundAll() - @Around()");
		TaskCheckDTO taskCheckDTO = (TaskCheckDTO) joinPoint.getArgs()[0];

		//查询当前任务单状态
		String taskNo = taskCheckDTO.getTaskNo();
		String operateType = taskCheckDTO.getOperateType();
		Integer curStatus = taskInnerResource.qryCurStatus(taskNo);
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

		String str = (String) joinPoint.proceed();

		//记录任务节点信息
		if("ok".equals(str)) {
			if (taskNodeInnerResource.insert(taskNodeDTO) <= 0) {
				log.info("====== 记录任务节点信息失败 ======");
			} else {
				log.info("====== 记录任务节点信息成功 ======");
			}
			//记录创建申请单过程中的变量
			Integer taskType = taskInnerResource.qryTaskType(taskNo);
			taskNodeVariateInnerResource.getJSONToInsert(taskNodeNo,taskType,operateType,taskCheckDTO);

		}
		return str;
	}

	@Around("loadNoteRecordPointcut()")
	public String loadNoteRecordAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("@Around() - loadNoteRecordAroundAll() - @Around()");
		LoadNoteDTO loadNoteDTO = (LoadNoteDTO) joinPoint.getArgs()[0];

		//查询当前任务单状态
		String taskNo = loadNoteDTO.getTaskNo();
		String operateType = loadNoteDTO.getOperateType();
		Integer curStatus = taskInnerResource.qryCurStatus(taskNo);
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

		String string =  (String) joinPoint.proceed();

		//记录任务节点信息
		if( "ok".equals(string)) {
			if (taskNodeInnerResource.insert(taskNodeDTO) <= 0) {
				log.info("====== 记录任务节点信息失败 ======");
			} else {
				log.info("====== 记录任务节点信息成功 ======");
			}
			//记录创建申请单过程中的变量
			Integer taskType = taskInnerResource.qryTaskType(taskNo);
			taskNodeVariateInnerResource.getJSONToInsert(taskNodeNo,taskType,operateType,loadNoteDTO);
		}
		return string;
	}
}
