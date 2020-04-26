package com.zjft.microservice.treasurybrain.task.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.AddnoteLineInnerResource;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineScheduleInnerResource;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineWorkInnerResource;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.storage.web_inner.TaskShelfUserInnerResource;
import com.zjft.microservice.treasurybrain.task.domain.*;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskDTOConverter;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityDetailPO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import com.zjft.microservice.treasurybrain.task.repository.*;
import com.zjft.microservice.treasurybrain.task.service.TaskService;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskDetailPropertyInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskLineInnerResource;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

	@Resource
	private TaskInfoMapper taskInfoMapper;

	@Resource
	private TaskDetailMapper taskDetailMapper;

	@Resource
	private TaskEntityMapper taskEntityMapper;

	@Resource
	private TaskEntityDetailMapper taskEntityDetailMapper;

	@Resource
	private TaskNodeMapper taskNodeMapper;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private AddnoteLineInnerResource addnoteLineInnerResource;

	@Resource
	private TaskShelfUserInnerResource taskShelfUserInnerResource;

	@Resource
	private TaskLineInnerResource taskLineInnerResource;

	@Resource
	private TaskDetailPropertyInnerResource taskDetailPropertyInnerResource;

	@Resource
	private TaskDetailPropertyMapper taskDetailPropertyMapper;

	@Resource
	private TaskLineMapper taskLineMapper;

	@Resource
	private LineScheduleInnerResource lineScheduleInnerResource;

	@Resource
	private LineWorkInnerResource lineWorkInnerResource;

	/**
	 * 加钞任务分配人员
	 * @param taskInfoDTO 分配人员信息
	 * @return 结果
	 */
	@Override
	public DTO assignTo(TaskInfoDTO taskInfoDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			//目前没有加钞任务审批，分配人员后直接审核通过
//			taskInfoDTO.setStatus(StatusEnum.TaskStatus.AUDITTED.getId());
			taskInfoDTO.setStatus(201);
			TaskInfo taskInfo = TaskDTOConverter.INSTANCE.domain2dto(taskInfoDTO);
			int x = taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
			if (x == 1) {
				dto.setRetMsg("人员分配成功");
				dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			}
		} catch (Exception e) {
			dto.setResult(RetCodeEnum.FAIL);
			dto.setRetMsg("人员分配失败");
			dto.setRetCode(RetCodeEnum.FAIL.getCode());
		}
		return dto;
	}

	/**
	 * 取消加钞计划
	 *
	 * @param taskNo, status
	 * @return 状态码
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> cancel(String taskNo, int status) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try {
		log.debug("[cancelAddNotesPlan]:" + taskNo);
		paramMap.put("taskNo", taskNo);
		paramMap.put("status", status);

		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
		if (taskInfo == null) {
			retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return retMap;
		}

		//int x = taskDetailMapper.updateByTaskNo(paramMap);
		int y = taskInfoMapper.updateByTaskNo(paramMap);
		if ( y == -1) {
			throw new RuntimeException("失败");
		}

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "取消任务单成功！");

		return retMap;
	}

	/**
	 * 分页查询任务单
	 *
	 */
	@Override
	public Map<String,Object> qryTask(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

		//计算分页参数
		Integer curPage = StringUtil.objectToInt(paramMap.get("curPage"));
		Integer pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));
		if (-1 == curPage) {
			curPage = 1;
		}
		if (-1 == pageSize) {
			pageSize = 10;
		}
		paramMap.put("startRow", pageSize * (curPage - 1));
		paramMap.put("endRow", pageSize * curPage);

		UserDTO authUser = sysUserResource.getAuthUserInfo();
		String orgNo = authUser.getOrgNo();
		String opNo = authUser.getUsername();
		Integer orgGradeNo = sysOrgInnerResource.qryOrgGradeNoByOrgNo(orgNo);
		Integer clrCenterFlag = sysOrgInnerResource.qryClrCenterFlag(orgNo);
		Integer productType = StringUtil.objectToInt(paramMap.get("productType"));//自定义产品
		if(productType !=1){
			productType =0;
		}

		//是否查询所有任务单（1：所有 0：当前登录人），默认为1
		Integer checkAll = StringUtil.objectToInt(paramMap.get("checkAll"));
		if(-1 == checkAll){
			checkAll = 1;
		}

		paramMap.put("opNo", opNo);
		paramMap.put("productType", productType);
		paramMap.put("orgNo", orgNo);
		paramMap.put("orgGradeNo", orgGradeNo);
		paramMap.put("clrCenterFlag", clrCenterFlag);
		paramMap.put("checkAll", checkAll);

		//查询任务单信息
		int totalRow = taskInfoMapper.selectTotalRow(paramMap);
		int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
		List<Map<String, Object>> taskList = taskInfoMapper.selectTask(paramMap);

//		String taskNo = StringUtil.parseString(paramMap.get("taskNo"));
//		Integer taskType = StringUtil.objectToInt(paramMap.get("taskType"));
			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

//			Map<String, String> addnotesLineMap = this.getAddnotesLineMap();
			for (Map<String, Object> resultMap : taskList) {
				Map<String, Object> entity = new HashMap<String, Object>();
				String taskNo = StringUtil.parseString(resultMap.get("taskNo"));
				entity.put("taskNo", taskNo);
				entity.put("taskType", StringUtil.parseString(resultMap.get("taskType")));
				entity.put("taskTypeName", StringUtil.parseString(resultMap.get("taskTypeName")));
				entity.put("planFinishDate", StringUtil.parseString(resultMap.get("planFinishDate")));
				entity.put("status", StringUtil.objectToInt(resultMap.get("status")));
				entity.put("clrCenterNo", StringUtil.parseString(resultMap.get("clrCenterNo")));
				entity.put("opNo1", StringUtil.parseString(resultMap.get("opNo1")));
				entity.put("opName1", StringUtil.parseString(resultMap.get("addnotesOpName1")));
				entity.put("opNo2", StringUtil.parseString(resultMap.get("opNo2")));
				entity.put("opName2", StringUtil.parseString(resultMap.get("addnotesOpName2")));
				entity.put("createOpNo", StringUtil.parseString(resultMap.get("createOpNo")));
				entity.put("createOpName", StringUtil.parseString(resultMap.get("createOpName")));
				entity.put("createTime", StringUtil.parseString(resultMap.get("createTime")));
				entity.put("lineNo", StringUtil.parseString(resultMap.get("lineNo")));
				entity.put("lineName", StringUtil.parseString(resultMap.get("lineName")));
				entity.put("addnotesPlanNo", StringUtil.parseString(resultMap.get("addnotesPlanNo")));
				entity.put("clrCenterName", StringUtil.parseString(resultMap.get("clrCenterName")));
				entity.put("shelfNo", StringUtil.parseString(resultMap.get("shelfNo")));
				entity.put("customerNo", StringUtil.parseString(resultMap.get("customerNo")));
				entity.put("customerType", StringUtil.parseString(resultMap.get("customerType")));
				entity.put("customerName", StringUtil.parseString(resultMap.get("customerName")));
				entity.put("note", StringUtil.parseString(resultMap.get("note")));
				entity.put("urgentFlag", StringUtil.parseString(resultMap.get("urgentFlag")));
				entity.put("address", StringUtil.parseString(resultMap.get("address")));
				entity.put("addnotesAmt", StringUtil.parseString(resultMap.get("addnotesAmt")));
				entity.put("statusName", StringUtil.parseString(resultMap.get("statusName")));

				//如果任务单类型为2（网点调拨任务单），则查询不同箱子的数量以及金额
				if (StringUtil.objectToInt(resultMap.get("taskType")) == 2) {

					String taskNos = StringUtil.parseString(resultMap.get("taskNo"));
//					int containerType = StatusEnum.ContainerType.UNDERSTANDING_BOX.getType();
					Map<String, Object> paramaMap = new HashMap<String, Object>();
					paramaMap.put("taskNo", taskNos);
					paramaMap.put("productNo","R0003");
					int underStaBoxCount = taskEntityDetailMapper.selectContainerNo(paramaMap);
//					int containerType1 = StatusEnum.ContainerType.DEPOSIT_BOX.getType();
					Map<String, Object> parambMap = new HashMap<String, Object>();
					parambMap.put("taskNo", taskNos);
					parambMap.put("productNo", "R0001");
					int depBoxCount = taskEntityDetailMapper.selectContainerNo(parambMap);
					//解现箱总金额
					Map<String, Object> paramcMap = new HashMap<String, Object>();
					paramcMap.put("taskNo", taskNos);
					paramcMap.put("direction", 1);
//					String deptBoxAmount = taskInOutInnerResource.selectSumAmount(paramcMap);
					Integer deptBoxAmount = taskDetailMapper.selectSumAmount(paramcMap);

					if (deptBoxAmount == null) {
						deptBoxAmount = 0;
					}
					// 现金款箱数量
					entity.put("underStandingBoxCount", underStaBoxCount);
					// 寄库款箱数量
					entity.put("depositBoxCount", depBoxCount);
					// 解现总金额
					entity.put("depositBoxAmount", deptBoxAmount);
					Map<String, Object> paraMap = new HashMap<String, Object>();
					paraMap.put("taskNo", taskNos);
				} else if (StringUtil.objectToInt(resultMap.get("taskType")) == 9) {
					String taskNo2 = StringUtil.parseString(resultMap.get("taskNo"));
					int containerType = StatusEnum.ContainerType.UNDERSTANDING_BOX.getType();
					Map<String, Object> paramaMap = new HashMap<String, Object>();
					paramaMap.put("taskNo", taskNo2);
					paramaMap.put("containerType", containerType);
					int underStaBoxCount = taskEntityDetailMapper.selectContainerNo(paramaMap);
					//现金款箱总金额
					BigDecimal deptBoxAmount = taskEntityDetailMapper.selectSumAmount(taskNo2);
					if (deptBoxAmount == null) {
						deptBoxAmount = new BigDecimal("0");
					}
					entity.put("underStandingBoxCount", underStaBoxCount);
					entity.put("depositBoxAmount", deptBoxAmount);
				} else if (StringUtil.objectToInt(resultMap.get("taskType")) == 4 ||
						StringUtil.objectToInt(resultMap.get("taskType")) == 8) {
					Map paramMap1 = new HashMap();
					paramMap1.put("taskNo", StringUtil.parseString(resultMap.get("taskNo")));
					paramMap1.put("direction", StatusEnum.Direction.OUT.getId());
//					String deptBoxAmount = taskInOutInnerResource.selectSumAmount(paramMap1);
					Integer deptBoxAmount = taskDetailMapper.selectSumAmount(paramMap1);
					entity.put("depositBoxAmount", deptBoxAmount);
				}
				//如果任务单类型为3（寄存箱出库任务单），则查询待出库箱子的数量以及金额
				if (StringUtil.objectToInt(resultMap.get("taskType")) == 3) {
					Map paramMap1 = new HashMap();
					paramMap1.put("taskNo", taskNo);
					paramMap1.put("direction", StatusEnum.Direction.OUT.getId());
					Integer deptBoxAmount = taskEntityMapper.selectSumAmount(paramMap1);
//					String deptBoxAmount = Optional.ofNullable(taskEntityMapper.selectSumAmount(paramMap1)).orElse("0");
					entity.put("depositBoxAmount", deptBoxAmount);
					Map<String, Object> parambMap = new HashMap<String, Object>();
					int containerType1 = StatusEnum.ContainerType.DEPOSIT_BOX.getType();
					String taskNos = StringUtil.parseString(resultMap.get("taskNo"));
					parambMap.put("taskNo", taskNos);
					parambMap.put("containerType", containerType1);
					int depBoxCount = taskEntityDetailMapper.selectContainerNo(parambMap);
					entity.put("depositBoxCount", depBoxCount);
				}


//				String lineNoString = StringUtil.parseString(resultMap.get("lineNo")).replaceAll("\"|\\[|\\]", "");
//				String lineName = null;
//				if (lineNoString != null) {
//					lineName = addnotesLineMap.get(lineNoString);
//				}
//				entity.put("addnotesAmt", StringUtil.parseString(resultMap.get("addnotesAmt")));
//				entity.put("lineNo", lineNoString);
//				entity.put("lineName", lineName);
				retList.add(entity);
			}

		retMap.put("retList", retList);
		retMap.put("totalRow", totalRow);
		retMap.put("totalPage", totalPage);
		retMap.put("pageSize", pageSize);
		retMap.put("curPage", curPage);
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "分页查询任务单成功！");

		return retMap;
	}

	private Map<String, String> getAddnotesLineMap() {
		List<LineTableDTO> addnoteLineListAll = addnoteLineInnerResource.qryAllInfo();

		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < addnoteLineListAll.size(); i++) {
			String lineNo = addnoteLineListAll.get(i).getLineNo() == null ? "" : addnoteLineListAll.get(i).getLineNo();
			String description = addnoteLineListAll.get(i).getLineName() == null ? "" : addnoteLineListAll.get(i).getLineName();
			map.put(lineNo, description);
		}
		return map;
	}

	@Override
	public Map<String, Object> qryExportTask(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String taskNosString = ((String) params.get("taskNos")).replaceAll("\"|\\[|\\]", "");

			String[] stringArr = taskNosString.split(",");
			String addnotesPlanNos = taskNosString.replaceAll(",", "-");
			List<Object> taskList = new ArrayList<Object>();

			for (String taskNo : stringArr) {
				TaskTableForName taskTableForName = taskInfoMapper.selectDetailByPrimaryKey(taskNo);
				if (taskTableForName == null) {
					retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
					return retMap;
				}
				TaskInfoDTO taskInfoDTO = TaskDTOConverter.INSTANCE.domain2dto(taskTableForName);

				List<Object> sortNoList = taskDetailMapper.getSortNoList(taskNo);
				if (sortNoList != null && sortNoList.size() > 0) {
					HashMap<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("taskNo", taskNo);
					for (Object sortNo : sortNoList) {
						if (sortNo != null) {
							paramMap.put("addnotesSortNo", StringUtil.objectToInt(sortNo));
						} else {
							paramMap.put("addnotesSortNo", null);
						}
						Map<String, Object> taskMap = new HashMap<String, Object>();
						List<TaskDetail> taskDetailTableList = taskDetailMapper.selectDetailWithSortNo(paramMap);
						if (taskDetailTableList != null && taskDetailTableList.size() > 0) {
							// todo 报错注释
//							taskMap.put("sortNo", taskDetailTableList.get(0).getSort());
//							taskMap.put("orgNo", taskDetailTableList.get(0).getOrgNo());
//							taskMap.put("orgName", taskDetailTableList.get(0).getOrgName());
//							taskMap.put("orgAddress", taskDetailTableList.get(0).getAddress());
//
//							for (TaskDetail taskDetail : taskDetailTableList) {
//								taskMap.put("customerNo", taskDetail.getCustomerNo());
//								taskMap.put("addnotesAmount", taskDetail.getAddnotesAmount());
//							}
						}
						taskMap.put("planFinishDate", taskInfoDTO.getPlanFinishDate());
						taskMap.put("addnotesPlanNo", taskInfoDTO.getAddnotesPlanNo());
						taskMap.put("centerName", taskInfoDTO.getClrCenterName());
						taskMap.put("lineName", taskInfoDTO.getLineName());
						taskMap.put("lineNo", taskTableForName.getLineNo());
//						taskMap.put("carNumber", taskTableForName.getCarNumber());
						taskMap.put("opNo1", taskInfoDTO.getOpNo1());
						taskMap.put("opNo2", taskInfoDTO.getOpNo2());
						taskMap.put("taskNo", taskNo);
						taskMap.put("clrCenterNo", taskTableForName.getClrCenterNo());
						taskMap.put("planDistance", taskTableForName.getPlanDistance());
						taskMap.put("planTimeCost", taskTableForName.getPlanTimeCost());
						taskList.add(taskMap);
					}
				}
			}

			int curRow = 0;
			for (int i = 0; i < taskList.size(); i++) {
				curRow++;
			}

			retMap.put("curRow", curRow);
			retMap.put("addnotesPlanNos", addnotesPlanNos);
			retMap.put("taskList", taskList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询加钞任务导出信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询加钞任务导出信息异常!");
			log.error("qryExportDispatch Fail: ", e);
		}
		return retMap;
	}

	/**
	 * 查询当前用户当天可执行的任务明细
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> qryOwnTaskDetail(String taskNo) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		TaskInfo taskInfo = taskInfoMapper.selectOwnTaskDetail(taskNo);
		Map<String,Object> lineMap = taskLineMapper.getLineByTaskNo(taskNo);
		if(lineMap!=null){
			taskInfo.setLineNo(StringUtil.parseString(lineMap.get("LINE_NO")));
			taskInfo.setLineName(StringUtil.parseString(lineMap.get("LINE_NAME")));
		}
		if (taskInfo == null) {
			retMap.put("retCode", RetCodeEnum.RESULT_EMPTY.getCode());
			retMap.put("retMsg", "查询失败，结果为空！");
			return retMap;
		}
		List<Object> taskList = new ArrayList<Object>();
		List<TaskDetail> taskDetailList = qryTaskDetailList(taskNo);
		List<TaskEntityPO> taskEntityPOList = qryEntityNoLists(taskNo);

		taskInfo.setTaskDetailList(taskDetailList);
		taskInfo.setTaskEntityPOList(taskEntityPOList);
		taskList.add(taskInfo);

		retMap.put("retList", taskList);
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "查询成功！");

		return retMap;
	}

	/**
	 * 根据线路查询任务单明细
	 *
	 * @return 结果
	 */
	@Override
	public Map<String, Object> qryTaskByLineNo(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		String taskType = StringUtil.parseString(paramMap.get("taskType"));
		List<String> taskTypeList = null;
		if (taskType != "") {
			String[] taskType1 = taskType.split(",");
			taskTypeList = Arrays.asList(taskType1);
		}
		paramMap.put("taskTypeList", taskTypeList);
		List<TaskInfo> taskInfoList = taskInfoMapper.qryTaskByLineNo(paramMap);
		List<Object> taskList = new ArrayList<Object>();
		if (taskInfoList.size() == 0) {
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "结果为空！");
			retMap.put("retList", taskList);
			return retMap;
		}

		for (TaskInfo taskInfo : taskInfoList) {
			String taskNo = taskInfo.getTaskNo();
			List<TaskDetail> taskDetailList = qryTaskDetailList(taskNo);
			List<TaskEntityPO> taskEntityPOList = qryEntityNoLists(taskNo);

			taskInfo.setTaskDetailList(taskDetailList);
			taskInfo.setTaskEntityPOList(taskEntityPOList);
			taskList.add(taskInfo);
		}

		retMap.put("retList", taskList);
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "查询成功！");

		return retMap;
	}

	@Override
	public String qryTaskByCustomerNo(Map<String, Object> paramMap) {
		String taskNo = "";
		try {
			taskNo = taskInfoMapper.qryTaskByCustomerNo(paramMap);

		} catch (Exception e) {
			return null;
		}
		return taskNo;
	}

	/**
	 * 根据任务单编号查询金额明细
	 * @param taskNo 任务编号
	 * @return
	 */
	@Override
	public List<TaskDetail> qryTaskDetailList(String taskNo) {
		List<TaskDetail> taskDetailList = taskDetailMapper.getDetailList(taskNo);
		List<TaskDetail> taskDetailList1 = new ArrayList<>();
		for(TaskDetail taskDetail : taskDetailList){
			String detailId = taskDetail.getId();
			List<TaskDetailPropertyDO> taskDetailPropertyDOList = taskDetailPropertyMapper.selectByDetailId(detailId);
			List<TaskDetailPropertyDO> taskDetailPropertyDOList1 = new ArrayList<TaskDetailPropertyDO>();

			for (TaskDetailPropertyDO taskDetailPropertyDO : taskDetailPropertyDOList) {
				TaskDetailPropertyDO taskDetailPropertyDO1 = new TaskDetailPropertyDO();
				taskDetailPropertyDO1.setId(taskDetailPropertyDO.getId());
				taskDetailPropertyDO1.setDetailId(taskDetailPropertyDO.getDetailId());
				taskDetailPropertyDO1.setKey(taskDetailPropertyDO.getKey());
				taskDetailPropertyDO1.setValue(taskDetailPropertyDO.getValue());
				taskDetailPropertyDO1.setTaskNo(taskDetailPropertyDO.getTaskNo());
				taskDetailPropertyDO1.setName(taskDetailPropertyDO.getName());
				taskDetailPropertyDOList1.add(taskDetailPropertyDO1);
			}
			taskDetail.setDetailList(taskDetailPropertyDOList1);
			taskDetailList1.add(taskDetail);
		}
		return taskDetailList1;
	}

	/**
	 * 根据任务单编号查询箱子及箱子金额明细
	 * @param taskNo 任务编号
	 * @return
	 */
	@Override
	public List<TaskEntityPO> qryEntityNoLists(String taskNo) {
		List<TaskEntityPO> conList = new ArrayList<TaskEntityPO>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("taskNo", taskNo);
		//判断任务单类型，如果是atm加钞任务单，则不查询direction为1（回收的箱子）的明细
		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
		if (taskInfo.getTaskType() == 1) {
			paraMap.put("direction", 1);
		}
		List<TaskEntityPO> containerNoList = taskEntityMapper.selectContainerNoList(paraMap);
		for (TaskEntityPO containerNoList1 : containerNoList) {
			//查询容器类型
			String containerNo = containerNoList1.getEntityNo();
			TaskEntityPO containerNoList2 = new TaskEntityPO();
			containerNoList2.setId(containerNoList1.getId());
			containerNoList2.setTaskNo(containerNoList1.getTaskNo());
			containerNoList2.setEntityNo(containerNoList1.getEntityNo());
			containerNoList2.setProductNo(containerNoList1.getProductNo());
			containerNoList2.setAmount(containerNoList1.getAmount());
			containerNoList2.setParentEntity(containerNoList1.getParentEntity());
			containerNoList2.setStatus(containerNoList1.getStatus());
			containerNoList2.setDirection(containerNoList1.getDirection());
			containerNoList2.setNote(containerNoList1.getNote());
			containerNoList2.setDepositType(containerNoList1.getDepositType());

//				任务单类型为2、9的钞处解现单，金额具体到每个箱子
//			if (taskInfo.getTaskType() == 2 ||taskInfo.getTaskType() == 9) {
				String id = containerNoList1.getId();
				List<TaskEntityDetailPO> currencyTypeLists = taskEntityDetailMapper.selectCurrencyList(id);
				if(currencyTypeLists!=null){
					List<TaskEntityDetailPO> currencyTypeList = new ArrayList<TaskEntityDetailPO>();
					for (TaskEntityDetailPO currencyTypeList1 : currencyTypeLists) {
						TaskEntityDetailPO currencyTypeList2 = new TaskEntityDetailPO();
						currencyTypeList2.setId(currencyTypeList1.getId());
						currencyTypeList2.setTaskNo(currencyTypeList1.getTaskNo());
						currencyTypeList2.setKey(currencyTypeList1.getKey());
						currencyTypeList2.setValue(currencyTypeList1.getValue());
						currencyTypeList2.setName(currencyTypeList1.getName());
						currencyTypeList.add(currencyTypeList2);
					}
					containerNoList2.setTaskEntityDetailDTOList(currencyTypeList);
				}

//			}
			conList.add(containerNoList2);

		}
		return conList;
	}


	/**
	 * 根据容器号查询设备明细
	 */

	@Override
	public Map<String, Object> qryTaskDetailByContainerNo(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		//可以直接输入状态或者根据操作类型来查出当前状态，但是二者不可同时作为参数
		List<TaskInfo> taskInfoList = new ArrayList<>();
		if (paramMap.get("operateType") != null && paramMap.get("operateType") != "") {
			String operateType = StringUtil.parseString(paramMap.get("operateType"));
			paramMap.put("operateType", operateType);
			taskInfoList = taskInfoMapper.qryTaskDetailByContainerNo(paramMap);
		} else {
			//根据容器编号 容器类型 任务单状态 查出所有符合条件的任务单

			taskInfoList = taskInfoMapper.qryTaskDetailByContainerNo(paramMap);
		}


		if (taskInfoList.size() == 0) {
			retMap.put("retCode", RetCodeEnum.RESULT_EMPTY.getCode());
			retMap.put("retMsg", "查询失败，结果为空！");
			return retMap;
		}
		List<Object> taskList = new ArrayList<Object>();
		for (TaskInfo taskInfo : taskInfoList) {
			String taskNo = taskInfo.getTaskNo();
			Map<String,Object> lineMap = taskLineMapper.getLineByTaskNo(taskNo);
			if(lineMap!=null){
				taskInfo.setLineNo(StringUtil.parseString(lineMap.get("LINE_NO")));
				taskInfo.setLineName(StringUtil.parseString(lineMap.get("LINE_NAME")));
			}
//			TaskDetail taskDetail = qryCustomerNoLists(taskNo);
//			taskInfo.setTaskDetail(taskDetail);
			List<TaskDetail> taskDetailList = qryTaskDetailList(taskNo);
			List<TaskEntityPO> taskEntityPOList = qryEntityNoLists(taskNo);

			taskInfo.setTaskDetailList(taskDetailList);
			taskInfo.setTaskEntityPOList(taskEntityPOList);
			taskList.add(taskInfo);
		}

		retMap.put("retList", taskList);
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "查询成功！");

		return retMap;


	}

	/**
	 * 查询可执行线路
	 *
	 * @return 结果
	 */
	@Override
	public Map<String, Object> qryLineNoList(Map<String, Object> paramMap) {
		List<Map<String, Object>> lineNoList = new ArrayList<>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			int taskType = StringUtil.objectToInt(paramMap.get("taskType"));
			int containerType = StringUtil.objectToInt(paramMap.get("containerType"));
			String planFinishDate = StringUtil.parseString(paramMap.get("planFinishDate"));
			String operateType = StringUtil.parseString(paramMap.get("operateType"));
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			if (taskType != -1) {
				paramsMap.put("taskType", taskType);
			}
			if (!"".equals(planFinishDate)) {
				paramsMap.put("planFinishDate", planFinishDate);
			}
			if (containerType != -1) {
				paramsMap.put("containerType", containerType);
			}
			if (operateType != "" && operateType != null) {
				paramsMap.put("operateType", operateType);
			}

			lineNoList = taskInfoMapper.qryLineNoList(paramsMap);
			retMap.put("lineNoList", lineNoList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");
		} catch (Exception e) {
			log.error("查询线路失败", e);
			retMap.put("retCode", RetCodeEnum.FAIL.getCode());
			retMap.put("retMsg", "查询失败！");
		}

		return retMap;
	}

	/**
	 * 根据线路编号查询笼车明细（出库）
	 */
	@Override
	public Map<String, Object> qryShelfDetailByLineNo(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		String lineNoList = StringUtil.parseString(paramMap.get("lineNoList"));
		String[] lineNoLists = lineNoList.split(",");
		String productNo = StringUtil.parseString(paramMap.get("productNo"));
		int taskType = StringUtil.objectToInt(paramMap.get("taskType"));
		try {
			int status = 304;
			//int containerType = StatusEnum.ContainerType.UNDERSTANDING_BOX.getType();
			for (int i = 0; i < lineNoLists.length; i++) {
				Map<String, Object> paramsMap = new HashMap<>();
				String lineNo = StringUtil.parseString(lineNoLists[i]);
				List<String> taskNoList = lineWorkInnerResource.qryTaskNoByLineNo(lineNo);
				if (taskType == 1) {
					paramsMap.put("status", status);
					paramsMap.put("taskType", taskType);
					//paramsMap.put("lineNo", lineNo);
					paramsMap.put("taskNoList",taskNoList);
				} else if (taskType == 2) {
					paramsMap.put("status", status);
					paramsMap.put("taskType", taskType);
					//paramsMap.put("lineNo", lineNo);
					paramsMap.put("productNo",productNo);
					paramsMap.put("taskNoList",taskNoList);
					//paramsMap.put("containerType", containerType);
				}
				List<Map<String, Object>> shelfNoList = taskInfoMapper.qryShelfNoByLineNo(paramsMap);
				for (Map<String, Object> resultMap : shelfNoList) {
					Map<String, Object> parasMap = new HashMap<>();
					Map<String, Object> entity = new HashMap<String, Object>();
					entity.put("shelfNo", StringUtil.parseString(resultMap.get("shelfNo")));
					entity.put("lineNo", lineNo);
					String shelfNo = StringUtil.parseString(resultMap.get("shelfNo"));
					if (taskType == 1) {
						parasMap.put("status", status);
						parasMap.put("taskType", taskType);
						parasMap.put("shelfNo", shelfNo);
					} else if (taskType == 2) {
						parasMap.put("status", status);
						parasMap.put("taskType", taskType);
						parasMap.put("shelfNo", shelfNo);
						parasMap.put("productNo", productNo);
					}
					List<String> containerNoList = taskInfoMapper.qryShelfDetailByShelfNo(parasMap);
					entity.put("containerNoList", containerNoList);
					retList.add(entity);
				}
			}
			retMap.put("retList", retList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "加钞任务查询成功！");
		} catch (Exception e) {
			log.error("查询笼车明细失败", e);
		}

		return retMap;
	}

	/**
	 * 任务单导出
	 * 1.校验任务单类型是否正确
	 * 2.查询任务单信息
	 * 3.根据文件类型导出任务单
	 *
	 * @param exportInfo
	 * @return
	 */
	@Override
	public DTO exportAddnotesTaskExcel(HttpServletRequest request, HttpServletResponse response, ExportTaskExcelDTO exportInfo) throws Exception {
		List<String> taskNos = exportInfo.getTaskNos();
		String fileType = exportInfo.getFileType();


		//1.校验任务单类型是否正确
		File exportFile = null;
		List<String> taskNosOutOfType = taskInfoMapper.isTaskOutOfType(taskNos, StatusEnum.taskType.ADDNOTESTASK.getId());

		if (taskNosOutOfType.size() > 0) {
			StringBuffer sb = new StringBuffer();
			int taskLength = 0;
			for (String taskNo : taskNosOutOfType) {
				sb.append(taskNo).append(",");
				taskLength = taskNo.length();
			}

			sb = new StringBuffer(sb.substring(0, sb.length() - 1));
			log.error("任务单[{}]不是{}", sb.toString(), StatusEnum.taskType.ADDNOTESTASK.getName());
			DTO dto = new DTO(RetCodeEnum.FAIL);
			if (sb.length() > (taskLength * 2 + 1)) {
				dto.setRetMsg(sb.toString() + "...");
			} else {
				dto.setRetMsg("任务单[" + sb.toString() + "]不是" + StatusEnum.taskType.ADDNOTESTASK.getName());
			}
		}

		//生成文件
		String moduleFileFullName = FileUtil.getBasePath() + "//template//AddNotesPlanTmp.xls";
		log.debug("模板文件{}", moduleFileFullName);
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			fis = new FileInputStream(new File(moduleFileFullName));
			//生成任务单文件名称生成规则
			String fileName = null;
			for (int i = 1; ; i++) {
				fileName = String.format("AddnotesPlan_%s_%06d.xls", CalendarUtil.getSysTimeYMD(), i);
				if (new File(FileUtil.getBasePath() + "//dispatch", fileName).exists()) {
					if (i > 999998) {
						log.error("当日生成任务单文件达999999");
						DTO dto = new DTO(RetCodeEnum.FAIL);
						dto.setRetMsg("当日生成任务单文件达999999,无法生成更多");
						return dto;
					}
				} else {
					log.debug("生成任务单文件{}", fileName);
					break;
				}

			}


			wb = new HSSFWorkbook(fis);


			List<TaskDetailToExportDO> taskDetailToExportDOSAll = new ArrayList<>();
			for (String taskNo : taskNos) {
				//查询任务单信息
				TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
				if (StatusEnum.TaskStatus.UNASSIGNED.getId() == taskInfo.getStatus()) {
					DTO dto = new DTO(RetCodeEnum.FAIL);
					dto.setRetMsg("任务单[" + taskNo + "]未分配人员");
					return dto;
				}
				//查询任务单详情
				List<TaskDetailToExportDO> taskDetailToExportDOS = taskDetailMapper.getDetailToExportList(taskNo);
				createSeparateSheet(taskInfo, taskDetailToExportDOS, wb);
				taskDetailToExportDOSAll.addAll(taskDetailToExportDOS);
			}

			createTotalSheet(taskDetailToExportDOSAll, wb);

			if (null != wb) {
				//exportFile = new File(FileUtil.getBasePath() + "//dispatch", fileName);
				FileOutputStream output = null;
				try {
					output = new FileOutputStream(FileUtil.getBasePath() + "//dispatch//" + fileName);
					wb.write(output);
				} finally {
					if (null != output) {
						output.close();
					}
				}
			} else {
				DTO dto = new DTO(RetCodeEnum.FAIL);
				dto.setRetMsg("生成文件为空");
				return dto;
			}

			FileUtil.downLoadResponseFile(request, response, fileName, FileUtil.getBasePath() + "//dispatch//" + fileName, false, false);
		} finally {
			if (null != wb) {
				wb.close();
			}

			if (null != fis) {
				fis.close();
			}
		}

		//return null,因为前面传输文件已经发送response后就关闭了连接
		return null;
	}


	/**
	 * 插入加钞任务单的第一页汇总信息的excel页
	 *
	 * @param taskDetails
	 * @return
	 * @throws Exception
	 */
	private void createTotalSheet(List<TaskDetailToExportDO> taskDetails, HSSFWorkbook wb) throws Exception {

		HSSFSheet sheet = wb.getSheetAt(0);

		for (int i = 0; i < taskDetails.size(); ++i) {
			HSSFRow row = sheet.createRow(i + 2);
			TaskDetailToExportDO taskDetail = taskDetails.get(i);
			HSSFCell cell = row.createCell(0);
			// todo 报错注释
			cell.setCellValue(taskDetail.getProductNo());
			cell = row.createCell(1);
			cell.setCellValue(taskDetail.getDirection());
			cell = row.createCell(2);
			cell.setCellValue("更换钞箱");
			cell = row.createCell(3);
			cell.setCellValue(taskDetail.getAmount().toString());
			cell = row.createCell(4);
			if (null != taskDetail.getOpName1()) {
				cell.setCellValue(taskDetail.getOpName1());
			}
			cell = row.createCell(5);
			if (null != taskDetail.getOpName2()) {
				cell.setCellValue(taskDetail.getOpName2());
			}

		}
	}

	/**
	 * 按线路导出加钞任务单excel页
	 *
	 * @param taskInfo
	 * @param taskDetails
	 * @param wb
	 * @throws Exception
	 */
	private void createSeparateSheet(TaskInfo taskInfo, List<TaskDetailToExportDO> taskDetails, HSSFWorkbook wb) throws Exception {
		HSSFSheet sheet = wb.createSheet(taskInfo.getLineName());
		short rowhi = 450;
		double d = 6.0D;
		sheet.setColumnWidth(0, (int) (d * 256.0D));
		sheet.setColumnWidth(1, (int) (2.5D * d * 256.0D));
		sheet.setColumnWidth(2, (int) (7.5D * d * 256.0D));
		sheet.setColumnWidth(3, (int) (1.3D * d * 256.0D));
		sheet.setColumnWidth(4, (int) (1.7D * d * 256.0D));
		sheet.setColumnWidth(5, (int) (3.0D * d * 256.0D));
		sheet.setColumnWidth(6, (int) (3.0D * d * 256.0D));
		sheet.setColumnWidth(7, (int) (3.0D * d * 256.0D));
		sheet.setColumnWidth(8, (int) (3.0D * d * 256.0D));
		sheet.setColumnWidth(9, (int) (3.0D * d * 256.0D));
		sheet.setColumnWidth(10, (int) (3.0D * d * 256.0D));
		sheet.setColumnWidth(11, (int) (1.5D * d * 256.0D));
		sheet.setMargin((short) 2, 0.1D);
		sheet.setMargin((short) 3, 0.1D);
		sheet.setMargin((short) 0, 0.5D);
		sheet.setMargin((short) 1, 0.1D);
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setPaperSize((short) 9);
		printSetup.setLandscape(true);
		printSetup.setVResolution((short) 600);
		printSetup.setScale((short) 70);
		sheet.setRepeatingRows(new CellRangeAddress(2, 2, -1, -1));
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setWrapText(false);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		HSSFFont fontCL = wb.createFont();
		fontCL.setFontName("宋体");
		fontCL.setFontHeightInPoints((short) 16);
		HSSFCellStyle cellStyleCL = wb.createCellStyle();
		cellStyleCL.setFont(fontCL);
		cellStyleCL.setWrapText(true);
		cellStyleCL.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleCL.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleCL.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleCL.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleCL.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleCL.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		HSSFFont fontCL14 = wb.createFont();
		fontCL14.setFontName("宋体");
		fontCL14.setFontHeightInPoints((short) 14);
		HSSFCellStyle cellStyleCL14 = wb.createCellStyle();
		cellStyleCL14.setFont(fontCL14);
		cellStyleCL14.setWrapText(true);
		cellStyleCL14.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleCL14.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleCL14.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleCL14.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleCL14.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleCL14.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		HSSFFont font16 = wb.createFont();
		font16.setFontName("宋体");
		font16.setFontHeightInPoints((short) 16);
		HSSFCellStyle cellStyleLeft = wb.createCellStyle();
		cellStyleLeft.setFont(font16);
		cellStyleLeft.setWrapText(false);
		cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleLeft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		HSSFCellStyle cellStyleHeadCenter = wb.createCellStyle();
		HSSFFont fontbt = wb.createFont();
		fontbt.setFontName("宋体");
		fontbt.setFontHeightInPoints((short) 20);
		cellStyleHeadCenter.setFont(fontbt);
		cellStyleHeadCenter.setWrapText(true);
		cellStyleHeadCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleHeadCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFCellStyle cellStyleHead = wb.createCellStyle();
		HSSFFont fontbt2 = wb.createFont();
		fontbt2.setFontName("宋体");
		fontbt2.setFontHeightInPoints((short) 16);
		cellStyleHead.setFont(fontbt2);
		cellStyleHead.setWrapText(true);
		cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFCellStyle cellStyleHeadRight = wb.createCellStyle();
		HSSFFont fontbt3 = wb.createFont();
		fontbt3.setFontName("宋体");
		fontbt3.setFontHeightInPoints((short) 16);
		cellStyleHeadRight.setFont(fontbt3);
		cellStyleHeadRight.setWrapText(true);
		cellStyleHeadRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		cellStyleHeadRight.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 11);
		sheet.addMergedRegion(region1);
		CellRangeAddress region2 = new CellRangeAddress(1, 1, 1, 2);
		sheet.addMergedRegion(region2);
		CellRangeAddress region21 = new CellRangeAddress(1, 1, 3, 5);
		sheet.addMergedRegion(region21);
		CellRangeAddress region28 = new CellRangeAddress(1, 1, 6, 7);
		sheet.addMergedRegion(region28);
		CellRangeAddress region22 = new CellRangeAddress(1, 1, 10, 11);
		sheet.addMergedRegion(region22);
		HSSFRow row = sheet.createRow(0);
		row.setHeight(rowhi);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(cellStyleHeadCenter);
		cell.setCellValue("自动柜员机领钞、加钞通知书(本通知书加钞金额手工修改无效，一式3份记账员留存1份、加钞员1份、配钞1份)");
		row = sheet.createRow(1);
		row.setHeight(rowhi);
		cell = row.createCell(1);
		cell.setCellStyle(cellStyleHead);
		cell.setCellValue("加钞线路：" + taskInfo.getLineName());
		cell = row.createCell(3);
		cell.setCellStyle(cellStyleHead);
		cell.setCellValue("日期：" + taskInfo.getPlanFinishDate());
		cell = row.createCell(6);
		cell.setCellStyle(cellStyleHead);
		if (!StringUtil.isNullorEmpty(taskInfo.getOpName1()) && !StringUtil.isNullorEmpty(taskInfo.getOpName2())) {
			cell.setCellValue("加钞人员：" + taskInfo.getOpName1() + "、" + taskInfo.getOpName2());
		} else if (StringUtil.isNullorEmpty(taskInfo.getOpName1()) && !StringUtil.isNullorEmpty(taskInfo.getOpName2())) {
			cell.setCellValue("加钞人员：" + taskInfo.getOpName2());
		} else if (!StringUtil.isNullorEmpty(taskInfo.getOpName1()) && StringUtil.isNullorEmpty(taskInfo.getOpName2())) {
			cell.setCellValue("加钞人员：" + taskInfo.getOpName1());
		} else {
			cell.setCellValue("加钞人员：");
		}

		cell = row.createCell(8);
		cell.setCellStyle(cellStyleHeadRight);
		cell.setCellValue("新开机：");
		cell = row.createCell(9);
		cell.setCellStyle(cellStyleHeadRight);
		cell = row.createCell(10);
		cell.setCellStyle(cellStyleHead);
		cell.setCellValue("停机：");
		row = sheet.createRow(2);
		row.setHeight(rowhi);
		cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(7);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(9);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(10);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(11);
		cell.setCellStyle(cellStyle);
		row = sheet.createRow(2);
		row.setHeight((short) (rowhi * 2));
		cell = row.createCell(0);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("序号");
		cell = row.createCell(1);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("ATM 机号");
		cell = row.createCell(2);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("地           址");
		cell = row.createCell(3);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("型号");
		cell = row.createCell(4);
		cell.setCellStyle(cellStyleCL14);
		cell.setCellValue("加钞金额(万元)");
		cell = row.createCell(5);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("重点提示");
		cell = row.createCell(6);
		cell.setCellStyle(cellStyleCL14);
		cell.setCellValue("ATM现金清点金额或加钞袋编号");
		cell = row.createCell(7);
		cell.setCellStyle(cellStyleCL14);
		cell.setCellValue("主机余额或加钞袋锁条号");
		cell = row.createCell(8);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("核对（卡钞）结果");
		cell = row.createCell(9);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("回钞箱(袋)编号");
		cell = row.createCell(10);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("回收箱(袋)锁条号");
		cell = row.createCell(11);
		cell.setCellStyle(cellStyleCL);
		cell.setCellValue("备注");

		for (int i = 0; i < taskDetails.size(); ++i) {
			log.debug("line" + i);
			TaskDetailToExportDO taskDetail = taskDetails.get(i);
			row = sheet.createRow(3 + i);
			row.setHeight(rowhi);
			cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue((double) (i + 1));
			cell = row.createCell(1);
			// todo 报错注释
			cell.setCellStyle(cellStyle);
			cell.setCellValue(taskDetail.getProductNo());
			cell = row.createCell(2);
			cell.setCellStyle(cellStyleLeft);
			cell.setCellValue(taskDetail.getDirection());
			cell = row.createCell(3);
//			cell.setCellStyle(cellStyleLeft);
//			String devVendor = taskDetail.getDevVendorName();
//			if (devVendor != null && devVendor.length() > 4) {
//				devVendor = devVendor.substring(0, 4);
//			}

//			cell.setCellValue(devVendor);
//			cell = row.createCell(4);
			cell.setCellStyle(cellStyle);
			if (taskDetail.getAmount() != null) {
				cell.setCellValue((double) (taskDetail.getAmount().intValue() / 10000));
			}

			cell = row.createCell(5);
			cell.setCellStyle(cellStyle);
			String keyEventDetail = taskDetail.getKeyEventDetail();
			if (keyEventDetail != null && keyEventDetail.contains("超过最大加钞周期")) {
				cell.setCellValue("超期");
			}
			cell = row.createCell(6);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(7);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(8);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(9);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(10);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(11);
			cell.setCellStyle(cellStyle);
		}

		row = sheet.createRow(3 + taskDetails.size());
		row.setHeight(rowhi);
		cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("合计");
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		String sumstring = "SUM(E4:E" + (3 + taskDetails.size()) + ")";
		cell.setCellFormula(sumstring);
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(7);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(9);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(10);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(11);
		cell.setCellStyle(cellStyle);
		row = sheet.createRow(4 + taskDetails.size());
		row.setHeight(rowhi);
		cell = row.createCell(0);
		cell.setCellStyle(cellStyleHead);
		cell.setCellValue("加钞线路：" + taskInfo.getLineName());
		cell = row.createCell(4);
		cell.setCellValue("审核: ");
		cell.setCellStyle(cellStyleHead);
		cell = row.createCell(6);
		cell.setCellStyle(cellStyleHead);
		cell.setCellValue("帐务处理员: ");
		cell = row.createCell(8);
		cell.setCellStyle(cellStyleHead);
		if (null == taskInfo.getOpName1()) {
			taskInfo.setOpName1("");
		}
		if (null == taskInfo.getOpName2()) {
			taskInfo.setOpName2("");
		}
		if (!StringUtil.isNullorEmpty(taskInfo.getOpName1()) && !StringUtil.isNullorEmpty(taskInfo.getOpName2())) {
			cell.setCellValue("加钞人员：" + taskInfo.getOpName1() + "、" + taskInfo.getOpName2());
		} else if (StringUtil.isNullorEmpty(taskInfo.getOpName1()) && !StringUtil.isNullorEmpty(taskInfo.getOpName2())) {
			cell.setCellValue("加钞人员：" + taskInfo.getOpName2());
		} else if (!StringUtil.isNullorEmpty(taskInfo.getOpName1()) && StringUtil.isNullorEmpty(taskInfo.getOpName2())) {
			cell.setCellValue("加钞人员：" + taskInfo.getOpName1());
		} else {
			cell.setCellValue("加钞人员：");
		}
		CellRangeAddress region23 = new CellRangeAddress(4 + taskDetails.size(), 4 + taskDetails.size(), 0, 3);
		sheet.addMergedRegion(region23);
		CellRangeAddress region24 = new CellRangeAddress(4 + taskDetails.size(), 4 + taskDetails.size(), 4, 5);
		sheet.addMergedRegion(region24);
		CellRangeAddress region25 = new CellRangeAddress(4 + taskDetails.size(), 4 + taskDetails.size(), 6, 7);
		sheet.addMergedRegion(region25);
		CellRangeAddress region26 = new CellRangeAddress(4 + taskDetails.size(), 4 + taskDetails.size(), 8, 11);
		sheet.addMergedRegion(region26);
	}

	@Override
	public DTO taskOutTime(Map<String, Object> paramMap) {
		taskInfoMapper.taksTimeOut(paramMap);
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO taskSplit(TaskSplitDTO taskSplitDTO) {
		//验证该任务单状态是否可以拆分
//		String operateType = taskSplitDTO.getOperateType();
		String operateType = "Split";
		int nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		Integer statusConvertMatch = serviceInnerResource.isStatusConvertMatch(taskSplitDTO.getTaskType(), taskSplitDTO.getStatus(), operateType);
		if (statusConvertMatch < 1) {
			return new DTO(RetCodeEnum.FAIL.getCode(), "当前任务单无法拆分，请检查状态");
		}

		String planFinishDate = taskSplitDTO.getPlanFinishDate();
		String theYearMonth = planFinishDate.substring(0, 7);
		String theDay = planFinishDate.substring(8);
		String customerNo = taskSplitDTO.getCustomerNo();

		//查询服务对象基本信息
		Map<String, String> customerMap = sysOrgInnerResource.qryCenterByNo(customerNo);
		String customerName = StringUtil.parseString(customerMap.get("NAME"));
		String customerAddress = StringUtil.parseString(customerMap.get("ADDRESS"));
		String lng = StringUtil.parseString(customerMap.get("X"));
		String lat = StringUtil.parseString(customerMap.get("Y"));

		//拆分子任务单生成
		List<TaskSplitContainerListDTO> taskSplitContainerLists = taskSplitDTO.getTaskSplitContainerLists();
		for (TaskSplitContainerListDTO taskSplitContainerList : taskSplitContainerLists) {
			TaskInfo taskInfoPO = new TaskInfo();
			int taskType = taskSplitDTO.getTaskType();
			String taskNo = taskInnerResource.getNextLogicId(taskSplitDTO.getClrCenterNo(), taskType);
			taskInfoPO.setTaskNo(taskNo);
			taskInfoPO.setTaskType(taskSplitDTO.getTaskType());
			taskInfoPO.setPlanFinishDate(taskSplitDTO.getPlanFinishDate());
			//默认修改之后的任务单状态和原任务单状态保持一致。
			taskInfoPO.setStatus(taskSplitDTO.getStatus());
			//taskInfoPO.setLineNo(taskSplitDTO.getLineNo());
			taskInfoPO.setClrCenterNo(taskSplitDTO.getClrCenterNo());
			taskInfoPO.setNote(taskSplitDTO.getNote());
			taskInfoPO.setCreateOpNo(ServletRequestUtil.getUsername());
			taskInfoPO.setCreateTime(CalendarUtil.getSysTimeYMDHMS());
			taskInfoPO.setCustomerNo(customerNo);
			taskInfoPO.setCustomerName(taskSplitDTO.getCustomerName());
			taskInfoPO.setCustomerType(taskSplitDTO.getCustomerType());
			//插入task_table
			int insertTask = taskInfoMapper.insertSelective(taskInfoPO);
			if (insertTask < 1) {
				log.error("插入订单任务表task_table失败");
				throw new RuntimeException("插入订单任务表task_table失败");
			}


			//插入产品明细表task_detail_table（金额暂未做处理）
			HashMap<String, Object> parabMap = new HashMap<String, Object>();
			parabMap.put("id", java.util.UUID.randomUUID().toString().replace("-", ""));
			parabMap.put("taskNo", taskNo);
			parabMap.put("amount",0);
			int b = taskDetailMapper.insertSelectiveByMap(parabMap);
			if (b == -1) {
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
			//taskLineMap.put("earlyTime", earlyTime);
			//taskLineMap.put("latestTime", latestTime);
			taskLineInnerResource.insertSelectiveByMap(taskLineMap);


			//插入task_detail_table和task_detail_property_table
			List<TaskSplitContainerDTO> taskSplitContainers1 = taskSplitContainerList.getTaskSplitContainers();
			//插入task_entity_table 和 task_entity_detail
			for (int i = 0; i < taskSplitContainers1.size(); i++) {
				TaskSplitContainerDTO taskEntityDTO = (TaskSplitContainerDTO) taskSplitContainers1.get(i);
				//插入task_entity_table
				HashMap<String, Object> paracMap = new HashMap<String, Object>();
				String id = java.util.UUID.randomUUID().toString().replace("-", "");
				String entityNo = StringUtil.parseString(taskEntityDTO.getEntityNo());
				String productNo = StringUtil.parseString(taskEntityDTO.getProductNo());
				String parentEntity = StringUtil.parseString(taskEntityDTO.getParentEntity());
				BigDecimal amount = StringUtil.objBigDecimal(taskEntityDTO.getAmount());

				Integer depositType = StringUtil.objectToInt(taskEntityDTO.getDepositType());
				String note1 = StringUtil.parseString(taskEntityDTO.getNote());

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
				int f = taskEntityMapper.insertSelectiveByMap(paracMap);
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
					int e = taskEntityDetailMapper.insertSelectiveByMap(paradMap);
					if (e == -1) {
						throw new RuntimeException("失败");
					}
				}
			}
		}

		//原任务单状态置为 已拆分-206
		TaskInfo taskInfoPO = new TaskInfo();
		taskInfoPO.setTaskNo(taskSplitDTO.getTaskNo());
		taskInfoPO.setStatus(taskSplitDTO.getNextStatus());
		int updateTask = taskInfoMapper.updateByPrimaryKeySelective(taskInfoPO);
		if (updateTask < 1) {
			log.error("更新订单任务表task_table失败");
			throw new RuntimeException("更新订单任务表task_table失败");
		}
		return new DTO(RetCodeEnum.SUCCEED.getCode(), "网点寄库&解现任务单拆分成功！");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO taskMerge(List<String> taskNoList) {
		//验证 所有子任务单状态是否能进行合并
		for (String taskNo : taskNoList) {
			TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
			if (serviceInnerResource.isStatusConvertMatch(taskInfo.getTaskType(), taskInfo.getStatus(), "Merge") < 1) {
				return new DTO(RetCodeEnum.FAIL.getCode(), "任务单" + taskNo + "无法合并，请检查状态");
			}
		}

		//校验 需要合并的任务单必须是相同的网点和计划完成日期和状态
		String customerNo = "";
		String planFinishDate = "";
		String clrCenterNo = "";
		Integer status = null;
		Integer taskType = null;
		for (int i = 0; i < taskNoList.size(); i++) {
			String taskNo = taskNoList.get(i);
			TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
			taskType = taskInfo.getTaskType();
			clrCenterNo = taskInfo.getClrCenterNo();
			if (i == 0) {
				planFinishDate = taskInfo.getPlanFinishDate();
				customerNo = taskInfo.getCustomerNo();
				status = taskInfo.getStatus();
			} else if (!planFinishDate.equals(taskInfo.getPlanFinishDate()) || !customerNo.equals(taskInfo.getCustomerNo()) || !status.equals(taskInfo.getStatus())) {
				return new DTO(RetCodeEnum.FAIL.getCode(), "合并失败，请确保所选任务单具有相同的网点和寄库日期以及状态");
			}
		}

		//查询服务对象基本信息
		Map<String, String> customerMap = sysOrgInnerResource.qryCenterByNo(customerNo);
		String customerName = StringUtil.parseString(customerMap.get("NAME"));
		String customerAddress = StringUtil.parseString(customerMap.get("ADDRESS"));
		String lng = StringUtil.parseString(customerMap.get("X"));
		String lat = StringUtil.parseString(customerMap.get("Y"));

		//生成新的任务单
		String taskNoNew = taskInnerResource.getNextLogicId(clrCenterNo, taskType);
		for (int i = 0; i < taskNoList.size(); i++) {
			String taskNo = taskNoList.get(i);
			TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
			//订单任务表task_table和订单任务明细表task_detail_table只在遍历第一个子任务单的时候插入一条数据
			if (i == 0) {
				TaskInfo taskInfoPO = taskInfoMapper.selectByPrimaryKey(taskNo);
				taskInfoPO.setTaskNo(taskNoNew);
				//默认修改之后的任务单状态和原任务单保持一致
				taskInfoPO.setCustomerNo(customerNo);
				taskInfoPO.setCustomerName(customerName);
				taskInfoPO.setCustomerType(taskInfo.getCustomerType());
				taskInfoPO.setClrCenterNo(clrCenterNo);
				taskInfoPO.setTaskType(taskType);
				taskInfoPO.setPlanFinishDate(planFinishDate);
				taskInfoPO.setStatus(status);
				taskInfoPO.setCreateOpNo(ServletRequestUtil.getUsername());
				taskInfoPO.setCreateTime(CalendarUtil.getSysTimeYMDHMS());
				//插入task_table
				int insertTask = taskInfoMapper.insertSelective(taskInfoPO);
				if (insertTask < 1) {
					log.error("插入订单任务表task_table失败");
					throw new RuntimeException("插入订单任务表task_table失败");
				}

				//插入task_line
				//根据customerNo查询线路排班信息
				String theYearMonth = planFinishDate.substring(0, 7);
				String theDay = planFinishDate.substring(8);
				HashMap<String, Object> lineMap = new HashMap<String, Object>();
				lineMap.put("customerNo", customerNo);
				lineMap.put("theYearMonth", theYearMonth);
				lineMap.put("theDay", theDay);
				LineScheduleDTO lineScheduleDTO = lineScheduleInnerResource.selectByMap(lineMap);
				HashMap<String, Object> taskLineMap = new HashMap<String, Object>();
				taskLineMap.put("lineWorkId", lineScheduleDTO.getLineWorkId());
				taskLineMap.put("taskNo", taskNoNew);
				taskLineMap.put("sortNo", lineScheduleDTO.getSortNo());
				taskLineMap.put("address", customerAddress);
				taskLineMap.put("lng", lng);
				taskLineMap.put("lat", lat);
				//todo 最早到达时间与最迟到达时间应该从线路排班中取得
				//taskLineMap.put("earlyTime", earlyTime);
				//taskLineMap.put("latestTime", latestTime);
				taskLineMapper.insertSelectiveByMap(taskLineMap);

				//网点寄库&解现任务所有明细信息
				List<TaskDetail> taskDetails = taskDetailMapper.qryByTaskNo(taskNo);
				for (TaskDetail taskDetail : taskDetails ) {
					String oldDetailId = taskDetail.getId();
					List<TaskDetailPropertyDO> taskDetailPropertyDOS = taskDetailPropertyMapper.selectByDetailId(oldDetailId);
					String newDetailId = UUID.randomUUID().toString().replace("-", "");
					taskDetail.setId(newDetailId);
					taskDetail.setTaskNo(taskNoNew);
					//插入task_detail表
					int insertTaskDetail = taskDetailMapper.insertSelective(taskDetail);
					if (insertTaskDetail < 1) {
						log.error("插入订单任务明细表task_detail_table失败");
						throw new RuntimeException("插入订单任务明细表task_detail_table失败");
					}
					for (TaskDetailPropertyDO taskDetailPropertyDO : taskDetailPropertyDOS  ) {
						taskDetailPropertyDO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
						taskDetailPropertyDO.setDetailId(newDetailId);
						//插入task_detail_property表
						int i1 = taskDetailPropertyMapper.insertSelective(taskDetailPropertyDO);
						if (i1 < 1) {
							log.error("插入订单任务明细属性表task_detail_property失败");
							throw new RuntimeException("插入订单任务明细属性表task_detail_property失败");
						}
					}
				}

			}

			//删除原来的任务线路信息
			taskLineMapper.deleteByTaskNo(taskNo);

			//插入任务物品信息
			List<TaskEntityPO> taskEntityPOs = taskEntityMapper.selectByTaskNo(taskNo);
			for (TaskEntityPO taskEntityPO : taskEntityPOs) {
				String oldTaskEntityId = taskEntityPO.getId();
				List<TaskEntityDetailPO> taskEntityDetailPOS = taskEntityDetailMapper.selectByPrimaryKey(oldTaskEntityId);
				String newTaskEntityId = UUID.randomUUID().toString().replace("-", "");
				taskEntityPO.setId(newTaskEntityId);
				taskEntityPO.setTaskNo(taskNoNew);
				//插入task_entity_table表
				int insertTaskEntity = taskEntityMapper.insertSelective(taskEntityPO);
				if (insertTaskEntity < 1) {
					log.error("插入任务物品表task_entity_table失败");
					throw new RuntimeException("插入任务物品表task_entity_table失败");
				}
				for (TaskEntityDetailPO taskEntityDetailPO : taskEntityDetailPOS) {
					taskEntityDetailPO.setId(newTaskEntityId);
					taskEntityDetailPO.setTaskNo(taskNoNew);
					int i1 = taskEntityDetailMapper.insertSelective(taskEntityDetailPO);
					if (i1 < 1) {
						log.error("插入任务物品详细表task_entity_detail失败");
						throw new RuntimeException("插入任务物品详细表task_entity_detail失败");
					}
				}
			}

		}

		//将原任务单状态置为 已合并-209
		for (String taskNo : taskNoList) {
			TaskInfo taskInfoPO = new TaskInfo();
			taskInfoPO.setTaskNo(taskNo);
			taskInfoPO.setStatus(209);
			int updateTask = taskInfoMapper.updateByPrimaryKeySelective(taskInfoPO);
			if (updateTask < 1) {
				log.error("更新订单任务表task_table失败");
				throw new RuntimeException("更新订单任务表task_table失败");
			}
		}

		return new DTO(RetCodeEnum.SUCCEED.getCode(), "网点寄库&解现任务单合并成功！");
	}

	@Override
	public Map<String, Object> qryBankNote(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			if (StringUtil.parseString(paramMap.get("startDate")).equals("") || StringUtil.parseString(paramMap.get("startDate")) == null) {
				paramMap.put("startDate", CalendarUtil.getSysTimeYMD());
			}
			if (StringUtil.parseString(paramMap.get("endDate")).equals("") || StringUtil.parseString(paramMap.get("endtDate")) == null) {
				paramMap.put("endDate", CalendarUtil.getLaterDate());
			}
			List<Map<String, Object>> bankNoteList = taskInfoMapper.selectBankNote(paramMap);
			List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : bankNoteList) {
				Map<String, Object> entity = new HashMap<String, Object>();
				String taskNo = StringUtil.parseString(map.get("TASK_NO"));
				Integer taskType = StringUtil.objectToInt(map.get("TASK_TYPE"));
				int containerCount = taskEntityMapper.selectContainerCount(taskNo);
				entity.put("taskNo", StringUtil.parseString(map.get("TASK_NO")));
				entity.put("taskType", StringUtil.parseString(map.get("TASK_TYPE")));
				entity.put("shelfNo", StringUtil.parseString(map.get("SHELF_NO")));
				entity.put("lineName", StringUtil.parseString(map.get("LINE_NAME")));
				entity.put("containerCount", containerCount);
				if (taskType != 1) {
					entity.put("customerNo", StringUtil.parseString(map.get("CUSTOMER_NO")));
					entity.put("customerName", StringUtil.parseString(map.get("NAME")));
				}
				allList.add(entity);
			}
			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
			//加钞任务单涉及到一个任务单对应多个服务对象，查询结果会有重复现象，在此去重
			for (int i = 0; i < allList.size(); i++) {
				if (i == 0){
					retList.add(allList.get(i));
					continue;
				}
				Map m1 = allList.get(i);
				if ( ! retList.contains(m1)){
					retList.add(m1);
				}
			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "钞处中心待执行任务信息查询成功！");
			retMap.put("retList", retList);
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "钞处中心待执行任务信息查询异常!");
		}
		return retMap;
	}

	/**
	 * 任务单基础信息修改
	 * @param taskInfoDTO 需要修改的任务单信息
	 * @return
	 */
	@Override
	public DTO updateTaskInfo(TaskInfoDTO taskInfoDTO){
		DTO dto = new DTO(RetCodeEnum.FAIL);

		String operateType = taskInfoDTO.getOperateType();
		String taskNo = taskInfoDTO.getTaskNo();
		String operateTypeName = serviceInnerResource.qryNameByOperateType(operateType);

		Set set = new LinkedHashSet();
		boolean convert = serviceInnerResource.isConvert(taskNo, operateType, set);
		if (convert == false) {
			dto.setRetCode(RetCodeEnum.FAIL.getCode());
			dto.setRetMsg("任务单当前状态无法执行【"+operateTypeName+"】操作");
			return dto;
		}

		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		TaskInfo taskInfo = TaskDTOConverter.INSTANCE.domain2dto(taskInfoDTO);
		TaskInfo taskInfo1 = taskInfoMapper.selectByPrimaryKey(taskInfo.getTaskNo());
		if (taskInfo1 == null){
			dto.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}

		taskInfo.setStatus(nextStatus);
		int y = taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
		if(y < 1){
			dto.setRetMsg(operateTypeName+"失败");
			return dto;
		}


		String lineWorkId = taskInfoDTO.getLineWorkId();
		if (lineWorkId != null){
			Map<String, Object> lineWorkMap = new HashMap<>();
			lineWorkMap.put("lineWorkId",lineWorkId);
			lineWorkMap.put("taskNo",taskNo);
			int z = taskLineInnerResource.updateSelectiveByMap(lineWorkMap);
			if(z < 1){
				dto.setRetMsg(operateTypeName+"失败");
				return dto;
			}
		}

		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg(operateTypeName+"成功！");
		//跳过弱节点的执行
		if (set.size() != 0) {
			dto.setRetMsg(operateTypeName+"执行成功！但跳过"+set.toString()+"操作");
		}

		return dto;
	}

	/**
	 * 删除任务单
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	@Override
	public DTO delTask(String taskNo) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(), "删除失败");
		TaskInfo taskInfo1 = taskInfoMapper.selectByPrimaryKey(taskNo);
		if (taskInfo1 == null){
			dto.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}
		int a = taskDetailPropertyInnerResource.deleteByTaskNo(taskNo);//detailproperty
		int b = taskDetailMapper.deleteByTaskNo(taskNo);//detail
		int c = taskEntityDetailMapper.deleteByTaskNo(taskNo);//TASK_ENTITY_DETAIL
		int d = taskEntityMapper.deleteByTaskNo(taskNo);//TASK_ENTITY_TABLE
//		int e = taskInOutInnerResource.deleteByTaskNo(taskNo);//TASK_IN_OUT
		int f = taskShelfUserInnerResource.deleteByTaskNo(taskNo);//TASK_SHELF_USER
		int g = taskLineInnerResource.deleteByTaskNo(taskNo);
		int h = taskInfoMapper.deleteByPrimaryKey(taskNo);
		if (a == -1 || b == -1 || c == -1 || d == -1  || f == -1 || g == -1 || h == -1) {
			throw new RuntimeException("失败");
		}
		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("删除成功");
		return dto;
	}

	/**
	 * 批量修改任务单状态
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO affirmOutBatchNotesReceipt(TaskInfoDTO taskInfoDTO){
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String operateType = taskInfoDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		Integer taskType = taskInfoDTO.getTaskType();

		//根据任务编号查询任务单
		List<String> list = taskInfoDTO.getList();
		for (String taskNo : list) {
			TaskInfo taskInfo1 = taskInfoMapper.selectByPrimaryKey(taskNo);
			if (taskInfo1 == null){
				dto.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}

			//判断任务单是否能进行此操作
			Set set = new LinkedHashSet();
			boolean convert = serviceInnerResource.isConvert(taskNo, operateType, set);
			if (convert == false) {
				dto.setRetCode(RetCodeEnum.FAIL.getCode());
				dto.setRetMsg("任务单当前状态无法执行【"+operateType+"】操作");
				return dto;
			}
		}

		//更新任务单状态
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setStatus(nextStatus);
		taskInfo.setModeOp(ServletRequestUtil.getUsername());
		taskInfo.setModeTime(CalendarUtil.getSysTimeYMDHMS());
		for (String taskNo : list) {
			taskInfo.setTaskNo(taskNo);
			int y = taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
			if (y != 1) {
				throw new RuntimeException(taskNo+"任务单修改失败！");
			}
		}

		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("成功！");
		return dto;
	}

	/**
	 * 根据任务单编号查询历史节点
	 * @param taskNo 任务编号
	 * @return
	 */
	@Override
	public ListDTO<TaskNodeDTO> qryTaskNode(String taskNo) {
		ListDTO<TaskNodeDTO> listDTO = new ListDTO<TaskNodeDTO>(RetCodeEnum.EXCEPTION);

		TaskInfo taskInfo1 = taskInfoMapper.selectByPrimaryKey(taskNo);
		if (taskInfo1 == null){
			listDTO.setRetMsg("任务单编号不存在！");
			return listDTO;
		}

		List<TaskNodeDTO> taskNodeDTOList = taskNodeMapper.qryTaskNode(taskNo);

		listDTO.setRetList(taskNodeDTOList);
		listDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		listDTO.setRetMsg("查询任务单操作记录成功！");
		return listDTO;
	}
}
