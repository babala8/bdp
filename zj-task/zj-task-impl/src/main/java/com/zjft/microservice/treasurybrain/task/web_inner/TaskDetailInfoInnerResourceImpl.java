package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.CustomerInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskDetailTableDTO;
import com.zjft.microservice.treasurybrain.task.service.TaskDetailInfoInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-02
 */
@Slf4j
@RestController
public class TaskDetailInfoInnerResourceImpl implements TaskDetailInfoInnerResource {

	@Resource
	private TaskDetailInfoInnerService taskDetailInfoInnerService;

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskDetailInfoInnerService.deleteByTaskNo(taskNo);
	}

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskDetailInfoInnerService.insertSelectiveByMap(paramMap);
	}

	@Override
	public int updateByPrimaryKeyMap(Map<String, Object> paramMap) {
		return taskDetailInfoInnerService.updateByPrimaryKeyMap(paramMap);
	}

	@Override
	public TaskDetailTableDTO selectByPrimaryKey(String taskNo, String customerNo) {
		return taskDetailInfoInnerService.selectByPrimaryKey(taskNo,customerNo);
	}

	@Override
	public int qryDevNumToLoad(String taskNo) {
		return taskDetailInfoInnerService.qryDevNumToLoad(taskNo);
	}

	@Override
	public int selectCount(Map<String, Object> paramMap) {
		return taskDetailInfoInnerService.selectCount(paramMap);
	}

	@Override
	public List<CustomerInfoDTO> qryCustomerInfo(String taskNo) {
		return taskDetailInfoInnerService.qryCustomerInfo(taskNo);
	}
}
