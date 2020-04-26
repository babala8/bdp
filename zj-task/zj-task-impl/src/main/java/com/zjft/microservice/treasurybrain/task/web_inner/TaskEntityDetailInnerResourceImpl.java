package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.service.TaskEntityDetailService;
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
public class TaskEntityDetailInnerResourceImpl implements TaskEntityDetailInnerResource {

	@Resource
	private TaskEntityDetailService taskEntityDetailService;
	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskEntityDetailService.insertSelectiveByMap(paramMap);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskEntityDetailService.deleteByTaskNo(taskNo);
	}

	@Override
	public int deleteByTaskNoAndContainerNo(Map<String, Object> paramMap) {
		return taskEntityDetailService.deleteByTaskNoAndContainerNo(paramMap);
	}

	@Override
	public List<TaskEntityDetailDTO> selectByTaskNo(String taskNo) {
		return taskEntityDetailService.selectByTaskNo(taskNo);
	}

	@Override
	public TaskEntityDetailDTO selectOneByTaskNo(String taskNo, String entityNo) {
		return taskEntityDetailService.selectOneByTaskNo(taskNo,entityNo);
	}

	@Override
	public int selectByTaskNoAndContainerNo(Map<String, Object> paramMap) {
		return taskEntityDetailService.selectByTaskNoAndContainerNo(paramMap);
	}

	@Override
	public List<StorageUseCassetteBagDTO> getCassetteBagList(String taskNo, String devNo) {
		return taskEntityDetailService.getCassetteBagList(taskNo,devNo);
	}

	@Override
	public List<StorageUseCassetteBagDetailDTO> getCassetteDetail(String id) {
		return taskEntityDetailService.getCassetteDetail(id);
	}


}
