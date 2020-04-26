package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.channelcenter.service.DevServiceCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 崔耀中
 * @since 2020-01-03
 */
@Slf4j
@RestController
public class DevServiceCompanyInnerResourceImpl implements DevServiceCompanyInnerResource{

	@Resource
	private DevServiceCompanyService devServiceCompanyService;

	@Override
	public String qryNoByName(String name){
		return devServiceCompanyService.qryNoByName(name);
	}

}
