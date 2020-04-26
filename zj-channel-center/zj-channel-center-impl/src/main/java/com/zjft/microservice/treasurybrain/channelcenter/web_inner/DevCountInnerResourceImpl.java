package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.channelcenter.service.DevCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-07
 */
@Slf4j
@RestController
public class DevCountInnerResourceImpl implements DevCountInnerResource{

	@Resource
	private DevCountService devCountService;

	@Override
	public int deleteByDevNo(String devNo){
		return devCountService.deleteByDevNo(devNo);
	}

	@Override
	public int insert(Map<String,Object> record){
		return devCountService.insert(record);
	}

	@Override
	public List<String> selectlineNoList(String devNo){
		return devCountService.selectlineNoList(devNo);
	}

	@Override
	public List selectDctVOList(Map<String, Object> paramMap){
		return devCountService.selectDctVOList(paramMap);
	}

}
