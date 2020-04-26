package com.zjft.microservice.treasurybrain.task.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.business.web_inner.TaskInOutInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.AddnotesLineDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.AddnoteLineInnerResource;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.task.domain.TaskDetail;
import com.zjft.microservice.treasurybrain.task.domain.TaskInfo;
import com.zjft.microservice.treasurybrain.task.dto.TaskInfoDTO;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskDTOConverter;
import com.zjft.microservice.treasurybrain.task.repository.TaskDetailMapper;
import com.zjft.microservice.treasurybrain.task.repository.TaskEntityDetailMapper;
import com.zjft.microservice.treasurybrain.task.repository.TaskEntityMapper;
import com.zjft.microservice.treasurybrain.task.repository.TaskInfoMapper;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@ZjComponentResource(group = "task")
public class TaskFlowResourceComponent {

	@Resource
	private TaskInfoMapper taskInfoMapper;

	@Resource
	private TaskDetailMapper taskDetailMapper;

	@Resource
	private TaskEntityMapper taskEntityMapper;

	@Resource
	private TaskEntityDetailMapper taskEntityDetailMapper;

	@Resource
	private TaskInOutInnerResource taskInOutInnerResource;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private AddnoteLineInnerResource addnoteLineInnerResource;

	@Resource
	private ServiceInnerResource serviceInnerResource;


	/**
	 * 删除任务单前参数校验
	 */
	@ZjComponentMapping("删除任务单前参数校验")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String delTaskCheck(String requestString, DTO returnDTO, String taskNo) {

		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(requestString);
		if (taskInfo == null){
			returnDTO.setRetCode(RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
			returnDTO.setRetMsg("任务单不存在");
			return "fail";
		}
		taskNo = requestString;
		return "ok";
	}

	/**
	 * 删除任务单明细表
	 */
	@ZjComponentMapping("删除任务单明细表")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String delTaskDetail(String requestString, DTO returnDTO, String taskNo) {
		int a = taskDetailMapper.deleteByTaskNo(taskNo);
		if (a == -1) {
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			returnDTO.setRetMsg("删除任务单明细表失败");
			return "fail";
		}
		return "ok";
	}

	/**
	 * 删除任务物品明细表
	 */
	@ZjComponentMapping("删除任务物品明细表")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String delTaskEntityDetail(String requestString, DTO returnDTO, String taskNo) {
		int a = taskEntityDetailMapper.deleteByTaskNo(taskNo);
		if (a == -1) {
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			returnDTO.setRetMsg("删除任务单物品明细表失败");
			return "fail";
		}
		return "ok";
	}

	/**
	 * 删除任务物品表
	 */
	@ZjComponentMapping("删除任务物品表")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String delTaskEntity(String requestString, DTO returnDTO, String taskNo) {
		int a = taskEntityMapper.deleteByTaskNo(taskNo);
		if (a == -1) {
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			returnDTO.setRetMsg("删除任务单物品表失败");
			return "fail";
		}
		return "ok";
	}

	/**
	 * 删除任务调入调出表
	 */
	@ZjComponentMapping("删除任务调入调出表")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String delTaskInOut(String requestString, DTO returnDTO, String taskNo) {
		int a = taskInOutInnerResource.deleteByTaskNo(taskNo);
		if (a == -1) {
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			returnDTO.setRetMsg("删除任务单调入调出表失败");
			return "fail";
		}
		return "ok";
	}

	/**
	 * 删除任务表
	 */
	@ZjComponentMapping("删除任务表")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String delTask(String requestString, DTO returnDTO, String taskNo) {
		int a = taskInfoMapper.deleteByPrimaryKey(taskNo);
		if (a == -1) {
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			returnDTO.setRetMsg("删除任务表失败");
			return "fail";
		}
		returnDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		returnDTO.setRetMsg("删除成功");
		return "ok";
	}

	/**
	 * 分页查询任务单参数检查
	 */
	@ZjComponentMapping("分页查询任务单参数检查")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String qryTaskCheck(HashMap requestHashMap, PageDTO returnPageDTO, HashMap paramsMap) {

		Object taskNo = requestHashMap.get("taskNo");
		Object clrCenterNo = requestHashMap.get("clrCenterNo");
		Object taskType = requestHashMap.get("taskType");
		Object lineNo = requestHashMap.get("lineNo");
		Object startDate = requestHashMap.get("startDate");
		Object endDate = requestHashMap.get("endDate");
		Object operateType = requestHashMap.get("operateType");
		Object addnotesPlanNo = requestHashMap.get("addnotesPlanNo");

		//计算分页参数
		Integer curPage = StringUtil.objectToInt(requestHashMap.get("curPage"));
		Integer pageSize = StringUtil.objectToInt(requestHashMap.get("pageSize"));

		if (-1 == curPage) {
			curPage = 1;
		}
		if (-1 == pageSize) {
			pageSize = 10;
		}

		UserDTO authUser = sysUserResource.getAuthUserInfo();
		String orgNo = authUser.getOrgNo();
		String opNo = authUser.getUsername();
		Integer orgGradeNo = sysOrgInnerResource.qryOrgGradeNoByOrgNo(orgNo);
		Integer clrCenterFlag = sysOrgInnerResource.qryClrCenterFlag(orgNo);
		Integer productType = StringUtil.objectToInt(requestHashMap.get("productType"));//自定义产品
		if(productType !=1){
			productType =0;
		}
		//是否查询所有任务单（1：所有 0：当前登录人），默认为1
		Integer checkAll = StringUtil.objectToInt(requestHashMap.get("checkAll"));
		if(-1 == checkAll){
			checkAll = 1;
		}

		paramsMap.put("startRow", pageSize * (curPage - 1));
		paramsMap.put("endRow", pageSize * curPage);
		paramsMap.put("opNo", opNo);
		paramsMap.put("productType", productType);
		paramsMap.put("orgNo", orgNo);
		paramsMap.put("orgGradeNo", orgGradeNo);
		paramsMap.put("clrCenterFlag", clrCenterFlag);
		paramsMap.put("checkAll", checkAll);
		paramsMap.put("taskNo", taskNo);
		paramsMap.put("clrCenterNo", clrCenterNo);
		paramsMap.put("taskType", taskType);
		paramsMap.put("lineNo", lineNo);
		paramsMap.put("startDate", startDate);
		paramsMap.put("endDate", endDate);
		paramsMap.put("operateType", operateType);
		paramsMap.put("addnotesPlanNo", addnotesPlanNo);

		return "ok";
	}

	/**
	 * 分页查询任务单列表
	 */
	@ZjComponentMapping("查询任务单参数检查")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String qryTask(HashMap requestHashMap, PageDTO returnPageDTO, HashMap paramsMap) {

		//查询任务单信息
		Integer curPage = StringUtil.objectToInt(paramsMap.get("curPage"));
		Integer pageSize = StringUtil.objectToInt(paramsMap.get("pageSize"));
		int totalRow = taskInfoMapper.selectTotalRow(paramsMap);
		int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
		List<Map<String, Object>> taskList = taskInfoMapper.selectTask(paramsMap);

		String taskNo = StringUtil.parseString(paramsMap.get("taskNo"));
		Integer taskType = StringUtil.objectToInt(paramsMap.get("taskType"));
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

		Map<String, String> addnotesLineMap = this.getAddnotesLineMap();
		for (Map<String, Object> resultMap : taskList) {
			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("taskNo", StringUtil.parseString(resultMap.get("taskNo")));
			entity.put("taskType", StringUtil.parseString(resultMap.get("taskType")));
			entity.put("taskTypeName", StringUtil.parseString(resultMap.get("taskTypeName")));
			entity.put("planFinishDate", StringUtil.parseString(resultMap.get("planFinishDate")));
			entity.put("status", StringUtil.objectToInt(resultMap.get("status")));
			entity.put("clrCenterNo", StringUtil.parseString(resultMap.get("clrCenterNo")));
			entity.put("lineNo", StringUtil.parseString(resultMap.get("lineNo")));
			entity.put("opNo1", StringUtil.parseString(resultMap.get("opNo1")));
			entity.put("opName1", StringUtil.parseString(resultMap.get("addnotesOpName1")));
			entity.put("opNo2", StringUtil.parseString(resultMap.get("opNo2")));
			entity.put("opName2", StringUtil.parseString(resultMap.get("addnotesOpName2")));
			entity.put("createOpNo", StringUtil.parseString(resultMap.get("createOpNo")));
			entity.put("createOpName", StringUtil.parseString(resultMap.get("createOpName")));
			entity.put("createTime", StringUtil.parseString(resultMap.get("createTime")));
			entity.put("modeOp", StringUtil.parseString(resultMap.get("modeOp")));
			entity.put("modeTime", StringUtil.parseString(resultMap.get("modeTime")));
			entity.put("carNumber", StringUtil.parseString(resultMap.get("carNumber")));
			entity.put("addnotesPlanNo", StringUtil.parseString(resultMap.get("addnotesPlanNo")));
			entity.put("clrCenterName", StringUtil.parseString(resultMap.get("clrCenterName")));
			entity.put("lineName", StringUtil.parseString(resultMap.get("lineName")));
			entity.put("orgName", StringUtil.parseString(resultMap.get("orgName")));
			if (taskType != 1) {
				entity.put("customerNo", StringUtil.parseString(resultMap.get("customerNo")));
			}
			//如果任务单类型为2（网点调拨任务单），则查询不同箱子的数量以及金额
			if (StringUtil.objectToInt(resultMap.get("taskType")) == 2) {

				String taskNos = StringUtil.parseString(resultMap.get("taskNo"));
				int containerType = StatusEnum.ContainerType.UNDERSTANDING_BOX.getType();
				Map<String, Object> paramaMap = new HashMap<String, Object>();
				paramaMap.put("taskNo", taskNos);
				paramaMap.put("containerType", containerType);
				int underStaBoxCount = taskEntityDetailMapper.selectContainerNo(paramaMap);
				int containerType1 = StatusEnum.ContainerType.DEPOSIT_BOX.getType();
				Map<String, Object> parambMap = new HashMap<String, Object>();
				parambMap.put("taskNo", taskNos);
				parambMap.put("containerType", containerType1);
				int depBoxCount = taskEntityDetailMapper.selectContainerNo(parambMap);
				//解现箱总金额
				Map<String, Object> paramcMap = new HashMap<String, Object>();
				paramcMap.put("taskNo", taskNos);
				paramcMap.put("direction", 1);
				String deptBoxAmount = taskInOutInnerResource.selectSumAmount(paramcMap);
				if (deptBoxAmount == null) {
					deptBoxAmount = "0";
				}
				entity.put("underStandingBoxCount", underStaBoxCount);
				entity.put("depositBoxCount", depBoxCount);
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
				String deptBoxAmount = taskInOutInnerResource.selectSumAmount(paramMap1);
				entity.put("depositBoxAmount", deptBoxAmount);
			}
			//如果任务单类型为3（寄存箱出库任务单），则查询待出库箱子的数量以及金额
			if (StringUtil.objectToInt(resultMap.get("taskType")) == 3) {
				Map paramMap1 = new HashMap();
				paramMap1.put("taskNo", taskNo);
				paramMap1.put("direction", StatusEnum.Direction.OUT.getId());
				String deptBoxAmount = Optional.ofNullable(taskInOutInnerResource.selectSumAmount(paramMap1)).orElse("0");
				entity.put("depositBoxAmount", deptBoxAmount);
				Map<String, Object> parambMap = new HashMap<String, Object>();
				int containerType1 = StatusEnum.ContainerType.DEPOSIT_BOX.getType();
				String taskNos = StringUtil.parseString(resultMap.get("taskNo"));
				parambMap.put("taskNo", taskNos);
				parambMap.put("containerType", containerType1);
				int depBoxCount = taskEntityDetailMapper.selectContainerNo(parambMap);
				entity.put("depositBoxCount", depBoxCount);
			}


			String lineNoString = StringUtil.parseString(resultMap.get("lineNo")).replaceAll("\"|\\[|\\]", "");
			String lineName = null;
			if (lineNoString != null) {
				lineName = addnotesLineMap.get(lineNoString);
			}
			List<TaskDetail> taskDetail = taskDetailMapper.getDetailList(StringUtil.parseString(resultMap.get("taskNo")));
			int devCount = taskDetail.size();
			entity.put("addnotesAmt", StringUtil.parseString(resultMap.get("addnotesAmt")));
			entity.put("devCount", devCount);
			entity.put("lineNo", lineNoString);
			entity.put("lineName", lineName);
			retList.add(entity);
		}

		returnPageDTO.setPageSize(pageSize);
		returnPageDTO.setCurPage(curPage);
		returnPageDTO.setTotalPage(totalPage);
		returnPageDTO.setTotalRow(totalRow);
		returnPageDTO.setRetList(retList);
		returnPageDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		returnPageDTO.setRetMsg("任务查询成功！");

		return "ok";
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



	/**
	 * 任务单基础信息修改
	 */
	@ZjComponentMapping("任务单基础信息修改参数检查")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String updateTaskInfoCheck(TaskInfoDTO taskInfoDTO, DTO returnDTO, DTO tempDTO) {
		try {
			String taskNo = taskInfoDTO.getTaskNo();
			if(StringUtil.isNullorEmpty(taskNo)){
				returnDTO.setResult(RetCodeEnum.PARAM_LACK);
				return "fail";
			}
		} catch (Exception e) {
			log.error("任务单基础信息修改", e);
			returnDTO.setResult(RetCodeEnum.FAIL);
		}
		return "ok";
	}

	/**
	 * 任务单基础信息修改
	 */
	@ZjComponentMapping("任务单基础信息修改参数检查")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String updateTaskInfo(TaskInfoDTO taskInfoDTO, DTO returnDTO, DTO tempDTO) {
		//查询当前任务单状态
		Integer curStatus = taskInfoMapper.qryCurStatus(taskInfoDTO.getTaskNo());
		String operateType = taskInfoDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		Integer taskType = taskInfoDTO.getTaskType();

		int x = serviceInnerResource.isStatusConvertMatch(taskType, curStatus, operateType);
		if (x < 1) {
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			returnDTO.setRetMsg("任务单当前状态无法执行【"+operateType+"】操作");
			return "fail";
		}

		TaskInfo taskInfo = TaskDTOConverter.INSTANCE.domain2dto(taskInfoDTO);

		TaskInfo taskInfo1 = taskInfoMapper.selectByPrimaryKey(taskInfo.getTaskNo());
		if (taskInfo1 == null){
			returnDTO.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
			return "fail";
		}

		taskInfo.setStatus(nextStatus);
		int y = taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
		if(y < 1){
			returnDTO.setRetMsg("修改任务单失败");
			return "fail";
		}
		returnDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		returnDTO.setRetMsg("修改任务单成功！");
		return "ok";
	}


}
