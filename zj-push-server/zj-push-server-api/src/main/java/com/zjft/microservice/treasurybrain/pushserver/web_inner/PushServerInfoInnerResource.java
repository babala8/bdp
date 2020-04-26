package com.zjft.microservice.treasurybrain.pushserver.web_inner;

import com.zjft.microservice.treasurybrain.pushserver.dto.PushSeverInfoDTO;

import java.util.List;

/**
 * @author 常 健
 * @since 2020/2/25
 */
public interface PushServerInfoInnerResource {

	List<PushSeverInfoDTO> qryPushInfo();

	int deletePushServerInfoById(String no);
}
