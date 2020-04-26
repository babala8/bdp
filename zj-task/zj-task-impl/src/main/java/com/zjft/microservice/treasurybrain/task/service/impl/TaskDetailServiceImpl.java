package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.business.web_inner.AddnotesPlanDetailInnerResource;
import com.zjft.microservice.treasurybrain.business.web_inner.TaskInOutInnerResource;
import com.zjft.microservice.treasurybrain.clearcenter.dto.TaskCheckDTO;
import com.zjft.microservice.treasurybrain.clearcenter.web_inner.TaskCheckInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.task.domain.TaskCountInfo;
import com.zjft.microservice.treasurybrain.task.domain.TaskInfo;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.mapstruct.*;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityDetailPO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import com.zjft.microservice.treasurybrain.task.repository.*;
import com.zjft.microservice.treasurybrain.task.service.TaskDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class TaskDetailServiceImpl implements TaskDetailService {
	@Resource
	private TaskInfoMapper taskInfoMapper;

	@Resource
	private TaskDetailMapper taskDetailMapper;

	@Resource
	private TaskEntityDetailMapper taskEntityDetailMapper;

	@Resource
	private TaskEntityMapper taskEntityMapper;

	@Resource
	private TaskCheckInnerResource taskCheckInnerResource;

	@Resource
	private TaskInOutInnerResource taskInOutInnerResource;

	@Resource
	private AddnotesPlanDetailInnerResource addnotesPlanDetailInnerResource;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskDetailPropertyMapper taskDetailPropertyMapper;

	@Override
	public ListDTO<String> qryPreviousContainByCustomerNo(String customerNo) {
		ListDTO<String> previousContainNoList = new ListDTO<>(RetCodeEnum.FAIL);
		String previousTaskNo = taskDetailMapper.qryPreviousTaskNo(customerNo);
		if (previousTaskNo != null) {
			List<String> previousContainNos = taskDetailMapper.qryPreviousContainByPreviousTaskNo(previousTaskNo,customerNo);
			previousContainNoList.addAll(previousContainNos);
			previousContainNoList.setResult(RetCodeEnum.SUCCEED);
		} else {
			previousContainNoList.setRetMsg("该设备为第一次加钞，无上一次任务记录");
		}
		return previousContainNoList;
	}

	/**
	 * 网点调拨任务单审核
	 * 通过taskType判断任务单类型，依次辨别领现和寄库解现
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO modDevClrTime(TransferTaskInfoDTO transferTaskInfoDTO) {
		DTO dto1 = new DTO(RetCodeEnum.FAIL.getCode(), "审核失败");
		//查询当前任务单状态
		Integer curStatus = taskInfoMapper.qryCurStatus(transferTaskInfoDTO.getTaskNo());
		String operateType = transferTaskInfoDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		Integer taskType = transferTaskInfoDTO.getTaskType();
		String taskNo = transferTaskInfoDTO.getTaskNo();
//		String auditTime = CalendarUtil.getSysTimeYMDHMS();
		String note = transferTaskInfoDTO.getNote();
//		String auditOp = ServletRequestUtil.getUsername();

		Set set = new LinkedHashSet();
		boolean convert = serviceInnerResource.isConvert(taskNo, operateType, set);
		if (convert == false) {
			dto1.setRetCode(RetCodeEnum.FAIL.getCode());
			dto1.setRetMsg("任务单当前状态无法执行【"+operateType+"】操作");
			return dto1;
		}

		//更新任务单状态
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setTaskNo(taskNo);
		taskInfo.setStatus(nextStatus);
		taskInfo.setAuditNote(note);

		//网点审核不能更改解现信息，金库确认可以更改
		if (curStatus == 201) {
			int y = taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
			if (y != 1) {
				throw new RuntimeException("失败");
			}
			dto1.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto1.setRetMsg("网点审核成功");
		} else if (curStatus == 204 && taskType == 4) {
			int yy = taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
			if (yy != 1) {
				throw new RuntimeException("失败");
			}
			dto1.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto1.setRetMsg("网点审核成功");
		} else if (curStatus == 204 && taskType == 2) {
			List<TaskProductDTO> taskProductDTOList = transferTaskInfoDTO.getTaskProductDTOList();
			//删除后重新添加明细信息
			taskDetailPropertyMapper.deleteByTaskNo(taskNo);
			taskDetailMapper.deleteByTaskNo(taskNo);
			if (taskProductDTOList != null) {
				//插入task_detail_table和task_detail_property_table,修改前先将两张表原有任务数据删除
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
			} else {
				//插入产品明细表task_detail_table
				String id = java.util.UUID.randomUUID().toString().replace("-", "");
				HashMap<String, Object> parabMap = new HashMap<String, Object>();
				parabMap.put("id", id);
				parabMap.put("taskNo", taskNo);
				parabMap.put("amount", 0);
				int b = taskDetailMapper.insertSelectiveByMap(parabMap);
				if (b == -1) {
					throw new RuntimeException("失败");
				}
			}

			int y = taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
			if (y != 1) {
				throw new RuntimeException("失败");
			}
			dto1.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto1.setRetMsg("金库审核成功");

		}
		return dto1;
	}

	/**
	 * 清点信息查询详情
	 *
	 * @param taskNo 任务编号
	 * @return
	 */
	@Override
	public TaskDTO qryLoadNoteDetail(String taskNo) {
		TaskDTO taskDTO = new TaskDTO(RetCodeEnum.FAIL);
		try {
			//根据任务编号查询任务详情信息
			TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
			if (taskInfo == null) {
				return new TaskDTO(RetCodeEnum.RESULT_EMPTY);
			}
			taskDTO = TaskDTOConverter.INSTANCE.domain2dto(taskInfo);

			List<TaskCheckDTO> taskCheckDTOS = taskCheckInnerResource.qryInfoByTaskNo(taskNo);
			List<TaskCheckInfoDTO> taskCheckInfoDTOS = TaskCheckDTOConverter.INSTANCE.domain2dto(taskCheckDTOS);

			taskDTO.setTaskCheckList(taskCheckInfoDTOS);

			taskDTO.setRetCode("00");
			taskDTO.setRetMsg("查询清点详情成功");
		} catch (Exception e) {
			log.error("[查询详情异常]: ", e);
			return new TaskDTO(RetCodeEnum.FAIL);
		}
		return taskDTO;
	}

	/**
	 * 分页查询清点信息
	 *
	 * @param
	 * @return
	 */
	@Override
	public PageDTO<LoadNoteDTO> qryLoadNoteByPage(Map<String, Object> paramMap) {
		log.info("------------[qryLoadInfoByPage]SortService-------------");
		PageDTO pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		//清点任务管理处用到了此接口批量查询任务单金额，所以这里对任务单号做一下判断,传入的是单个任务单还是多个任务单
		String taskNoList = StringUtil.parseString(paramMap.get("taskNo"));
		String[] taskNos = taskNoList.split(",");
		Integer taskNoCount = taskNos.length;
		if(taskNoCount>=2){
			paramMap.put("taskNoCount",taskNoCount);
		}else {
			paramMap.put("taskNoCount","");
		}
		// 获取数据总条数
		int totalRow = taskInfoMapper.qryTotalRowLoadCheck(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		//获取分页数据
		List<TaskInfo> list = taskInfoMapper.qryTotalLoadCheck(paramMap);

		List<LoadInfoDTO> dtoList = LoadInfoConverter.INSTANCE.domain2dto(list);
		for (LoadInfoDTO loadInfoDTO : dtoList) {
			BigDecimal amount = taskCheckInnerResource.qryCheckAmount(loadInfoDTO.getTaskNo());
			loadInfoDTO.setCheckAmount(amount);

			int customerCount = taskDetailMapper.selectCustomerCount(loadInfoDTO.getTaskNo());
			loadInfoDTO.setCustomerCount(customerCount);
		}
		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		return pageDTO;
	}

	/**
	 * 分页查询配钞信息
	 *
	 * @return
	 */
	@Override
	public PageDTO<LoadNoteDTO> qryLoadInfoByPage(Map<String, Object> paramMap) {
		log.info("------------[qryLoadInfoByPage]SortService-------------");
		PageDTO pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		//配款任务管理处用到了此接口批量查询任务单金额，所以这里对任务单号做一下判断,传入的是单个任务单还是多个任务单
		String taskNoList = StringUtil.parseString(paramMap.get("taskNo"));
		String[] taskNos = taskNoList.split(",");
		Integer taskNoCount = taskNos.length;
		if(taskNoCount>=2){
			paramMap.put("taskNoCount",taskNoCount);
		}else {
			paramMap.put("taskNoCount","");
		}
		// 获取数据总条数
		int totalRow = taskInfoMapper.qryTotalRowLoadInfo(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		//获取分页数据
		List<TaskInfo> list = taskInfoMapper.qryTotalLoadInfo(paramMap);
		List<LoadInfoDTO> dtoList = LoadInfoConverter.INSTANCE.domain2dto(list);
		for (LoadInfoDTO loadInfoDTO : dtoList) {
			TaskInfo taskTablePO = taskInfoMapper.selectByPrimaryKey(loadInfoDTO.getTaskNo());
			double addnoteAmount = addnotesPlanDetailInnerResource.selectSumAddnoteAmount(taskTablePO.getAddnotesPlanNo());
			loadInfoDTO.setAddnoteAmount(addnoteAmount);
			if (taskTablePO.getTaskType() == 4) {
				Map<String,Object> map = new HashMap<>();
				map.put("taskNo",loadInfoDTO.getTaskNo());
				map.put("direction",2);
				Integer integer = taskDetailMapper.selectSumAmount(map);
				loadInfoDTO.setAddnoteAmount(integer);
			}
//			int count = taskEntityDetailMapper.selectTaskNoCount(loadInfoDTO.getTaskNo());
			int count = taskEntityMapper.selectTaskNoCount(loadInfoDTO.getTaskNo());
			if (count > 0) {
//				Map<String, Object> paraMap = new HashMap<>();
//				paraMap.put("taskNo", loadInfoDTO.getTaskNo());
//				paraMap.put("opType", StatusEnum.OpertateType.LOAD.getId());
				double amount = taskEntityMapper.selectAmountByTaskNo(loadInfoDTO.getTaskNo());
				loadInfoDTO.setAllocAmount(amount);
			}
//			int customerCount = taskDetailMapper.selectCustomerCount(loadInfoDTO.getTaskNo());
			//一个任务单对应一个服务对象
			loadInfoDTO.setCustomerCount(1);
		}
		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		return pageDTO;
	}

	/**
	 * 配钞信息查询
	 *
	 * @param taskNo 任务编号
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	@Override
	public LoadNoteDTO qryLoadInfo(String taskNo) {

		//查询任务基础表
		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
		String customerNo = taskInfo.getCustomerNo();
		String customerName = taskInfo.getCustomerName();
		Integer customerType = taskInfo.getCustomerType();
		Integer status = taskInfo.getStatus();
//		//根据任务编号查询任务详情信息
//		List<TaskDetail> taskDetailTablePOS = taskDetailMapper.selectByTaskNo(taskNo);
//		if (taskDetailTablePOS == null) {
//			listDTO.setRetCode(RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
//			listDTO.setRetMsg("任务[" + taskNo + "]不存在");
//			return listDTO;
//		}

		//根据任务编号查询容器信息
		List<TaskEntityPO> taskEntityPOS = taskEntityMapper.selectByTaskNo(taskNo);
		List<TaskEntityDTO> taskEntityDTOList = new ArrayList<>();
		TaskEntityDTO taskEntityDTO = null;
		for (TaskEntityPO taskEntityPO : taskEntityPOS) {
			taskEntityDTO = new TaskEntityDTO();
			taskEntityDTO.setId(taskEntityPO.getId());
			taskEntityDTO.setTaskNo(taskEntityPO.getTaskNo());
			taskEntityDTO.setProductNo(taskEntityPO.getProductNo());
			taskEntityDTO.setAmount(taskEntityPO.getAmount());
			taskEntityDTO.setParentEntity(taskEntityPO.getParentEntity());
			taskEntityDTO.setDirection(taskEntityPO.getDirection());
			taskEntityDTO.setLeafFlag(taskEntityPO.getLeafFlag());
			taskEntityDTO.setNote(taskEntityPO.getNote());
			taskEntityDTOList.add(taskEntityDTO);
		}

		for (TaskEntityDTO taskEntityDTO1 : taskEntityDTOList) {
			//查询每个容器的属性信息
			List<TaskEntityDetailPO> taskEntityDetailPO = taskEntityDetailMapper.selectByPrimaryKey(taskEntityDTO1.getId());
			List<TaskEntityDetailDTO> taskEntityDetailDTOS = TaskEntityDetailConverter.INSTANCE.domain2dto(taskEntityDetailPO);
			taskEntityDTO1.setTaskEntityDetailDTOList(taskEntityDetailDTOS);
		}

		LoadNoteDTO loadNoteDTO = new LoadNoteDTO();
		loadNoteDTO.setTaskNo(taskNo);
		loadNoteDTO.setCustomerNo(customerNo);
		loadNoteDTO.setCustomerType(customerType);
		loadNoteDTO.setStatus(status);
		loadNoteDTO.setTaskEntityDTOList(taskEntityDTOList);
		loadNoteDTO.setRetMsg("成功");
		loadNoteDTO.setRetCode("00");
		return loadNoteDTO;
		}


	/**
	 * 分页查询配钞清分信息
	 * @return
	 */
	@Override
	public PageDTO<TaskCountInfoDTO> qryCountTaskListByPage(Map<String, Object> paramMap) {
		log.info("------------[qryCountTaskListByPage]TaskToCountService-------------");
		PageDTO pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);

		int type = StringUtil.objectToInt(paramMap.get("type"));
		int taskType = StringUtil.objectToInt(paramMap.get("taskType"));
		if(type == 1) {
			if(taskType == 1) {
				paramMap.put("taskStatus","402");
			}else if(taskType == 2) {
				paramMap.put("taskStatus","402");
			}else if(taskType == 5) {
				paramMap.put("taskStatus","201");
				paramMap.put("taskStatus1","301");
			}else {
				paramMap.put("taskStatus","000");
			}

		}else if(type == 2) {
			if(taskType == 1) {
				paramMap.put("taskStatus","201");
			}else if(taskType == 4) {
				paramMap.put("taskStatus","207");
				paramMap.put("taskStatus1","209");
			}else {
				paramMap.put("taskStatus","000");
			}
		}
		// 获取数据总条数
		int totalRow = taskInfoMapper.qryTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		//获取分页数据
		List<TaskCountInfo> list = taskInfoMapper.qryCountTaskListByPage(paramMap);
		List<TaskCountInfoDTO> dtoList = TaskCountInfoConverter.INSTANCE.do2dto(list);

		if(type == 2) {
			if(taskType == 4) {
				for(TaskCountInfoDTO taskCountInfoDTO : dtoList) {
					Map paramMap1 = new HashMap();
					paramMap1.put("taskNo", StringUtil.parseString(taskCountInfoDTO.getTaskNo()));
					paramMap1.put("direction", StatusEnum.Direction.OUT.getId());

					Integer deptBoxAmount = taskDetailMapper.selectSumAmount(paramMap1);
//					String deptBoxAmount = taskInOutInnerResource.selectSumAmount(paramMap1);
					taskCountInfoDTO.setCountAmount(deptBoxAmount);
				}
			}else if(taskType == 1) {
				for(TaskCountInfoDTO taskCountInfoDTO : dtoList) {
					String addnotesPlanNo = StringUtil.parseString(taskCountInfoDTO.getAddnotesPlanNo());
					double deptBoxAmount = addnotesPlanDetailInnerResource.selectSumAddnoteAmount(addnotesPlanNo);
					taskCountInfoDTO.setCountAmount(deptBoxAmount);
				}
			}

		}

		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		return pageDTO;
	}

}
