package com.zjft.microservice.treasurybrain.business.service.impl;

import com.zjft.microservice.treasurybrain.business.dto.*;
import com.zjft.microservice.treasurybrain.business.po.TransferTaskInOutPo;
import com.zjft.microservice.treasurybrain.business.repository.TaskInOutMapper;
import com.zjft.microservice.treasurybrain.business.service.NotesReceiptService;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineScheduleInnerResource;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author 韩通
 * @since 2019/11/14
 */
@Slf4j
@Service
public class NotesReceiptServiceImpl implements NotesReceiptService {
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
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private LineScheduleInnerResource lineScheduleInnerResource;

	@Resource
	private TaskLineInnerResource taskLineInnerResource;

	@Resource
	private TaskDetailPropertyInnerResource taskDetailPropertyInnerResource;
	/**
	 * 钞处领现申请
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> applyForNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

		String clrCenterNo = StringUtil.parseString(transferTaskInfoDTO.getClrCenterNo());
		String planFinishDate = StringUtil.parseString(transferTaskInfoDTO.getPlanFinishDate());
		String theYearMonth = planFinishDate.substring(0, 7);
		String theDay = planFinishDate.substring(8);
		int taskType = StringUtil.objectToInt(transferTaskInfoDTO.getTaskType());
		String taskNo = taskInnerResource.getNextLogicId(clrCenterNo,taskType);
		String note = StringUtil.parseString(transferTaskInfoDTO.getNote());
		String authUser = ServletRequestUtil.getUsername();
		String creatTime = CalendarUtil.getSysTimeYMDHMS();
		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());
		int customerType = StringUtil.objectToInt(transferTaskInfoDTO.getCustomerType());

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
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "生成领现任务单成功！");
		retMap.put("taskNo", taskNo);

		//推送给所有人信息
		paraMap.put("type", "JinKuQueRen");
		String jsonStorageInfo = JSONUtil.createJsonString(paraMap);
		sendInfoInnerResource.sendInfo2All(jsonStorageInfo);

		return retMap;
	}


	/**
	 * 钞处领现修改
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO modNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);

		String taskNo = StringUtil.parseString(transferTaskInfoDTO.getTaskNo());
		if (taskInnerResource.getByPrimaryKey(taskNo) == null) {
			dto.setRetCode(RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
			dto.setRetMsg(RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return dto;
		}
		//判断任务单状态
		String operateType = transferTaskInfoDTO.getOperateType();
		String operateTypeName = serviceInnerResource.qryNameByOperateType(operateType);
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		Set set = new LinkedHashSet();
		boolean convert = serviceInnerResource.isConvert(taskNo, operateType, set);
		if (convert == false) {
			dto.setRetCode(RetCodeEnum.FAIL.getCode());
			dto.setRetMsg("任务单当前状态无法执行【"+operateTypeName+"】操作");
			return dto;
		}

		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());
		String planFinishDate = StringUtil.parseString(transferTaskInfoDTO.getPlanFinishDate());
		String theYearMonth = planFinishDate.substring(0, 7);
		String theDay = planFinishDate.substring(8);
		String note = StringUtil.parseString(transferTaskInfoDTO.getNote());

		//查询服务对象基本信息
		Map<String, String> customerMap = sysOrgInnerResource.qryCenterByNo(customerNo);
		String customerName = StringUtil.parseString(customerMap.get("NAME"));
		String customerAddress = StringUtil.parseString(customerMap.get("ADDRESS"));
		String lng = StringUtil.parseString(customerMap.get("X"));
		String lat = StringUtil.parseString(customerMap.get("Y"));

		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("taskNo", taskNo);
		paraMap.put("customerNo", customerNo);
		paraMap.put("customerName", customerName);
		paraMap.put("address", customerAddress);
		paraMap.put("note", note);
		paraMap.put("planFinishDate", planFinishDate);
		paraMap.put("status", nextStatus);
		//修改task_table
		int a = taskInnerResource.updateByPrimaryKeyMap(paraMap);
		if (a == -1) {
			throw new RuntimeException("修改task_table失败");
		}

		//修改task_line
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
		taskLineInnerResource.updateSelectiveByMap(taskLineMap);



		//插入task_detail_table和task_detail_property_table,修改前先将两张表原有任务数据删除
		taskDetailPropertyInnerResource.deleteByTaskNo(taskNo);
		taskDetailInfoInnerResource.deleteByTaskNo(taskNo);
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

		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("修改领现任务单成功！");

		return dto;
	}

	/**
	 * 钞处解现申请（金额以物品为单位）
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> transferForClearCenter(TransferTaskInfoDTO transferTaskInfoDTO) {
		List taskEntityDTOList = transferTaskInfoDTO.getTaskEntityDTOList();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

		String clrCenterNo = StringUtil.parseString(transferTaskInfoDTO.getClrCenterNo());
		String planFinishDate = StringUtil.parseString(transferTaskInfoDTO.getPlanFinishDate());
		String theYearMonth = planFinishDate.substring(0, 7);
		String theDay = planFinishDate.substring(8);
		int taskType = StringUtil.objectToInt(transferTaskInfoDTO.getTaskType());
		String taskNo = taskInnerResource.getNextLogicId(clrCenterNo, taskType);
		String note = StringUtil.parseString(transferTaskInfoDTO.getNote());
		String authUser = ServletRequestUtil.getUsername();
		String creatTime = CalendarUtil.getSysTimeYMDHMS();
		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());
		int customerType = StringUtil.objectToInt(transferTaskInfoDTO.getCustomerType());

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
		if (taskProductDTOList != null) {
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
		}
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
			Integer depositType = StringUtil.objectToInt(taskEntityDTO.getDepositType());

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
			paracMap.put("depositType", depositType);
			int f = taskEntityInnerResource.insertSelectiveByMap(paracMap);
			if (f == -1) {
				throw new RuntimeException("失败");
			}
			List taskEntityDetailDTO = taskEntityDTO.getTaskEntityDetailDTOList();
			if (taskEntityDetailDTO != null ) {
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
		}
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "生成钞处解现任务单成功！");
		retMap.put("taskNo", taskNo);
		return retMap;
	}

	/**
	 * 钞处解现修改
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO modForTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		List taskEntityDTOList = transferTaskInfoDTO.getTaskEntityDTOList();
		String taskNo = StringUtil.parseString(transferTaskInfoDTO.getTaskNo());
		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());
		TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
		if (taskTableDTO == null) {
			dto.setRetCode(RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
			dto.setRetMsg(RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return dto;
		}
		String note = StringUtil.parseString(transferTaskInfoDTO.getNote());
		String planFinishDate = StringUtil.parseString(transferTaskInfoDTO.getPlanFinishDate());
		String theYearMonth = planFinishDate.substring(0, 7);
		String theDay = planFinishDate.substring(8);

		//查询服务对象基本信息
		Map<String, String> customerMap = sysOrgInnerResource.qryCenterByNo(customerNo);
		String customerName = StringUtil.parseString(customerMap.get("NAME"));
		String customerAddress = StringUtil.parseString(customerMap.get("ADDRESS"));
		String lng = StringUtil.parseString(customerMap.get("X"));
		String lat = StringUtil.parseString(customerMap.get("Y"));

		//修改task_table
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("taskNo", taskNo);
		paraMap.put("customerNo", customerNo);
		paraMap.put("customerName", customerName);
		paraMap.put("address", customerAddress);
		paraMap.put("note", note);
		paraMap.put("planFinishDate", planFinishDate);
		//默认修改之后的任务单状态变为已创建
		paraMap.put("status", 201);
		int a = taskInnerResource.updateByPrimaryKeyMap(paraMap);
		if (a == -1) {
			throw new RuntimeException("修改task_table失败");
		}

		//修改task_line
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
		taskLineInnerResource.updateSelectiveByMap(taskLineMap);



		//插入task_detail_table和task_detail_property_table,修改前先将两张表原有任务数据删除
		taskDetailPropertyInnerResource.deleteByTaskNo(taskNo);
		taskDetailInfoInnerResource.deleteByTaskNo(taskNo);
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

		//删除task_entity_table 和 task_entity_detail 然后在插入
		taskEntityDetailInnerResource.deleteByTaskNo(taskNo);
		taskEntityInnerResource.deleteByTaskNo(taskNo);
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
			Integer depositType = StringUtil.objectToInt(taskEntityDTO.getDepositType());

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
			paracMap.put("depositType", depositType);
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

		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("修改钞处解现任务单成功！");

		return dto;
	}
}
