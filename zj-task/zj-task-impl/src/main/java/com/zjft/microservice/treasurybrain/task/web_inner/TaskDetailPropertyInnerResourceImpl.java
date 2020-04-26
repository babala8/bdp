package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.service.TaskDetailPropertyInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-02-25
 */
@Slf4j
@RestController
public class TaskDetailPropertyInnerResourceImpl implements TaskDetailPropertyInnerResource {

	@Resource
	private TaskDetailPropertyInnerService taskDetailPropertyInnerService;

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskDetailPropertyInnerService.insertSelectiveByMap(paramMap);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskDetailPropertyInnerService.deleteByTaskNo(taskNo);
	}
}
