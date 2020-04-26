package com.zjft.microservice.treasurybrain.business.service.impl;

import com.zjft.microservice.treasurybrain.business.dto.*;
import com.zjft.microservice.treasurybrain.business.po.TransferTaskInOutPo;
import com.zjft.microservice.treasurybrain.business.repository.*;
import com.zjft.microservice.treasurybrain.business.service.SelfProductService;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.CallCustomerInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskDetailInfoInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityDetailInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨志勇
 * @since 2019/10/11
 */

@Slf4j
@Service
public class SelfProductServiceImpl implements SelfProductService {

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskDetailInfoInnerResource taskDetailInfoInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskEntityDetailInnerResource taskEntityDetailInnerResource;

	@Resource
	private TaskInOutMapper taskInOutMapper;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private CallCustomerInnerResource callCustomerInnerResource;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> applyForSelfProduct(TransferTaskInfoDTO transferTaskInfoDTO) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

		String clrCenterNo = StringUtil.parseString(transferTaskInfoDTO.getClrCenterNo());
		String planFinishDate = StringUtil.parseString(transferTaskInfoDTO.getPlanFinishDate());
		int taskType = StringUtil.objectToInt(transferTaskInfoDTO.getTaskType());
		String taskNo = taskInnerResource.getNextLogicId(clrCenterNo, taskType);
		String note = StringUtil.parseString(transferTaskInfoDTO.getNote());
		String authUser = ServletRequestUtil.getUsername();
		String creatTime = CalendarUtil.getSysTimeYMDHMS();

		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());
		int customerType = StringUtil.objectToInt(transferTaskInfoDTO.getCustomerType());

		List transferTaskDetailList = transferTaskInfoDTO.getTransferTaskDetailDTO();

		//TODO 通用性改造

		//根据taskType查询线路号
		String lineNo = "";
		if (customerType == 3) { //网点
			lineNo = sysOrgInnerResource.selectLineNo(customerNo);
		} else {                 //客户
			lineNo = callCustomerInnerResource.selectOutOrgLineNo(customerNo);
		}

		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("taskNo", taskNo);
		paraMap.put("taskType", taskType);
		paraMap.put("note", note);
		//新申请的任务单，默认任务单状态为已创建
		paraMap.put("status", "201");
		paraMap.put("createOpNo", authUser);
		paraMap.put("createTime", creatTime);
		paraMap.put("clrCenterNo", clrCenterNo);
		paraMap.put("lineNo", lineNo);
		paraMap.put("planFinishDate", planFinishDate);
		//插入task_tabel 任务单号(taskNo) 出库日期(planFinishDate)   任务单类型（(taskType）
		int a = taskInnerResource.insertSelectiveByMap(paraMap);
		if (a == -1) {
			throw new RuntimeException("失败");
		}

		HashMap<String, Object> parabMap = new HashMap<String, Object>();
		parabMap.put("taskNo", taskNo);
		parabMap.put("customerType", customerType);
		parabMap.put("customerNo", customerNo);
		parabMap.put("note", note);

		//插入task_detail_table: 任务单号(taskNo)  物品类型（CUSTOMER_TYPE）   备注（出库日期）
		int b = taskDetailInfoInnerResource.insertSelectiveByMap(parabMap);
		if (b == -1) {
			throw new RuntimeException("失败");
		}


		for (int i = 0; i < transferTaskDetailList.size(); i++) {
			TransferTaskDetailDTO transferTaskDetailDTO = (TransferTaskDetailDTO) transferTaskDetailList.get(i);
			//插入task_entity_table: 任务单号(taskNo)  容器编号（CONTAINER_NO） CUSTOMER_NO(服务对象编号)  NOTE(出库日期)
			HashMap<String, Object> paracMap = new HashMap<String, Object>();
			String containerNo = StringUtil.parseString(transferTaskDetailDTO.getContainerNo());
			String outDate = StringUtil.parseString(transferTaskDetailDTO.getOutDate());
			String depositType = StringUtil.parseString(transferTaskDetailDTO.getDepositType());
			int containerType = StringUtil.objectToInt(transferTaskDetailDTO.getContainerType());

			//如果箱子类型是解现箱，那么寄库类型不区分，默认为null  3:解现箱  4：寄存箱
			if (containerType == 3) {
				depositType = null;
			}

			paracMap.put("taskNo", taskNo);
			paracMap.put("containerNo", containerNo);
			paracMap.put("customerNo", customerNo);
			paracMap.put("note", outDate);
			paracMap.put("depositType", depositType);
			paracMap.put("direction", 2); //1：出库  2：入库
			paracMap.put("leafFlag", 1);//调拨任务单中的容器默认为最下级容器
			int f = taskEntityInnerResource.insertSelectiveByMap(paracMap);
			if (f == -1) {
				throw new RuntimeException("失败");
			}
			String id = java.util.UUID.randomUUID().toString().replace("-", "");
			HashMap<String, Object> paradMap = new HashMap<String, Object>();
			paradMap.put("id", id);
			paradMap.put("taskNo", taskNo);
			paradMap.put("containerNo", containerNo);
			paradMap.put("opNo", authUser);
			paradMap.put("containerType", containerType);
			paradMap.put("opTime", CalendarUtil.getSysTimeYMDHMS());
			int e = taskEntityDetailInnerResource.insertSelectiveByMap(paradMap);
			if (e == -1) {
				throw new RuntimeException("失败");
			}
		}


		List currencyTypeDTO = transferTaskInfoDTO.getTransferCurrencyTypeDTO();
		if (currencyTypeDTO != null) {
			for (int i = 0; i < currencyTypeDTO.size(); i++) {
				TransferCurrencyTypeDTO transferCurrencyTypeDTO = (TransferCurrencyTypeDTO) currencyTypeDTO.get(i);
				String id = java.util.UUID.randomUUID().toString().replace("-", "");
				TransferTaskInOutPo transferTaskInOutPo = new TransferTaskInOutPo();
				transferTaskInOutPo.setId(id);
				transferTaskInOutPo.setTaskNo(taskNo);
				transferTaskInOutPo.setCustomerNo(customerNo);
				transferTaskInOutPo.setDirection(1);
				transferTaskInOutPo.setAmount(transferCurrencyTypeDTO.getAmount());
				transferTaskInOutPo.setCurrencyType(transferCurrencyTypeDTO.getCurrencyType());
				transferTaskInOutPo.setCurrencyCode(transferCurrencyTypeDTO.getCurrencyCode());
				transferTaskInOutPo.setDenomination(transferCurrencyTypeDTO.getDenomination());
				int c = taskInOutMapper.insertSelective(transferTaskInOutPo);
				if (c == -1) {
					throw new RuntimeException("失败");
				}
			}
		} else {
			TransferTaskInOutPo transferTaskInOutPo = new TransferTaskInOutPo();
			String id = java.util.UUID.randomUUID().toString().replace("-", "");
			transferTaskInOutPo.setId(id);
			transferTaskInOutPo.setTaskNo(taskNo);
			transferTaskInOutPo.setCustomerNo(customerNo);
			transferTaskInOutPo.setDirection(1);
			int c = taskInOutMapper.insertSelective(transferTaskInOutPo);
			if (c == -1) {
				throw new RuntimeException("失败");
			}
		}

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "产品业务申请成功！");
		//用于记录操作信息
		retMap.put("taskNo", taskNo);
		return retMap;
	}

}
