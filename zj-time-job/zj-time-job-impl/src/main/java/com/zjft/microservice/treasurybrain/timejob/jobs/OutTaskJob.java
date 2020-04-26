package com.zjft.microservice.treasurybrain.timejob.jobs;

import com.zjft.microservice.quartz.common.ResultEnum;
import com.zjft.microservice.quartz.jobs.BaseZjJob;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineScheduleInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.web_inner.*;
import com.zjft.microservice.treasurybrain.timejob.mapstruct.TimeJobTaskEntityConverter;
import com.zjft.microservice.treasurybrain.timejob.po.TimeJobTaskEntityPO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 常 健
 * @since 2019/08/27
 */
@Slf4j
public class OutTaskJob extends BaseZjJob {

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskDetailInfoInnerResource taskDetailInfoInnerResource;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private TaskEntityDetailInnerResource taskEntityDetailInnerResource;

	@Resource
	private LineScheduleInnerResource lineScheduleInnerResource;

	@Resource
	private TaskLineInnerResource taskLineInnerResource;

	@Resource
	private TaskDetailPropertyInnerResource taskDetailPropertyInnerResource;

	//@Transactional(rollbackFor = Exception.class)
	@Override
	protected ResultEnum taskPerform(JobExecutionContext jobExecutionContext) throws Exception {

		//查找需要出库的寄存箱
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, +1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = formatter.format(date.getTime()).toString();
		List<TaskEntityDTO> dtoList = taskEntityInnerResource.selectOutInfo(sysDate);
		if (dtoList.size() == 0) {
			return ResultEnum.SUCCEED;
		}
		List<TimeJobTaskEntityPO> poList = TimeJobTaskEntityConverter.INSTANCE.dto2po(dtoList);
		List<String> customerNoList = new ArrayList<>();
		List<TimeJobTaskEntityPO> taskEntityTablePOList = new ArrayList<>();
		for (TimeJobTaskEntityPO list : poList) {
			String taskNo = list.getTaskNo();
			if (StringUtil.isNullorEmpty(taskNo)) {
				return ResultEnum.EXCEPTION;
				//throw new RuntimeException("任务单编号为空");
			}
			String customerNo = list.getCustomerNo();
			if (!customerNoList.contains(customerNo)) {
				customerNoList.add(customerNo);
				//网点和任务单的绑定关系
				TimeJobTaskEntityPO taskEntityTablePO = new TimeJobTaskEntityPO();
				taskEntityTablePO.setTaskNo(taskNo);
				taskEntityTablePO.setCustomerNo(customerNo);
				taskEntityTablePOList.add(taskEntityTablePO);
			}
		}

		//网点和任务单的一对一关系（taskEntityTablePOList 包含网点、任务单信息）
		for (TimeJobTaskEntityPO taskEntityTablePO : taskEntityTablePOList) {
			String taskNo = taskEntityTablePO.getTaskNo();
			String customerNo = taskEntityTablePO.getCustomerNo();
			TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
//			String clrCenterNo = taskInnerResource.getClrCenterNo(taskNo);
			String clrCenterNo = taskTableDTO.getClrCenterNo();
//			String shelfNo = taskInnerResource.getShelfNo(taskNo);
			String shelfNo = taskTableDTO.getShelfNo();
			String planFinishDate = taskTableDTO.getPlanFinishDate();
			String theYearMonth = planFinishDate.substring(0, 7);
			String theDay = planFinishDate.substring(8);
//			String lineNo = sysOrgInnerResource.selectLineNo(customerNo);//84
			String newTaskNo = taskInnerResource.getNextLogicId(clrCenterNo, 3);

			//查询服务对象基本信息
			Map<String, String> customerMap = sysOrgInnerResource.qryCenterByNo(customerNo);
			String customerName = StringUtil.parseString(customerMap.get("NAME"));
			String customerAddress = StringUtil.parseString(customerMap.get("ADDRESS"));
			String lng = StringUtil.parseString(customerMap.get("X"));
			String lat = StringUtil.parseString(customerMap.get("Y"));

			//插入task_table
			Map<String, Object> taskMap = new HashMap<>();
			taskMap.put("taskNo", newTaskNo);
			taskMap.put("taskType", 3);
			taskMap.put("planFinishDate", sysDate);
			taskMap.put("status", 201);
			taskMap.put("clrCenterNo", clrCenterNo);
			taskMap.put("customerNo", customerNo);
			taskMap.put("customerType", 6);
			taskMap.put("customerName", customerName);
			taskMap.put("address", customerAddress);
			taskMap.put("createTime", CalendarUtil.getSysTimeYMDHMS());
			taskMap.put("shelfNo", shelfNo);
			int x = taskInnerResource.insertSelectiveByMap(taskMap);
			if (x < 1) {
				log.error("生成[task_table]失败");
				return ResultEnum.EXCEPTION;
				//throw new RuntimeException("失败");
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
				taskLineMap.put("taskNo", newTaskNo);
				taskLineMap.put("sortNo", lineScheduleDTO.getSortNo());
				taskLineMap.put("address", customerAddress);
				taskLineMap.put("lng", lng);
				taskLineMap.put("lat", lat);
				//todo 最早到达时间与最迟到达时间应该从线路排班中取得
//			taskLineMap.put("earlyTime", earlyTime);
//			taskLineMap.put("latestTime", latestTime);
				taskLineInnerResource.insertSelectiveByMap(taskLineMap);
			}

			//插入task_detail_table和task_detail_property_table
			List<TaskProductDTO> taskProductDTOList = taskInnerResource.qryTaskDetailList(taskNo);
			if (taskProductDTOList != null) {
				for (TaskProductDTO taskProductDTO : taskProductDTOList) {
					String productNo = taskProductDTO.getProductNo();
					String direction = taskProductDTO.getDirection();
					Integer amount = taskProductDTO.getAmount();

					//插入产品明细表task_detail_table
					String id = java.util.UUID.randomUUID().toString().replace("-", "");
					HashMap<String, Object> parabMap = new HashMap<String, Object>();
					parabMap.put("id", id);
					parabMap.put("taskNo", newTaskNo);
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
						paramMap.put("taskNo", newTaskNo);
						int c = taskDetailPropertyInnerResource.insertSelectiveByMap(paramMap);
						if (c == -1) {
							throw new RuntimeException("失败");
						}
					}
				}
			}

			//插入task_entity_table
			List<TaskEntityDTO> taskEntityDTOList = taskInnerResource.qryEntityNoLists(taskNo);
			if (taskEntityDTOList != null) {
				for (TaskEntityDTO taskEntityDTO : taskEntityDTOList) {
					String id = java.util.UUID.randomUUID().toString().replace("-", "");
					Map<String, Object> taskEntityMap = new HashMap<>();
					taskEntityMap.put("id", id);
					taskEntityMap.put("taskNo", newTaskNo);
					taskEntityMap.put("entityNo", taskEntityDTO.getEntityNo());
					taskEntityMap.put("productNo", taskEntityDTO.getProductNo());
					taskEntityMap.put("parentEntity", taskEntityDTO.getParentEntity());
					taskEntityMap.put("amount", taskEntityDTO.getAmount());
					taskEntityMap.put("direction", taskEntityDTO.getDirection()); //1：出库  2：入库
					taskEntityMap.put("leafFlag", taskEntityDTO.getLeafFlag());//调拨任务单中的容器默认为最下级容器
					taskEntityMap.put("note", taskEntityDTO.getNote());
					taskEntityMap.put("depositType", taskEntityDTO.getDepositType());
					int m = taskEntityInnerResource.insertSelectiveByMap(taskEntityMap);
					if (m < 1) {
						log.error("生成[task_entity_table]失败");
						return ResultEnum.EXCEPTION;
						//throw new RuntimeException("失败");
					}
					List<TaskEntityDetailDTO> taskEntityDetailDTOList = taskEntityDTO.getTaskEntityDetailDTOList();
					for (TaskEntityDetailDTO taskEntityDetailDTO : taskEntityDetailDTOList) {
						HashMap<String, Object> paradMap = new HashMap<String, Object>();
								paradMap.put("id", id);
								paradMap.put("taskNo", newTaskNo);
								paradMap.put("key", taskEntityDetailDTO.getKey());
								paradMap.put("value", taskEntityDetailDTO.getValue());
								paradMap.put("name", taskEntityDetailDTO.getName());
								int n = taskEntityDetailInnerResource.insertSelectiveByMap(paradMap);
								if (n < 1) {
									log.error("生成[task_entity_detail]失败");
									return ResultEnum.EXCEPTION;
									//throw new RuntimeException("失败");
								}
					}
				}
			}
		}

//
//
//			for (TimeJobTaskEntityPO timeJobTaskEntityPO : poList) {
//				if (customerNo.contains(timeJobTaskEntityPO.getCustomerNo())) {
//					String id = java.util.UUID.randomUUID().toString().replace("-", "");
//					Map<String, Object> taskEntityMap = new HashMap<>();
//					taskEntityMap.put("id", id);
//					taskEntityMap.put("taskNo", newTaskNo);
//					taskEntityMap.put("entityNo", timeJobTaskEntityPO.getEntityNo());
//					taskEntityMap.put("productNo", timeJobTaskEntityPO.getProductNo());
//					taskEntityMap.put("parentEntity", timeJobTaskEntityPO.getParentEntity());
//					taskEntityMap.put("amount", timeJobTaskEntityPO.getAmount());
//					taskEntityMap.put("direction", timeJobTaskEntityPO.getDirection()); //1：出库  2：入库
//					taskEntityMap.put("leafFlag", timeJobTaskEntityPO.getLeafFlag());//调拨任务单中的容器默认为最下级容器
//					taskEntityMap.put("note", timeJobTaskEntityPO.getNote());
//					taskEntityMap.put("depositType", timeJobTaskEntityPO.getDepositType());
//					int m = taskEntityInnerResource.insertSelectiveByMap(taskEntityMap);
//					if (m < 1) {
//						log.error("生成[task_entity_table]失败");
//						return ResultEnum.EXCEPTION;
//						//throw new RuntimeException("失败");
//					}
//					//插入task_entity_detail
//					//查找task_entity_detail里符合此任务单编号的信息
//					List<TaskEntityDetailDTO> taskEntityDetailDTOList = taskEntityDetailInnerResource.selectByTaskNo(taskNo);
//					//需要出库的容器的编号列表
//					List<String> outContainerNoList = new ArrayList<>();
//					// todo 报错注释
//					if (taskEntityDetailDTOList.size() > 0) {
//							for (TaskEntityDetailDTO taskEntityDetailDTO : taskEntityDetailDTOList) {
//								HashMap<String, Object> paradMap = new HashMap<String, Object>();
//								paradMap.put("id", id);
//								paradMap.put("taskNo", newTaskNo);
//								paradMap.put("key", taskEntityDetailDTO.getKey());
//								paradMap.put("value", taskEntityDetailDTO.getValue());
//								paradMap.put("name", taskEntityDetailDTO.getName());
//								int n = taskEntityDetailInnerResource.insertSelectiveByMap(paradMap);
//								if (n < 1) {
//									log.error("生成[task_entity_detail]失败");
//									return ResultEnum.EXCEPTION;
//									//throw new RuntimeException("失败");
//								}
//							}

//							//剔除不需要出库的容器编号
//							if (po.getContainerNo().contains(taskEntityDetailDTO.getContainerNo())) {
//								if (!outContainerNoList.contains(taskEntityDetailDTO.getContainerNo())) {
//									//存在一个容器多条记录的情况
//									outContainerNoList.add(taskEntityDetailDTO.getContainerNo());
//								}
//								String containerNo = taskEntityDetailDTO.getContainerNo();
//								Integer containerType = taskEntityDetailDTO.getContainerType();
//								BigDecimal amount = taskEntityDetailDTO.getAmount();
//								Integer currencyType = taskEntityDetailDTO.getCurrencyType();
//								String currencyCode = taskEntityDetailDTO.getCurrencyCode();
//								Integer denomination = taskEntityDetailDTO.getDenomination();
//								Integer opType = taskEntityDetailDTO.getOpType();
//								String opNo = taskEntityDetailDTO.getOpNo();
//								String opTime = taskEntityDetailDTO.getOpTime();
//								String clearMachineNo = taskEntityDetailDTO.getClearMachineNo();
//								BigDecimal applyAmount = taskEntityDetailDTO.getApplyAmount();
//								Map<String, Object> taskEntityDetailMap = new HashMap<>();
//								taskEntityDetailMap.put("id", UUID.randomUUID().toString().replace("-", ""));
//								taskEntityDetailMap.put("taskNo", newTaskNo);
//								taskEntityDetailMap.put("containerNo", containerNo);
//								taskEntityDetailMap.put("containerType", containerType);
//								taskEntityDetailMap.put("amount", amount);
//								taskEntityDetailMap.put("currencyType", currencyType);
//								taskEntityDetailMap.put("currencyCode", currencyCode);
//								taskEntityDetailMap.put("denomination", denomination);
//								taskEntityDetailMap.put("opType", opType);
//								taskEntityDetailMap.put("opNo", opNo);
//								taskEntityDetailMap.put("opTime", opTime);
//								taskEntityDetailMap.put("clearMachineNo", clearMachineNo);
//								taskEntityDetailMap.put("applyAmount", applyAmount);
//								int n = taskEntityDetailInnerResource.insertSelectiveByMap(taskEntityDetailMap);
//								if (n < 1) {
//									log.error("生成[task_entity_detail]失败");
//									return ResultEnum.EXCEPTION;
//									//throw new RuntimeException("失败");
//								}
//							}

//					}
//				}
//			}
//		}
			log.info("执行成功！");
			return ResultEnum.SUCCEED;
		}
	}

