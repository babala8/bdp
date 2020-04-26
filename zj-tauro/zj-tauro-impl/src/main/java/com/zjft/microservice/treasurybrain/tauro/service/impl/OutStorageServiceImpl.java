package com.zjft.microservice.treasurybrain.tauro.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.storage.web.ShelfResource;
import com.zjft.microservice.treasurybrain.storage.web_inner.TaskShelfUserInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityTableDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityDetailInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskPerRecorderInnerResource;
import com.zjft.microservice.treasurybrain.tauro.po.TauroTaskTablePO;
import com.zjft.microservice.treasurybrain.tauro.service.OutStorageService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/08/08
 */
@Slf4j
@Service
public class OutStorageServiceImpl implements OutStorageService {

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskEntityDetailInnerResource taskEntityDetailInnerResource;

	@Resource
	private TaskPerRecorderInnerResource taskPerRecorderInnerResource;

	@Resource
	private ShelfResource shelfResource;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private TaskShelfUserInnerResource taskShelfUserInnerResource;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO outStorage(TaskTransferDTO taskTransferDTO) {
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		String lineNo = taskTransferDTO.getLineNo();
		int taskType = taskTransferDTO.getTaskType();
		//int nextStatus = taskTransferDTO.getStatus();
		String operateType = taskTransferDTO.getOperateType();
		int nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		List<String> entityNoList = taskTransferDTO.getEntityNos();
		List<String> taskContainerList = new ArrayList<String>();
		List<String> taskNoList = new ArrayList<String>();
		List<String> statusString = serviceInnerResource.mayStatusConverts(operateType, taskType);
		for (String mayStatus : statusString) {
			Map<String, Object> lineMap = new HashMap<String, Object>();
			lineMap.put("lineNo", lineNo);
			lineMap.put("status", mayStatus);
			lineMap.put("planFinishDate", CalendarUtil.getSysTimeYMD());
			List<String> list = taskInnerResource.qryTaskNoByLineNo(lineMap);
			for (String taskNo : list) {
				TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
				int curStatus = taskTableDTO.getStatus();
				if (serviceInnerResource.isStatusConvertMatch(taskType, curStatus, operateType) > 0) {
					taskNoList.add(taskNo);
				}
			}
		}
		if (taskNoList.size() == 0) {
			throw new RuntimeException("当前线路下无[可出库]任务单");
		}
		for (String taskNo : taskNoList) {
			List<String> containerList = taskEntityInnerResource.selectContainerNoByTaskNo(taskNo);
			taskContainerList.addAll(containerList);
		}
		for (String taskNo : taskNoList) {
			TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
			int taskStatus = taskTableDTO.getStatus();
//			int taskStatus = tauroTaskTableMapper.qryStatusByNo(taskNo);
			for (String containerNo : entityNoList) {
				if (!taskContainerList.contains(containerNo)) {
					log.error("该容器[" + containerNo + "]编号输入错误");
					throw new RuntimeException("该容器[" + containerNo + "]编号输入错误");
				}
				//验证任务状态是否为已配钞入库且是否为当日任务
				String planFinishDate = taskTableDTO.getPlanFinishDate();
				if (serviceInnerResource.isStatusConvertMatch(taskType, taskStatus, operateType) < 1 || !planFinishDate.equals(CalendarUtil.getSysTimeYMD())) {
					throw new RuntimeException("当前任务[" + taskNo + "]未完成配钞或不是今日任务单");
				}
				//任务单中的如果存在钞袋没有匹配则不允许进行出库操作
				List<String> cassetteBagNos = taskEntityInnerResource.selectContainerNoByTaskNo(taskNo);
				for (String cassetteBagNo : cassetteBagNos) {
					if (!entityNoList.contains(cassetteBagNo)) {
						throw new RuntimeException("任务单[" + taskNo + "]中存在未匹配的容器，无法进行出库操作");
					}
				}
			}

			for (String higherContainerNo : entityNoList) {
				//TauroTaskEntityTablePO tauroTaskEntityTablePO = tauroTaskEntityTableMapper.qryByContainerNo(higherContainerNo);
				if (!taskNoList.contains(taskNo)) {
					taskNoList.add(taskNo);
				}
				//获取当前最上级容器以及下属所有容器编号
				List<String> containerNosList = new ArrayList<>();
				List<String> list = getLowerContainerNosList(higherContainerNo, containerNosList, taskNo);
				for (String containerNo : list) {
					//新增任务执行记录表
//					TauroTaskEntityDetailPO tauroTaskEntityDetailPO = tauroTaskEntityDetailMapper.selectByNo(taskNo, containerNo);
					TaskEntityDTO taskEntityDTO = taskEntityInnerResource.selectOneByTaskNo(taskNo,containerNo);
					if (taskEntityDTO == null) {
						continue;
					}
//					TauroTaskPerRecorderPO tauroTaskPerRecorderPO = new TauroTaskPerRecorderPO();
//					tauroTaskPerRecorderPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
//					tauroTaskPerRecorderPO.setTaskNo(taskNo);
//					tauroTaskPerRecorderPO.setContainerNo(containerNo);
//					tauroTaskPerRecorderPO.setPerformTime(CalendarUtil.getSysTimeYMDHMS());
//					tauroTaskPerRecorderPO.setPerformType(StatusEnum.TaskPerRecorderTypeTauro.TYPE_OUT_OF_STORAGE_AND_HANDOVER.getType());
//					tauroTaskPerRecorderPO.setOpNo1(sysUserResource.getAuthUserInfo().getUsername());
//					tauroTaskPerRecorderPO.setContainerType(taskEntityDetailTableDTO.getContainerType());

					Map<String,Object> taskPerRecorderMap = new HashMap<>();
					taskPerRecorderMap.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
					taskPerRecorderMap.put("taskNo",taskNo);
					taskPerRecorderMap.put("containerNo",containerNo);
					taskPerRecorderMap.put("performTime",CalendarUtil.getSysTimeYMDHMS());
					taskPerRecorderMap.put("performType",StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
					taskPerRecorderMap.put("opNo1",sysUserResource.getAuthUserInfo().getUsername());
					taskPerRecorderMap.put("containerType", taskEntityDTO.getProductNo());

					int x = taskPerRecorderInnerResource.insertByMap(taskPerRecorderMap);
					if (x != 1) {
						log.error("容器[" + containerNo + "]生成任务执行记录表失败");
						throw new RuntimeException("容器[" + containerNo + "]生成任务执行记录表失败");
					}
				}
			}
			//生成笼车卸钞记录
			String shelfNo = taskTableDTO.getShelfNo();
//			if (StringUtil.isNullorEmpty(shelfNo)) {
//				log.error("任务单[" + taskNo + "]未绑定笼车");
//				throw new RuntimeException("任务单[" + taskNo + "]未绑定笼车");
//			}
			if (!StringUtil.isNullorEmpty(shelfNo)) {
//				TauroTaskShelfUserPO tauroTaskShelfUserPO = new TauroTaskShelfUserPO();
//				tauroTaskShelfUserPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
//				tauroTaskShelfUserPO.setTaskNo(taskNo);
//				tauroTaskShelfUserPO.setShelfNo(shelfNo);
//				tauroTaskShelfUserPO.setOpType(StatusEnum.TaskShelfUserTypeTauro.TYPE_OUT.getType());
//				tauroTaskShelfUserPO.setOpTime(CalendarUtil.getSysTimeYMDHMS());
				Map<String,Object> taskShelfUserMap = new HashMap<>();
				taskShelfUserMap.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
				taskShelfUserMap.put("taskNo",taskNo);
				taskShelfUserMap.put("shelfNo",shelfNo);
				taskShelfUserMap.put("opType",StatusEnum.TaskShelfUserTypeTauro.TYPE_OUT.getType());
				taskShelfUserMap.put("opTime",CalendarUtil.getSysTimeYMDHMS());
//				int y = tauroTaskShelfUserMapper.insert(tauroTaskShelfUserPO);
				int y = taskShelfUserInnerResource.insertByMap(taskShelfUserMap);
				if (y != 1) {
					log.error("生成任务单[" + taskNo + "]笼车卸货记录失败");
					throw new RuntimeException("生成任务单[" + taskNo + "]笼车卸货记录失败");
				}

				//更新笼车状态
//				TauroShelfTablePO tauroShelfTablePO = new TauroShelfTablePO();
//				tauroShelfTablePO.setShelfNo(shelfNo);
//				tauroShelfTablePO.setStatus(StatusEnum.ShelfTableStatus.STATUS_ALREADY_ENABLE.getStatus());
				int b = shelfResource.updateStatusByNo(shelfNo,StatusEnum.ShelfTableStatus.STATUS_ALREADY_ENABLE.getStatus());
				if (b != 1) {
					log.error("更新任务单[" + taskNo + "]笼车状态失败");
					throw new RuntimeException("更新任务单[" + taskNo + "]笼车状态失败");
				}
			}

			//更新task_table的状态信息
//			TauroTaskTablePO tauroTaskTablePO = new TauroTaskTablePO();
//			tauroTaskTablePO.setTaskNo(taskNo);
			//更新任务状态
//			tauroTaskTablePO.setStatus(nextStatus);
			//更新物品和笼车的绑定状态（解除绑定）
//			tauroTaskTablePO.setShelfNo(null);
//			int z = tauroTaskTableMapper.updateByNo(tauroTaskTablePO);

			Map<String,Object> taskTableMap = new HashMap<>();
			taskTableMap.put("taskNo",taskNo);
			//更新任务状态
			taskTableMap.put("status",nextStatus);
			//更新物品和笼车的绑定状态（解除绑定）
			taskTableMap.put("shelfNo",null);
			int z = taskInnerResource.updateByPrimaryKeyMap(taskTableMap);
			if (z != 1) {
				log.error("更新表 [TASK_TABLE] 出库状态失败");
				throw new RuntimeException("更新订单任务编号[" + taskNo + "]出库状态失败");
			}
			//推送给所有人信息
			TauroTaskTablePO tauroTaskTablePO = new TauroTaskTablePO();
			tauroTaskTablePO.setTaskNo(taskNo);
			tauroTaskTablePO.setStatus(nextStatus);
			tauroTaskTablePO.setShelfNo(null);
			tauroTaskTablePO.setType("ChuKuJiaoJie");
			String jsonStorageInfo = JSONUtil.createJsonString(tauroTaskTablePO);
			sendInfoInnerResource.sendInfo2All(jsonStorageInfo);

		}

		return dto;
	}

	/**
	 * 查找该上级容器下及所有下属下级容器编号
	 */
	@Override
	public List<String> getLowerContainerNosList(String containerNo, List<String> list, String taskNo) {
		list.add(containerNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("containerNo", containerNo);
		map.put("taskNo", taskNo);
		TaskEntityDTO taskEntityDTO = taskEntityInnerResource.qryByContainerNo(containerNo);
		if (taskEntityDTO == null) {
			return list;
		}
		if (taskEntityDTO.getLeafFlag() == 1) {
			return list;
		} else {
			List<String> lowerContainerNos = taskEntityInnerResource.qryLowerContainterNoListByNo(containerNo, taskNo);
			for (String lowerContainerNo : lowerContainerNos) {
				getLowerContainerNosList(lowerContainerNo, list, taskNo);
			}
			return list;
		}
	}
}
