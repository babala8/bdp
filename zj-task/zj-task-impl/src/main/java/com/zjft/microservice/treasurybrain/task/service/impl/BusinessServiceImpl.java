package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineScheduleInnerResource;
import com.zjft.microservice.treasurybrain.task.domain.TaskInfo;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.repository.*;
import com.zjft.microservice.treasurybrain.task.service.BusinessService;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 韩 通
 * @since 2020-03-01
 */
@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

	@Resource
	private TaskInfoMapper taskInfoMapper;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private LineScheduleInnerResource lineScheduleInnerResource;

	@Resource
	private TaskLineMapper taskLineMapper;

	@Resource
	private TaskDetailMapper taskDetailMapper;

	@Resource
	private TaskDetailPropertyMapper taskDetailPropertyMapper;

	@Resource
	private TaskEntityMapper taskEntityMapper;

	@Resource
	private TaskEntityDetailMapper taskEntityDetailMapper;
	/**
	 * 任务单申请
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> applyForTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
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
		paraMap.put("planFinishDate", planFinishDate);
		//插入task_tabel
		int a = taskInfoMapper.insertSelectiveByMap(paraMap);
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
		//如果该服务对象有排班信息，该任务单绑定线路，否则不绑定线路
		if (lineScheduleDTO != null) {
			HashMap<String, Object> taskLineMap = new HashMap<String, Object>();
			taskLineMap.put("lineWorkId", lineScheduleDTO.getLineWorkId());
			taskLineMap.put("taskNo", taskNo);
			taskLineMap.put("sortNo", lineScheduleDTO.getSortNo());
			taskLineMap.put("address", customerAddress);
			taskLineMap.put("lng", lng);
			taskLineMap.put("lat", lat);
			//todo 最早到达时间与最迟到达时间应该从线路排班中取得
//			taskLineMap.put("earlyTime", earlyTime);
//			taskLineMap.put("latestTime", latestTime);
			taskLineMapper.insertSelectiveByMap(taskLineMap);
		}

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
				int b = taskDetailMapper.insertSelectiveByMap(parabMap);
				if (b == -1) {
					throw new RuntimeException("失败");
				}

				//插入task_detail_property_table
				List<PropertyDTO> detailList = taskProductDTO.getDetailList();
				if (detailList != null) {
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
						int c = taskDetailPropertyMapper.insertSelectiveByMap(paramMap);
						if (c == -1) {
							throw new RuntimeException("失败");
						}
					}
				}
			}
		}

		//插入task_entity_table 和 task_entity_detail
		List<TaskEntityDTO> taskEntityDTOList = transferTaskInfoDTO.getTaskEntityDTOList();
		if (taskEntityDTOList != null) {
			for (int i = 0; i < taskEntityDTOList.size(); i++) {
				TaskEntityDTO taskEntityDTO = (TaskEntityDTO) taskEntityDTOList.get(i);
				//插入task_entity_table
				HashMap<String, Object> paracMap = new HashMap<String, Object>();
				String id = java.util.UUID.randomUUID().toString().replace("-", "");
				String entityNo = StringUtil.parseString(taskEntityDTO.getEntityNo());
				String productNo = StringUtil.parseString(taskEntityDTO.getProductNo());
				String parentEntity = StringUtil.parseString(taskEntityDTO.getParentEntity());
				BigDecimal amount = StringUtil.objBigDecimal(taskEntityDTO.getAmount());
				Integer direction = StringUtil.objectToInt(taskEntityDTO.getDirection());
				Integer leafFlag = StringUtil.objectToInt(taskEntityDTO.getLeafFlag());
				Integer depositType = StringUtil.objectToInt(taskEntityDTO.getDepositType());
				String note1 = StringUtil.parseString(taskEntityDTO.getNote());

				//如果箱子类型是解现箱，那么寄库类型不区分，默认为null  3:解现箱  4：寄存箱
//				if (containerNo == 3) {
//	//				depositType = null;
//	//			}
				paracMap.put("id", id);
				paracMap.put("taskNo", taskNo);
				paracMap.put("entityNo", entityNo);
				paracMap.put("productNo", productNo);
				paracMap.put("parentEntity", parentEntity);
				paracMap.put("amount", amount);
				paracMap.put("direction", direction); //1：出库  2：入库
				paracMap.put("leafFlag", leafFlag);//调拨任务单中的容器默认为最下级容器
				paracMap.put("note", note1);
				paracMap.put("depositType", depositType);
				int f = taskEntityMapper.insertSelectiveByMap(paracMap);
				if (f == -1) {
					throw new RuntimeException("失败");
				}
				List<TaskEntityDetailDTO> taskEntityDetailDTOList = taskEntityDTO.getTaskEntityDetailDTOList();
				if (taskEntityDetailDTOList != null) {
					for (int j = 0; j < taskEntityDetailDTOList.size(); j++) {
						TaskEntityDetailDTO taskEntityDetailDTO1 = taskEntityDetailDTOList.get(j);
						String key = StringUtil.parseString(taskEntityDetailDTO1.getKey());
						String value = StringUtil.parseString(taskEntityDetailDTO1.getValue());
						String name = StringUtil.parseString(taskEntityDetailDTO1.getName());

						HashMap<String, Object> paradMap = new HashMap<String, Object>();
						paradMap.put("id", id);
						paradMap.put("taskNo", taskNo);
						paradMap.put("key", key);
						paradMap.put("value", value);
						paradMap.put("name", name);
						int e = taskEntityDetailMapper.insertSelectiveByMap(paradMap);
						if (e == -1) {
							throw new RuntimeException("失败");
						}
					}
				}
			}
		}

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "任务单申请成功！");
		retMap.put("taskNo", taskNo);
		return retMap;
	}

	/**
	 * 网点解现&寄库修改
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO modForTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String taskNo = StringUtil.parseString(transferTaskInfoDTO.getTaskNo());
		String customerNo = StringUtil.parseString(transferTaskInfoDTO.getCustomerNo());
		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
		if (taskInfo == null) {
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
		int a = taskInfoMapper.updateByPrimaryKeyMap(paraMap);
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
		//如果该服务对象有排班信息，该任务单绑定线路，否则不绑定线路
		if (lineScheduleDTO != null) {
			HashMap<String, Object> taskLineMap = new HashMap<String, Object>();
			taskLineMap.put("lineWorkId", lineScheduleDTO.getLineWorkId());
			taskLineMap.put("taskNo", taskNo);
			taskLineMap.put("sortNo", lineScheduleDTO.getSortNo());
			taskLineMap.put("address", customerAddress);
			taskLineMap.put("lng", lng);
			taskLineMap.put("lat", lat);
			//todo 最早到达时间与最迟到达时间应该从线路排班中取得
//			taskLineMap.put("earlyTime", earlyTime);
//			taskLineMap.put("latestTime", latestTime);
			taskLineMapper.updateSelectiveByMap(taskLineMap);
		}

		//插入task_detail_table和task_detail_property_table,修改前先将两张表原有任务数据删除
		taskDetailPropertyMapper.deleteByTaskNo(taskNo);
		taskDetailMapper.deleteByTaskNo(taskNo);
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
				int b = taskDetailMapper.insertSelectiveByMap(parabMap);
				if (b == -1) {
					throw new RuntimeException("失败");
				}

				//插入task_detail_property_table
				List<PropertyDTO> detailList = taskProductDTO.getDetailList();
				if (detailList != null) {
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
						int c = taskDetailPropertyMapper.insertSelectiveByMap(paramMap);
						if (c == -1) {
							throw new RuntimeException("失败");
						}
					}
				}
			}
		}

		//删除task_entity_table 和 task_entity_detail 然后在插入
		taskEntityDetailMapper.deleteByTaskNo(taskNo);
		taskEntityMapper.deleteByTaskNo(taskNo);
		List<TaskEntityDTO> taskEntityDTOList = transferTaskInfoDTO.getTaskEntityDTOList();
		if (taskEntityDTOList != null) {
			for (int i = 0; i < taskEntityDTOList.size(); i++) {
				TaskEntityDTO taskEntityDTO = (TaskEntityDTO) taskEntityDTOList.get(i);
				//插入task_entity_table
				HashMap<String, Object> paracMap = new HashMap<String, Object>();
				String id = java.util.UUID.randomUUID().toString().replace("-", "");
				String entityNo = StringUtil.parseString(taskEntityDTO.getEntityNo());
				String productNo = StringUtil.parseString(taskEntityDTO.getProductNo());
				String parentEntity = StringUtil.parseString(taskEntityDTO.getParentEntity());
				BigDecimal amount = StringUtil.objBigDecimal(taskEntityDTO.getAmount());
				Integer direction = StringUtil.objectToInt(taskEntityDTO.getDirection());
				Integer leafFlag = StringUtil.objectToInt(taskEntityDTO.getLeafFlag());
				String note1 = StringUtil.parseString(taskEntityDTO.getNote());
				Integer depositType = StringUtil.objectToInt(taskEntityDTO.getDepositType());

//				//如果箱子类型是解现箱，那么寄库类型不区分，默认为null  3:解现箱  4：寄存箱
//				if (containerType == 3) {
//					depositType = null;
//				}
				paracMap.put("id", id);
				paracMap.put("taskNo", taskNo);
				paracMap.put("entityNo", entityNo);
				paracMap.put("productNo", productNo);
				paracMap.put("parentEntity", parentEntity);
				paracMap.put("amount", amount);
				paracMap.put("direction", direction); //1：出库  2：入库
				paracMap.put("leafFlag", leafFlag);//调拨任务单中的容器默认为最下级容器 1
				paracMap.put("note", note1);
				paracMap.put("depositType", depositType);
				int f = taskEntityMapper.insertSelectiveByMap(paracMap);
				if (f == -1) {
					throw new RuntimeException("失败");
				}
				List taskEntityDetailDTO = taskEntityDTO.getTaskEntityDetailDTOList();
				if (taskEntityDetailDTO != null) {
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
						int e = taskEntityDetailMapper.insertSelectiveByMap(paradMap);
						if (e == -1) {
							throw new RuntimeException("失败");
						}
					}
				}
			}
		}

		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("修改调拨任务单成功！");

		return dto;
	}
}
