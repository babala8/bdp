package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.service.TaskPerRecorderInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-06
 */
@Slf4j
@RestController
public class TaskPerRecorderInnerResourceImpl implements TaskPerRecorderInnerResource{
	@Resource
	private TaskPerRecorderInnerService taskPerRecorderInnerService;
	@Override
	public int insertByMap(Map<String, Object> paramMap) {
		return taskPerRecorderInnerService.insertByMap(paramMap);
	}
}
