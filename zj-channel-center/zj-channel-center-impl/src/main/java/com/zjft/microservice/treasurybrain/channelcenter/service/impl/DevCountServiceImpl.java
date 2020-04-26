package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CountTaskInfoDO;
import com.zjft.microservice.treasurybrain.channelcenter.domain.DevCountDO;
import com.zjft.microservice.treasurybrain.channelcenter.domain.TaskCountInfo;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CountTaskInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.DevCountConverter;
import com.zjft.microservice.treasurybrain.channelcenter.po.DevCountPO;
import com.zjft.microservice.treasurybrain.channelcenter.repository.DevClrTimeParamMapper;
import com.zjft.microservice.treasurybrain.channelcenter.repository.DevCountMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevCountService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2019-10-10
 */
@Slf4j
@Service
public class DevCountServiceImpl implements DevCountService {

	@Resource
	private DevCountMapper devCountMapper;

	@Resource
	DevClrTimeParamMapper devClrTimeParamMapper;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Override
	public PageDTO<DevCountDTO> queryDevCountList(Map<String, Object> paramMap){
		log.info("------------[queryDevCountList]DevCountService-------------");
		PageDTO<DevCountDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		try {
			Integer devType = StringUtil.objectToInt(paramMap.get("devType"));
			Integer devStatus = StringUtil.objectToInt(paramMap.get("devStatus"));
			paramMap.put("devType", devType);
			paramMap.put("devStatus", devStatus);
			int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);

			// 获取数据总条数
			int totalRow = devCountMapper.qryTotalRowDevCount(paramMap);
			int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

			//获取分页数据
			List<DevCountDO> doList = devCountMapper.queryDevCountList(paramMap);
			List<DevCountDTO> dtoList = DevCountConverter.INSTANCE.domain2dto(doList);
			pageDTO.setTotalRow(totalRow);
			pageDTO.setTotalPage(totalPage);
			pageDTO.setPageSize(dtoList.size());
			pageDTO.setRetList(dtoList);
			pageDTO.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[queryDevCountList]Fail", e);
			pageDTO.setResult(RetCodeEnum.EXCEPTION);
		}
		return pageDTO;

	}

	@Override
	public Integer queryByDevNo(String devNo){
		log.info("------------[queryByDevNo]DevCountService-------------");
		return devCountMapper.queryByDevNo(devNo);
	}

	@Override
	public DTO addDevCountInfo(DevCountDTO devCountDTO){
		log.info("------------[addDevCountInfo]DevCountService-------------");
		try {
			DevCountPO devCountPO = DevCountConverter.INSTANCE.dto2domain(devCountDTO);
			int insert = devCountMapper.insert(devCountPO);
			if (insert == 1){
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}catch (Exception e){
			log.error("[addDevCountInfo] Fail: ", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
		return  new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO modDevCount(DevCountDTO devCountDTO){
		log.info("------------[modDevCount]DevCountService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			DevCountPO devCountPO = DevCountConverter.INSTANCE.dto2domain(devCountDTO);
			int x = devCountMapper.update(devCountPO);
			if (x == 1) {
				dto.setResult(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[modDevCount]Fail", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public DTO delDevCountByNo(String devNo){
		log.info("------------[delDevCountByNo]DevCountService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			int x = devCountMapper.delDevCountByNo(devNo);
			if (x == 1) {
				dto.setResult(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[delDevCountByNo]Fail", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public ListDTO<DevCountDTO> queryDevConMonitoring(Map<String, Object> paramMap) {
		ListDTO<DevCountDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
		List<DevCountDO> devCountDOS = devCountMapper.queryDevConMonitoring(paramMap);

		List<DevCountDO> devCountDOList = new ArrayList<>();
		for(DevCountDO devCountDO:devCountDOS){
			String devNo = devCountDO.getDevNo();
			int countStatus1 = 1; //等待执行
			int countStatus3 = 3; //已完成
			int countStatus4 = 4; //正在执行
			List<CountTaskInfoDO> doingList = devCountMapper.selectCountTaskInfo(devNo,countStatus3);
			List<CountTaskInfoDO> queueList = devCountMapper.selectCountTaskInfo(devNo,countStatus1);
			List<CountTaskInfoDO> doneList = devCountMapper.selectCountTaskInfo(devNo,countStatus4);
			devCountDO.setDoingList(doingList);
			devCountDO.setDoneList(doneList);
			devCountDO.setQueueList(queueList);
			devCountDOList.add(devCountDO);
		}
		List<DevCountDTO> devCountDTOS = DevCountConverter.INSTANCE.domain2dto(devCountDOList);
		dto.setRetList(devCountDTOS);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	@Override
	public ListDTO<DevCountDTO> qryCountTaskNum(){
		ListDTO<DevCountDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
		List<DevCountDO> devCountDOS = devCountMapper.qryCountTaskNum();
		List<DevCountDTO> devCountDTOS = DevCountConverter.INSTANCE.domain2dto(devCountDOS);
		dto.setRetList(devCountDTOS);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	@Override
	public DTO allotCount(CountTaskInfoDTO countTaskInfoDTO){
		log.info("------------[allotCount]DevCountService-------------");
		try {
			List<String> taskNoList = countTaskInfoDTO.getTaskNoList();
			for (String taskNo:taskNoList){
				CountTaskInfoDO x = devCountMapper.qryCountTaskInfoBytaskNo(taskNo);
				if (x!=null){
					log.error("添加失败，设备编号已存在");
					return new DTO(RetCodeEnum.FAIL.getCode(),"添加失败，"+taskNo+"任务编号已存在");
				}
				countTaskInfoDTO.setTaskNo(taskNo);
				countTaskInfoDTO.setCountStartDate(CalendarUtil.getSysTimeYMDHMS());
				CountTaskInfoDO countTaskInfoDO = DevCountConverter.INSTANCE.dto2domain(countTaskInfoDTO);
				int insert = devCountMapper.insertCountTask(countTaskInfoDO);
				if (insert != 1){
					return new DTO(RetCodeEnum.FAIL);
				}
			}
		}catch (Exception e){
			log.error("[addDevCountInfo] Fail: ", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
		//推送给所有人信息
		countTaskInfoDTO.setType("FenPeiQingFenJi");
		String jsonStorageInfo = JSONUtil.createJsonString(countTaskInfoDTO);
		sendInfoInnerResource.sendInfo2All(jsonStorageInfo);
		return  new DTO(RetCodeEnum.SUCCEED);
	}

	/**
	 * 清分设备信息查询
	 * @param paramMap
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	@Override
	public ListDTO<DevCountDTO> qryCountDevList(Map<String, Object> paramMap) {
		ListDTO listDTO=new ListDTO();
		List<DevCountDO> list = devCountMapper.qryCountDevList(paramMap);
		List<DevCountDTO> dtoList = DevCountConverter.INSTANCE.domain2dto(list);

		for(DevCountDTO devCountDTO : dtoList) {
			List<TaskCountInfo> taskList = devCountMapper.selectByDevNo(devCountDTO.getDevNo());

			if(taskList != null && taskList.size() > 0) {
//				devCountInfoDTO.setBatchCount(taskList.size());
				devCountDTO.setWorkStatus(2);
				int batch = 0;
				int count = 0;
				for(TaskCountInfo taskCountInfo : taskList) {

					if(batch != taskCountInfo.getBatch()) {
						count++;
					}

					if(taskCountInfo.getCountStatus() == 4) {
						devCountDTO.setWorkStatus(1);
					}
					batch = taskCountInfo.getBatch();
				}
				devCountDTO.setBatchCount(count);

			}
		}
		listDTO.setRetList(dtoList);
		listDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		listDTO.setRetMsg(RetCodeEnum.SUCCEED.getTip());
		return listDTO;
	}

	@Override
	public int deleteByDevNo(String devNo){
		return devClrTimeParamMapper.deleteByDevNo(devNo);
	}

	@Override
	public int insert(Map<String,Object> record){
		return devClrTimeParamMapper.insert(record);
	}

	@Override
	public List<String> selectlineNoList(String devNo){
		return devClrTimeParamMapper.selectlineNoList(devNo);
	}

	@Override
	public List selectDctVOList(Map<String, Object> paramMap){
		return devClrTimeParamMapper.selectDctVOList(paramMap);
	}

}
