package com.zjft.microservice.treasurybrain.storage.web_inner;

import com.zjft.microservice.treasurybrain.storage.service.TaskShelfUserInnerService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/1/7
 */
@RestController
public class TaskShelfUserInnerResourceImpl implements TaskShelfUserInnerResource {

	@Resource
	private TaskShelfUserInnerService taskShelfUserInnerService;

	@Override
	public int insertByMap(Map<String, Object> map) {
		return taskShelfUserInnerService.insertByMap(map);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskShelfUserInnerService.deleteByTaskNo(taskNo);
	}
}
