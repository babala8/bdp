package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.repository.DevStatusTableMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author 杨光
 */
@Slf4j
@Service
public class DevStatusServiceImpl implements DevStatusService {

	@Resource
	private DevStatusTableMapper devStatusTableMapper;


	@Override
	public List<Map<String, Object>> queryForList(String devNosSb) {
		return devStatusTableMapper.queryForList(devNosSb);
	}
}
