package com.zjft.microservice.treasurybrain.tauro.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityDetailInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskPerRecorderInnerResource;
import com.zjft.microservice.treasurybrain.tauro.po.TauroTaskTablePO;
import com.zjft.microservice.treasurybrain.tauro.service.InService;
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
 * @since 2019/08/16
 */
@Slf4j
@Service
public class InServiceImpl implements InService {

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskEntityDetailInnerResource taskEntityDetailInnerResource;

	@Resource
	private TaskPerRecorderInnerResource taskPerRecorderInnerResource;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	ServiceInnerResource serviceInnerResource;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO in(TaskTransferDTO taskTransferDTO) {
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		String lineNo = taskTransferDTO.getLineNo();
		//String shelfNo = taskTransferDTO.getShelfNo();
		String date = CalendarUtil.getSysTimeYMD();
		Integer taskType = taskTransferDTO.getTaskType();
		//Integer nextStatus = taskTransferDTO.getStatus();
		String operateType = taskTransferDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		List<String> entityNoList = taskTransferDTO.getEntityNos();

		if (StringUtil.isNullorEmpty(lineNo) || StringUtil.isNullorEmpty(StringUtil.parseString(taskType)) ||
				StringUtil.isNullorEmpty(StringUtil.parseString(nextStatus))  || entityNoList.size() == 0) {
			throw new RuntimeException("缺少必要参数");
		}

		/*//判断当前笼车是否可用
		int flag = tauroShelfTableMapper.qryAvailable(shelfNo);
		if (flag != 1) {
			log.error("当前笼车已被占用，请更换新的笼车！");
			throw new RuntimeException("当前笼车已被占用，请更换新的笼车！");
		}*/

		//查找符合本次状态更新的上一个任务单状态列表
		List<String> curStatusList = serviceInnerResource.selectCurStatus(taskType, operateType);
		if (curStatusList.size() == 0) {
			throw new RuntimeException("无可执行此任务状态的任务单，执行失败");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("lineNo", lineNo);
		map.put("date", date);
		map.put("curStatusList", curStatusList);
		List<String> taskList = taskInnerResource.selectTaskNoByLineNo(map);
		if (taskList.size() == 0) {
			throw new RuntimeException("该线路[" + lineNo + "]下不存在当日可执行任务单，无法进行操作");
		}

		for (String taskNo : taskList) {
			List<String> list = taskEntityInnerResource.selectContainerNoByTaskNo(taskNo);
			for (String containerNo : list) {
				if (!entityNoList.contains(containerNo)) {
					throw new RuntimeException("任务单[" + taskNo + "]中存在未匹配的容器，无法进行操作");
				}
			}

			for (String containerNo : list) {
				//获取所有容器编号
				List<String> allContainerNoList = new ArrayList<>();
				List<String> allList = getBackContainerNosList(containerNo, allContainerNoList, taskNo);

				for (String no : allList) {
//					TauroTaskEntityDetailPO tauroTaskEntityDetailPO = tauroTaskEntityDetailMapper.selectByNo(taskNo, containerNo);
					TaskEntityDTO taskEntityDTO = taskEntityInnerResource.selectOneByTaskNo(taskNo,containerNo);
//					TauroTaskPerRecorderPO tauroTaskPerRecorderPO = new TauroTaskPerRecorderPO();
//					tauroTaskPerRecorderPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
//					tauroTaskPerRecorderPO.setTaskNo(taskNo);
//					tauroTaskPerRecorderPO.setContainerNo(no);
//					tauroTaskPerRecorderPO.setPerformTime(CalendarUtil.getSysTimeYMDHMS());
//					tauroTaskPerRecorderPO.setPerformType(StatusEnum.TaskPerRecorderTypeTauro.TYPE_IN_STORAGE_AND_HANDOVER.getType());
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

					int y = taskPerRecorderInnerResource.insertByMap(taskPerRecorderMap);
					if (y != 1) {
						log.error("容器编号[" + no + "]入库任务执行记录表添加失败");
						throw new RuntimeException("容器编号[" + no + "]入库任务执行记录表添加失败");
					}
				}
			}

			/*//生成笼车装货记录
			TauroTaskShelfUserPO tauroTaskShelfUserPO = new TauroTaskShelfUserPO();
			tauroTaskShelfUserPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
			tauroTaskShelfUserPO.setTaskNo(taskNo);
			tauroTaskShelfUserPO.setShelfNo(shelfNo);
			tauroTaskShelfUserPO.setOpType(StatusEnum.TaskShelfUserTypeTauro.TYPE_IN.getType());
			tauroTaskShelfUserPO.setOpTime(CalendarUtil.getSysTimeYMDHMS());
			int z = tauroTaskShelfUserMapper.insert(tauroTaskShelfUserPO);
			if (z != 1) {
				log.error("生成笼车[" + shelfNo + "]装货记录失败");
				throw new RuntimeException("生成笼车[" + shelfNo + "]装货记录失败");
			}

			//更新笼车状态
			TauroShelfTablePO tauroShelfTablePO = new TauroShelfTablePO();
			tauroShelfTablePO.setShelfNo(shelfNo);
			tauroShelfTablePO.setStatus(StatusEnum.ShelfTableStatus.STATUS_USED.getStatus());
			int b = tauroShelfTableMapper.updateStatusByNo(tauroShelfTablePO);
			if (b != 1) {
				log.error("更新笼车[" + shelfNo + "]状态失败");
				throw new RuntimeException("更新笼车[" + shelfNo + "]状态失败");
			}*/

			//更新任务单
//			TauroTaskTablePO tauroTaskTablePO = new TauroTaskTablePO();
//			tauroTaskTablePO.setTaskNo(taskNo);
//			tauroTaskTablePO.setStatus(nextStatus);
			//tauroTaskTablePO.setShelfNo(shelfNo);

			Map<String,Object> TaskTableMap = new HashMap<>();
			TaskTableMap.put("taskNo",taskNo);
			TaskTableMap.put("status",nextStatus);

			int n = taskInnerResource.updateByPrimaryKeyMap(TaskTableMap);
			if (n != 1) {
				log.error("更新表 [TASK_TABLE] 入库交接状态失败");
				throw new RuntimeException("更新订单任务编号[" + taskNo + "]入库交接状态失败");
			}

			//推送给所有人信息
			TauroTaskTablePO tauroTaskTablePO = new TauroTaskTablePO();
			tauroTaskTablePO.setTaskNo(taskNo);
			tauroTaskTablePO.setStatus(nextStatus);
			tauroTaskTablePO.setType("RuKuJiaoJie");
			String jsonStorageInfo = JSONUtil.createJsonString(tauroTaskTablePO);
			sendInfoInnerResource.sendInfo2All(jsonStorageInfo);
		}
		return dto;
	}

	@Override
	public List<String> getBackContainerNosList(String containerNo, List<String> list, String taskNo) {
		list.add(containerNo);
		TaskEntityDTO taskEntityDTO = taskEntityInnerResource.qryByContainerNo(containerNo);
		if (taskEntityDTO.getLeafFlag() == 1) {
			return list;
		} else {
			List<String> lowerContainerNos = taskEntityInnerResource.qryRecoverContainterNoListByNo(containerNo, taskNo);
			for (String lowerContainerNo : lowerContainerNos) {
				getBackContainerNosList(lowerContainerNo, list, taskNo);
			}
			return list;
		}
	}
}
