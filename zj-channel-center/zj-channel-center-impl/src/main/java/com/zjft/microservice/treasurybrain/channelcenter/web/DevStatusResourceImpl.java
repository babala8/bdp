package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.service.DevStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author 杨光
 * @since 2019-05-06
 */

@Slf4j
@RestController
public class DevStatusResourceImpl implements DevStatusResource {

	@Resource
	private DevStatusService devStatusService;


	// -----------------------------------------------
	//                   内部服务，待优化
	// -----------------------------------------------

	@Override
	public List<Map<String, Object>> queryForList(String devNosSb) {
		return devStatusService.queryForList(devNosSb);
	}
}
