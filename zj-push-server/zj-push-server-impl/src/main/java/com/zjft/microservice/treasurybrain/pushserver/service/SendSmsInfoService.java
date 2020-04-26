package com.zjft.microservice.treasurybrain.pushserver.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushServerRequestDTO;

/**
 * @author 常 健
 * @since 2020/2/24
 */
public interface SendSmsInfoService {

	DTO sendInfo2User(PushServerRequestDTO pushServerRequestDTO);

	DTO sendInfo2Roles(PushServerRequestDTO pushServerRequestDTO);

	DTO sendInfo2All(PushServerRequestDTO pushServerRequestDTO);
}
