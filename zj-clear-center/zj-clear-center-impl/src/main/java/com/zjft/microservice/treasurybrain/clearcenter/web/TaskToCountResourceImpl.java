package com.zjft.microservice.treasurybrain.clearcenter.web;

import com.zjft.microservice.treasurybrain.clearcenter.service.TaskToCountService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @author zhangjs
 * @since 2019/10/10
 */
@Slf4j
@RestController
public class TaskToCountResourceImpl implements TaskToCountResource {

	@Resource
	TaskToCountService taskToCountService;

	/**
	 * 配钞：对任务进行配钞清分
	 */
	@Override
	public DTO countTask(Map<String, Object> paramMap){
		return taskToCountService.countTask(paramMap);
	}

}
