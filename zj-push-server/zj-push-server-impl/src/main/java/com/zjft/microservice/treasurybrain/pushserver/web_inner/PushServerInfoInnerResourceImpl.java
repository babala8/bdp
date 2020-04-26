package com.zjft.microservice.treasurybrain.pushserver.web_inner;

import com.zjft.microservice.treasurybrain.pushserver.dto.PushSeverInfoDTO;
import com.zjft.microservice.treasurybrain.pushserver.service.PushServerInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 常 健
 * @since 2020/2/25
 */
@RestController
public class PushServerInfoInnerResourceImpl implements PushServerInfoInnerResource{

	@Resource
	private PushServerInfoService pushServerInfoService;

	@Override
	public List<PushSeverInfoDTO> qryPushInfo() {
		return pushServerInfoService.qryPushInfo();
	}

	@Override
	public int deletePushServerInfoById(String no) {
		return pushServerInfoService.deletePushServerInfoById(no);
	}
}
