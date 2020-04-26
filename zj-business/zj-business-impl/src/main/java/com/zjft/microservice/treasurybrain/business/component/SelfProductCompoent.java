package com.zjft.microservice.treasurybrain.business.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.business.dto.*;
import com.zjft.microservice.treasurybrain.business.po.TransferTaskInOutPo;
import com.zjft.microservice.treasurybrain.business.repository.TaskInOutMapper;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.CallCustomerInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineScheduleInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ZjComponentResource(group = "selfProduct")
public class SelfProductCompoent {

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

	@Resource
	private LineScheduleInnerResource lineScheduleInnerResource;

	@Resource
	private TaskLineInnerResource taskLineInnerResource;

	@Resource
	private TaskDetailPropertyInnerResource taskDetailPropertyInnerResource;

	/**
	 * 插入task_tabel
	 */
	@ZjComponentMapping("insertTask")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String insertTask(TransferTaskInfoDTO transferTaskInfoDTO, ObjectDTO returnDTO, List<String> taskNos) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

		String clrCenterNo = StringUtil.parseString(transferTaskInfoDTO.getClrCenterNo());
		String planFinishDate = StringUtil.parseString(transferTaskInfoDTO.getPlanFinishDate());
		String theYearMonth = planFinishDate.substring(0, 7);
		String theDay = planFinishDate.substring(8);
		int taskType = StringUtil.objectToInt(transferTaskInfoDTO.getTaskType());
		String taskNo = taskInnerResource.getNextLogicId(clrCenterNo, taskType);
		taskNos.add(taskNo);
		String note = StringUtil.parseString(transferTaskInfoDTO.getNote());
		String authUser = ServletRequestUtil.getUsername();
		String creatTime = CalendarUtil.getSysTimeYMDHMS();

		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());
		int customerType = StringUtil.objectToInt(transferTaskInfoDTO.getCustomerType());

		//TODO 通用性改造

//		//根据taskType查询线路号
//		String lineNo = "";
//		if (customerType == 3) { //网点
//			lineNo = sysOrgInnerResource.selectLineNo(customerNo);
//		} else {                 //客户
//			lineNo = callCustomerInnerResource.selectOutOrgLineNo(customerNo);
//		}
		//查询服务对象基本信息
		Map<String, String> customerMap = sysOrgInnerResource.qryCenterByNo(customerNo);
		String customerName = StringUtil.parseString(customerMap.get("NAME"));
		String customerAddress = StringUtil.parseString(customerMap.get("ADDRESS"));
		String lng = StringUtil.parseString(customerMap.get("X"));
		String lat = StringUtil.parseString(customerMap.get("Y"));

		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("taskNo", taskNo);
		paraMap.put("taskType", taskType);
		paraMap.put("customerNo", customerNo);
		paraMap.put("customerType", customerType);
		paraMap.put("customerName", customerName);
		paraMap.put("address", customerAddress);
		paraMap.put("note", note);
		//新申请的任务单，默认任务单状态为已创建
		paraMap.put("status", "201");
		paraMap.put("createOpNo", authUser);
		paraMap.put("createTime", creatTime);
		paraMap.put("clrCenterNo", clrCenterNo);
//		paraMap.put("lineNo", lineNo);
		paraMap.put("planFinishDate", planFinishDate);
		//插入task_tabel
		int a = taskInnerResource.insertSelectiveByMap(paraMap);
		if (a == -1) {
			throw new RuntimeException("失败");
		}

		//插入task_line
		//根据customerNo查询线路排班信息
		HashMap<String, Object> lineMap = new HashMap<String, Object>();
		lineMap.put("customerNo", customerNo);
		lineMap.put("theYearMonth", theYearMonth);
		lineMap.put("theDay", theDay);
		LineScheduleDTO lineScheduleDTO = lineScheduleInnerResource.selectByMap(lineMap);
		HashMap<String, Object> taskLineMap = new HashMap<String, Object>();
		taskLineMap.put("lineWorkId", lineScheduleDTO.getLineWorkId());
		taskLineMap.put("taskNo", taskNo);
		taskLineMap.put("sortNo", lineScheduleDTO.getSortNo());
		taskLineMap.put("address", customerAddress);
		taskLineMap.put("lng", lng);
		taskLineMap.put("lat", lat);
		//todo 最早到达时间与最迟到达时间应该从线路排班中取得
//		taskLineMap.put("earlyTime", earlyTime);
//		taskLineMap.put("latestTime", latestTime);
		taskLineInnerResource.insertSelectiveByMap(taskLineMap);


		//插入task_detail_table和task_detail_property_table
		List<TaskProductDTO> taskProductDTOList = transferTaskInfoDTO.getTaskProductDTOList();
		for (TaskProductDTO taskProductDTO : taskProductDTOList) {
			String productNo = taskProductDTO.getProductNo();
			String direction = taskProductDTO.getDirection();
			Integer amount = taskProductDTO.getAmount();

			//插入产品明细表task_detail_table
			String id = java.util.UUID.randomUUID().toString().replace("-", "");
			HashMap<String, Object> parabMap = new HashMap<String, Object>();
			parabMap.put("id", id);
			parabMap.put("taskNo", taskNo);
			parabMap.put("productNo", productNo);
			parabMap.put("direction", direction);
			parabMap.put("amount", amount);
			int b = taskDetailInfoInnerResource.insertSelectiveByMap(parabMap);
			if (b == -1) {
				throw new RuntimeException("失败");
			}

			//插入task_detail_property_table
			List<PropertyDTO> detailList = taskProductDTO.getDetailList();
			for (PropertyDTO propertyDTO : detailList) {
				String key = propertyDTO.getKey();
				String name = propertyDTO.getName();
				String value = propertyDTO.getValue();

				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("id", java.util.UUID.randomUUID().toString().replace("-", ""));
				paramMap.put("detailId", id);
				paramMap.put("key", key);
				paramMap.put("name", name);
				paramMap.put("value", value);
				paramMap.put("taskNo", taskNo);
				int c = taskDetailPropertyInnerResource.insertSelectiveByMap(paramMap);
				if (c == -1) {
					throw new RuntimeException("失败");
				}
			}
		}
		return "ok";
	}

	/**
	 * 插入task_tabel
	 */
	@ZjComponentMapping("insertTaskEntity")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String insertTaskEntity(TransferTaskInfoDTO transferTaskInfoDTO, ObjectDTO returnDTO, List<String> taskNos) {
		List transferTaskDetailList = transferTaskInfoDTO.getTransferTaskDetailDTO();
		String clrCenterNo = StringUtil.parseString(transferTaskInfoDTO.getClrCenterNo());
		int taskType = StringUtil.objectToInt(transferTaskInfoDTO.getTaskType());
		String taskNo = taskNos.get(0);
		String authUser = ServletRequestUtil.getUsername();
		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());

		List<TaskEntityDTO> taskEntityDTOList = transferTaskInfoDTO.getTaskEntityDTOList();
		//插入task_entity_table 和 task_entity_detail
		for (int i = 0; i < taskEntityDTOList.size(); i++) {
			TaskEntityDTO taskEntityDTO = (TaskEntityDTO) taskEntityDTOList.get(i);
			//插入task_entity_table
			HashMap<String, Object> paracMap = new HashMap<String, Object>();
			String id = java.util.UUID.randomUUID().toString().replace("-", "");
			String entityNo = StringUtil.parseString(taskEntityDTO.getEntityNo());
			String productNo = StringUtil.parseString(taskEntityDTO.getProductNo());
			String parentEntity = StringUtil.parseString(taskEntityDTO.getParentEntity());
			BigDecimal amount = StringUtil.objBigDecimal(taskEntityDTO.getAmount());
//			Integer direction = StringUtil.objectToInt(taskEntityDTO.getDirection());
//			Integer leafFlag = StringUtil.objectToInt(taskEntityDTO.getLeafFlag());
			String note1 = StringUtil.parseString(taskEntityDTO.getNote());

//			//如果箱子类型是解现箱，那么寄库类型不区分，默认为null  3:解现箱  4：寄存箱
//			if (containerType == 3) {
//				depositType = null;
//			}
			paracMap.put("id", id);
			paracMap.put("taskNo", taskNo);
			paracMap.put("entityNo", entityNo);
			paracMap.put("productNo", productNo);
			paracMap.put("parentEntity", parentEntity);
			paracMap.put("amount", amount);
			paracMap.put("direction", 2); //1：出库  2：入库
			paracMap.put("leafFlag", 1);//调拨任务单中的容器默认为最下级容器
			paracMap.put("note", note1);
			int f = taskEntityInnerResource.insertSelectiveByMap(paracMap);
			if (f == -1) {
				throw new RuntimeException("失败");
			}
			List taskEntityDetailDTO = taskEntityDTO.getTaskEntityDetailDTOList();
			for (int j = 0; j < taskEntityDetailDTO.size(); j++) {
				TaskEntityDetailDTO taskEntityDetailDTO1 = (TaskEntityDetailDTO)taskEntityDetailDTO.get(j);
				String key = StringUtil.parseString(taskEntityDetailDTO1.getKey());
				String value = StringUtil.parseString(taskEntityDetailDTO1.getValue());
				String name = StringUtil.parseString(taskEntityDetailDTO1.getName());

				HashMap<String, Object> paradMap = new HashMap<String, Object>();
				paradMap.put("id", id);
				paradMap.put("taskNo", taskNo);
				paradMap.put("key", key);
				paradMap.put("value", value);
				paradMap.put("name", name);
				int e = taskEntityDetailInnerResource.insertSelectiveByMap(paradMap);
				if (e == -1) {
					throw new RuntimeException("失败");
				}
			}

		}

		return "ok";

	}

	/**
	 * 插入task_tabel
	 */
	@ZjComponentMapping("insertTaskInOut")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String insertTaskInOut(TransferTaskInfoDTO transferTaskInfoDTO, ObjectDTO returnDTO, List<String> taskNos) {
		String clrCenterNo = StringUtil.parseString(transferTaskInfoDTO.getClrCenterNo());
		int taskType = StringUtil.objectToInt(transferTaskInfoDTO.getTaskType());
		String taskNo = taskInnerResource.getNextLogicId(clrCenterNo, taskType);

		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());
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
		returnDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		returnDTO.setRetMsg("产品业务申请成功");
		return "ok";
	}

	/**
	 * 产品申请失败
	 */
	@ZjComponentMapping("applyForSelfProductFail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String applyForSelfProductFail(TransferTaskInfoDTO transferTaskInfoDTO, ObjectDTO returnDTO, List<String> taskNos) {
		returnDTO.setRetCode("FF");
		return "fail";
	}
}
