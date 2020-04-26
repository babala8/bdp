package com.zjft.microservice.treasurybrain.pushserver.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushSeverInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/26
 */
public interface PushServerInfoService {

	PageDTO<PushSeverInfoDTO> qryByPage(Map<String, Object> paramMap);

	DTO updateStatus(String no);

	List<PushSeverInfoDTO> qryPushInfo();

	int deletePushServerInfoById(String no);

	PushSeverInfoDTO qryMessageByNo(String no);
}
