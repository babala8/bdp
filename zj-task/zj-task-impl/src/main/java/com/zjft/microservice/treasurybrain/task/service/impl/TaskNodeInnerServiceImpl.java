package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.task.dto.TaskNodeDTO;
import com.zjft.microservice.treasurybrain.task.repository.TaskNodeMapper;
import com.zjft.microservice.treasurybrain.task.service.TaskNodeInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 韩通
 * @since 2020-01-07
 */
@Slf4j
@Service
public class TaskNodeInnerServiceImpl implements TaskNodeInnerService {
	@Resource
	private TaskNodeMapper taskNodeMapper;
	@Override
	public int insert(TaskNodeDTO taskNodeDTO) {
		return taskNodeMapper.insert(taskNodeDTO);
	}

	@Override
	public int insertBatch(List<TaskNodeDTO> taskNodeDTOList) {
		return taskNodeMapper.insertBatch(taskNodeDTOList);
	}

	@Override
	public String getNextTaskNodeNo(String taskNo) {
		String taskNodeNo = null;
		String maxNodeNo = taskNodeMapper.getMaxNodeNo(taskNo);
		//计算之后的taskNodeNo
		if(StringUtil.isNullorEmpty(maxNodeNo)){
			taskNodeNo = taskNo + "01";
		}else{
			String before = maxNodeNo.substring(0, maxNodeNo.length() - 2);
			String end = maxNodeNo.substring(maxNodeNo.length() - 2, maxNodeNo.length());
			taskNodeNo = before + String.format("%02d", Integer.parseInt(end)+1);
		}
		return taskNodeNo;
	}

	@Override
	public List<TaskNodeDTO> qryTaskNode(String taskNo) {
		return taskNodeMapper.qryTaskNode(taskNo);
	}
}
