package com.zjft.microservice.treasurybrain.clearcenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;

import java.util.Map;


/**
 * @author zhangjs
 * @since 2019-10-10
 */
public interface TaskToCountService {

	/**
	 * 设备配钞清分
	 *
	 * @param paramMap 配钞清分数据
	 * @return 响应信息
	 */
	DTO countTask(Map<String, Object> paramMap);
}
