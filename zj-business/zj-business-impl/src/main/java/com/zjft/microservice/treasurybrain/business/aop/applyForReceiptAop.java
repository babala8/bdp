package com.zjft.microservice.treasurybrain.business.aop;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
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
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 网点领现任务单切面类
 * - 网点领现申请切面
 * - 网点领现修改切面
 *
 * @author 韩通
 * @since 2019-11-01
 */
@Aspect
@Component
@Slf4j
public class applyForReceiptAop {

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskNodeVariateInnerResource taskNodeVariateInnerResource;

	@Resource
	private TaskNodeInnerResource taskNodeInnerResource;

	@Pointcut("execution(* com.zjft.microservice.treasurybrain.business.service.impl.NetBusinessServiceImpl.applyForCashTransfer(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NetBusinessServiceImpl.applyForReceipt(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.SelfProductServiceImpl.applyForSelfProduct(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NetBusinessServiceImpl.applyForTransfer(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NotesReceiptServiceImpl.transferForClearCenter(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NotesReceiptServiceImpl.applyForNotesReceipt(..)) ")
	public void applyForcut() {
	}

	@Pointcut("execution(* com.zjft.microservice.treasurybrain.business.service.impl.NetBusinessServiceImpl.modCashTransfer(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NetBusinessServiceImpl.modReceipt(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NetBusinessServiceImpl.modForTransfer(..)) || " +
//			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.TransferServiceImpl.modDevClrTime(..)) ||" +
//			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NotesReceiptServiceImpl.auditNotesReceipt(..)) || " +
			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NotesReceiptServiceImpl.modNotesReceipt(..)) "
//			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NotesReceiptServiceImpl.affirmOutNotesReceipt(..)) || " +
//			"execution(* com.zjft.microservice.treasurybrain.business.service.impl.NotesReceiptServiceImpl.outStorageNotesReceipt(..))"
	)
	public void modcut() {
	}

//	@Pointcut("execution(* com.zjft.microservice.treasurybrain.business.service.impl.NotesReceiptServiceImpl.affirmOutBatchNotesReceipt(..))")
//	public void modBatchcut() {
//	}

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

//	@Around("modBatchcut()")
//	public DTO modBatcAroundAll(ProceedingJoinPoint joinPoint) throws Throwable{
//		log.debug("@Around() - modBatcAroundAll() - @Around()");
//		TransferTaskInfoDTO transferTaskInfoDTO = (TransferTaskInfoDTO) joinPoint.getArgs()[0];
//
//		String operateType = transferTaskInfoDTO.getOperateType();
//		Integer nextStatus = productMapper.qryNextByOperateType(operateType);
//		String description = productMapper.qryNameByOperateType(operateType);
//		String curTime = CalendarUtil.getSysTimeYMDHMS();
//		String OpNo = ServletRequestUtil.getUsername();
//
//		List<TaskNodeDO> taskNodeDOList = new ArrayList<TaskNodeDO>();
//		List<TransferTaskDetailDTO> transferTaskDetailDTOList = transferTaskInfoDTO.getTransferTaskDetailDTO();
//		for (TransferTaskDetailDTO transferTaskDetailDTO : transferTaskDetailDTOList) {
//			//查询当前任务单状态
//			String taskNo = transferTaskDetailDTO.getTaskNo();
//			Integer curStatus = productStatusConvertMapper.qryCurStatus(taskNo);
//			String taskNodeNo = getNextTaskNodeNo(taskNo);
//
//			// 任务节点信息
//			TaskNodeDO taskNodeDO = new TaskNodeDO();
//			taskNodeDO.setTaskNodeNo(taskNodeNo);
//			taskNodeDO.setTaskNo(taskNo);
//			taskNodeDO.setOpNo(OpNo);
//			taskNodeDO.setFinishTime(curTime);
//			taskNodeDO.setBeforeNode(curStatus);
//			taskNodeDO.setCurNode(nextStatus);
//			taskNodeDO.setOperateType(operateType);
//			taskNodeDO.setDescription(description);
//			taskNodeDOList.add(taskNodeDO);
//		}
//
//		DTO dto = (DTO) joinPoint.proceed();
//
//		if(RetCodeEnum.SUCCEED.getCode() == dto.getRetCode()){
//			if ( taskNodeMapper.insertBatch(taskNodeDOList) <= 0 ){
//				log.info("====== 记录任务节点信息失败 ======");
//			}else {
//				log.info("====== 记录任务节点信息成功 ======");
//			}
//		}
//		return dto;
//	}


}
