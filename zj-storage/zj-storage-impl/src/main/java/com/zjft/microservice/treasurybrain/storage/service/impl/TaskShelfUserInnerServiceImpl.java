package com.zjft.microservice.treasurybrain.storage.service.impl;

import com.zjft.microservice.treasurybrain.storage.repository.StorageTaskShelfUserMapper;
import com.zjft.microservice.treasurybrain.storage.service.TaskShelfUserInnerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/1/7
 */
@Service
public class TaskShelfUserInnerServiceImpl implements TaskShelfUserInnerService {

	@Resource
	private StorageTaskShelfUserMapper storageTaskShelfUserMapper;

	@Override
	public int insertByMap(Map<String, Object> map) {
		return storageTaskShelfUserMapper.insertByMap(map);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return storageTaskShelfUserMapper.deleteByTaskNo(taskNo);
	}
}
